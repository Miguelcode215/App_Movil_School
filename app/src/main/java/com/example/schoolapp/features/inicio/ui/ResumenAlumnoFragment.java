package com.example.schoolapp.features.inicio.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.features.inicio.adapter.AlumnoResumenAdapter;
import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.inicio.viewmodel.ResumenAsistenciaViewModel;

import java.util.List;
import java.util.Map;

public class ResumenAlumnoFragment extends Fragment {

    private static final String ARG_ALUMNO = "alumno";
    private Alumno alumno;
    private RecyclerView recyclerView;
    private ResumenAsistenciaViewModel resumenAsistenciaViewModel;

    public ResumenAlumnoFragment() {}

    public static ResumenAlumnoFragment newInstance(Alumno alumno) {
        ResumenAlumnoFragment fragment = new ResumenAlumnoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALUMNO, alumno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                alumno = getArguments().getSerializable(ARG_ALUMNO, Alumno.class);
            } else {
                alumno = (Alumno) getArguments().getSerializable(ARG_ALUMNO);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inicio_fragment_resumen_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewResumen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (alumno != null) {
            resumenAsistenciaViewModel = new ViewModelProvider(this).get(ResumenAsistenciaViewModel.class);
            resumenAsistenciaViewModel.cargarResumenAsistencia(alumno.getId_Alumno());

            resumenAsistenciaViewModel.getResumenLiveData()
                    .observe(getViewLifecycleOwner(), resumen -> {
                        if (resumen != null) {
                            int porcentaje = resumen.getPorcentaje_asistencia();
                            int faltas = resumen.getConteo_faltas();
                            Map<String, Integer> conteo = resumen.getConteo();
                            Map<String, Float> distribucion = resumen.getDistribucion_porcentual();

                            Log.d("ResumenAlumnoFragment", "Conteo: " + conteo);
                            Log.d("ResumenAlumnoFragment", "Porcentaje: " + distribucion);

                            AlumnoResumenAdapter adapter = new AlumnoResumenAdapter(
                                    List.of(alumno),
                                    Map.of(alumno.getId_Alumno(), resumen)
                            );

                            recyclerView.setAdapter(adapter);
                        }
                    });
        } else {
            Log.e("ResumenAlumnoFragment", "Alumno recibido es null");
        }
    }
}
