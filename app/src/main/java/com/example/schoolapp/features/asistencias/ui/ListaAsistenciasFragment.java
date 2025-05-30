package com.example.schoolapp.features.asistencias.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.asistencias.adapter.AsistenciaPorMesAdapter;
import com.example.schoolapp.features.asistencias.model.AsistenciaPorMes;
import com.example.schoolapp.features.asistencias.viewmodel.AsistenciaPorMesViewModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaAsistenciasFragment extends Fragment {

    private TextView textNombre, textGrado, textMensaje;
    private MaterialAutoCompleteTextView spinnerMes;
    private RecyclerView recyclerView;
    private FrameLayout layoutLoading;

    private Alumno alumno;
    private AsistenciaPorMesAdapter adapter;
    private AsistenciaPorMesViewModel viewModel;

    private List<String> generarOpcionesMesAnio() {
        List<String> opciones = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int anioActual = calendar.get(Calendar.YEAR);

        String[] meses = new String[]{
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (String mes : meses) {
            opciones.add(mes + " " + anioActual);
        }
        return opciones;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asistencias_fragment_lista_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vistas
        textNombre = view.findViewById(R.id.textNombreAsistencia);
        textGrado = view.findViewById(R.id.textGradoAsistencia);
        spinnerMes = view.findViewById(R.id.spinnerMes);
        recyclerView = view.findViewById(R.id.recyclerAsistencias);
        textMensaje = view.findViewById(R.id.textMensajeAsistencias);
        layoutLoading = view.findViewById(R.id.loadingOverlay); // ✅ overlay de carga

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AsistenciaPorMesAdapter(null);
        recyclerView.setAdapter(adapter);

        alumno = (Alumno) getArguments().getSerializable("alumno");
        if (alumno != null) {
            textNombre.setText(alumno.getNombreCompleto());
            if (alumno.getGrados() != null) {
                textGrado.setText(alumno.getGrados().getNombre_grado());
            } else {
                textGrado.setText("Grado no disponible");
            }
        }

        // ViewModel
        viewModel = new ViewModelProvider(this).get(AsistenciaPorMesViewModel.class);

        // Spinner de meses
        List<String> opcionesMesAnio = generarOpcionesMesAnio();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.asistencias_spinner_dropdown_item, // Usar diseño personalizado (te lo muestro abajo)
                opcionesMesAnio
        );
        spinnerMes.setAdapter(spinnerAdapter);

        // Establecer selección inicial (mes actual)
        int mesActual = Calendar.getInstance().get(Calendar.MONTH);
        spinnerMes.setText(opcionesMesAnio.get(mesActual), false);

        // Establece mes actual

        spinnerMes.setOnItemClickListener((parent, view1, position, id) -> {
            cargarAsistencias(position + 1); // Backend espera 1–12
        });

        spinnerMes.setDropDownBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.asistencias_bg_dropdown_popup)
        );

        // Observadores del ViewModel
        // Loading solo aquí
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            layoutLoading.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE);
        });

        // Error solo enfocado al mensaje
        viewModel.getIsError().observe(getViewLifecycleOwner(), isError -> {
            if (isError != null && isError) {
                textMensaje.setVisibility(View.VISIBLE);
                textMensaje.setText("Error al cargar asistencias");
                adapter.actualizarLista(null);
            }
        });

        // Mostrar data sin tocar el loading
        viewModel.getAsistenciaResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getAsistencias() != null) {
                List<AsistenciaPorMes> lista = response.getAsistencias();
                if (!lista.isEmpty()) {
                    adapter.actualizarLista(lista);
                    textMensaje.setVisibility(View.GONE);
                } else {
                    adapter.actualizarLista(null);
                    textMensaje.setVisibility(View.VISIBLE);
                    textMensaje.setText("No hay asistencias registradas");
                }
            }
        });

        // Cargar por defecto el mes actual
        cargarAsistencias(mesActual + 1);
    }

    private void cargarAsistencias(int mesSeleccionado) {
        if (alumno != null) {
            viewModel.cargarAsistencias(alumno.getId_Alumno(), mesSeleccionado);
        }
    }
}
