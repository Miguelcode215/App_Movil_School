package com.example.schoolapp.features.matricula.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolapp.R;
import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.viewmodel.AlumnoViewModel;

import java.util.Calendar;
import java.util.List;

public class MatriculaFragment extends Fragment {

    private AlumnoViewModel viewModel;
    private TextView textTitulo, textSubtitulo, textAnio, textAlumnos, textGrados, textApoderado;
    private TextView textLabelAlumnos, textLabelGrados;
    private View loadingOverlay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matricula_fragment_matricula, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias UI
        textTitulo = view.findViewById(R.id.textTitulo);
        textSubtitulo = view.findViewById(R.id.textSubtitulo);
        textAnio = view.findViewById(R.id.textAnioAcademico);
        textAlumnos = view.findViewById(R.id.textAlumnos);
        textGrados = view.findViewById(R.id.textGrados);
        textApoderado = view.findViewById(R.id.textApoderado);
        textLabelAlumnos = view.findViewById(R.id.labelAlumnos);
        textLabelGrados = view.findViewById(R.id.labelGrados);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        // ViewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);
        viewModel.init(requireContext());

        // Solo si aún no hay datos, los cargamos
        if (viewModel.getAlumnos().getValue() == null) {
            viewModel.cargarAlumnos();
        }

        // Observadores
        viewModel.getAlumnos().observe(getViewLifecycleOwner(), alumnos -> {
            if (alumnos != null && !alumnos.isEmpty()) {
                int anio = Calendar.getInstance().get(Calendar.YEAR);
                textAnio.setText(String.valueOf(anio));

                StringBuilder nombres = new StringBuilder();
                StringBuilder grados = new StringBuilder();

                for (Alumno alumno : alumnos) {
                    nombres.append("\u2022 ").append(alumno.getNombreCompleto()).append("\n");
                    if (alumno.getGrados() != null) {
                        grados.append("\u2022 ").append(alumno.getGrados().getNombre_grado()).append("\n");
                    }
                }

                textAlumnos.setText(nombres.toString().trim());
                textGrados.setText(grados.toString().trim());

                textLabelAlumnos.setText(alumnos.size() > 1 ? "ALUMNOS:" : "ALUMNO:");
                textLabelGrados.setText(alumnos.size() > 1 ? "GRADOS:" : "GRADO:");
            }
        });

        viewModel.getApoderado().observe(getViewLifecycleOwner(), apoderado -> {
            textApoderado.setText(apoderado.getNombreCompleto());
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getIsError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && error) {
                Toast.makeText(getContext(), "Error al obtener datos de matrícula", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
