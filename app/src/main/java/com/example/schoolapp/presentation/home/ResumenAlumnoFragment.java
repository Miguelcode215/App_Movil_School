package com.example.schoolapp.presentation.home;

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
import com.example.schoolapp.adapter.AlumnoResumenAdapter;
import com.example.schoolapp.data.model.Alumno;
import com.example.schoolapp.data.model.Asistencia;
import com.example.schoolapp.data.model.ConteoAsistencias;
import com.example.schoolapp.data.model.PorcentajeAsistencias;
import com.example.schoolapp.viewmodel.AsistenciaViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumenAlumnoFragment extends Fragment {

    private static final String ARG_ALUMNO = "alumno";
    private Alumno alumno;
    private RecyclerView recyclerView;
    private AsistenciaViewModel asistenciaViewModel;

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
        return inflater.inflate(R.layout.fragment_resumen_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewResumen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (alumno != null) {
            asistenciaViewModel = new ViewModelProvider(this).get(AsistenciaViewModel.class);
            asistenciaViewModel.cargarAsistencias(alumno.getId_Alumno())
                    .observe(getViewLifecycleOwner(), asistencias -> {
                        if (asistencias != null) {
                            int porcentaje = asistenciaViewModel.calcularPorcentajeAsistencia(asistencias);
                            int faltas = asistenciaViewModel.contarFaltas(asistencias);

                            Map<Integer, ConteoAsistencias> mapaConteo = new HashMap<>();
                            mapaConteo.put(alumno.getId_Alumno(), asistenciaViewModel.obtenerConteoAsistencias(asistencias));

                            Map<Integer, PorcentajeAsistencias> mapaPorcentajes = new HashMap<>();
                            mapaPorcentajes.put(alumno.getId_Alumno(), asistenciaViewModel.obtenerPorcentajeAsistencias(asistencias));

                            Log.d("ResumenAlumnoFragment", "Conteo: " + mapaConteo.get(alumno.getId_Alumno()));
                            Log.d("ResumenAlumnoFragment", "Porcentaje: " + mapaPorcentajes.get(alumno.getId_Alumno()));

                            AlumnoResumenAdapter adapter = new AlumnoResumenAdapter(
                                    List.of(alumno),
                                    Map.of(alumno.getId_Alumno(), porcentaje),
                                    Map.of(alumno.getId_Alumno(), faltas),
                                    mapaConteo,
                                    mapaPorcentajes
                            );

                            recyclerView.setAdapter(adapter);
                        }
                    });
        } else {
            Log.e("ResumenAlumnoFragment", "Alumno recibido es null");
        }
    }
}
