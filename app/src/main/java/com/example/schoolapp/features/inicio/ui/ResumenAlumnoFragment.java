package com.example.schoolapp.features.inicio.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolapp.R;
import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.inicio.model.AsistenciaResumen;
import com.example.schoolapp.features.inicio.model.ConteoAsistencias;
import com.example.schoolapp.features.inicio.model.PorcentajeAsistencias;
import com.example.schoolapp.features.inicio.viewmodel.ResumenAsistenciaViewModel;
import com.example.schoolapp.features.inicio.utils.ChartUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.Map;

public class ResumenAlumnoFragment extends Fragment {

    private static final String ARG_ALUMNO = "alumno";
    private Alumno alumno;
    private ResumenAsistenciaViewModel resumenAsistenciaViewModel;

    // UI
    private TextView textNombre, textGrado, textPorcentaje, textFaltas;
    private PieChart pieChart;
    private BarChart barChart;

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

        View loadingOverlay = view.findViewById(R.id.loadingOverlay);

        textNombre = view.findViewById(R.id.textNombreCompleto);
        textGrado = view.findViewById(R.id.textGrado);
        textPorcentaje = view.findViewById(R.id.textAsistenciaPorcentaje);
        textFaltas = view.findViewById(R.id.textCantidadFaltas);
        pieChart = view.findViewById(R.id.pieChartAsistencia);
        barChart = view.findViewById(R.id.barChartAsistencia);

        if (alumno != null) {
            textNombre.setText(alumno.getNombreCompleto());
            textGrado.setText(alumno.getGrados().getNombre_grado());
            resumenAsistenciaViewModel = new ViewModelProvider(this).get(ResumenAsistenciaViewModel.class);
            resumenAsistenciaViewModel.cargarResumenAsistencia(alumno.getId_Alumno());

            resumenAsistenciaViewModel.getResumenLiveData()
                    .observe(getViewLifecycleOwner(), resumen -> {
                        if (resumen != null) {
                            mostrarDatos(alumno, resumen);
                        }
                    });
        } else {
            Log.e("ResumenAlumnoFragment", "Alumno recibido es null");
        }

        resumenAsistenciaViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loadingOverlay.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE);
        });

    }

    private void mostrarDatos(Alumno alumno, AsistenciaResumen resumen) {
        textNombre.setText(alumno.getNombreCompleto());
        textGrado.setText(alumno.getGrados().getNombre_grado());
        textPorcentaje.setText(ChartUtils.formatDecimal(resumen.getPorcentaje_asistencia()));
        textFaltas.setText(resumen.getConteo_faltas() + " faltas");

        Map<String, Integer> conteo = resumen.getConteo();
        Map<String, Float> distribucion = resumen.getDistribucion_porcentual();

        PorcentajeAsistencias porcentajes = new PorcentajeAsistencias(
                distribucion.getOrDefault("Presente", 0f),
                distribucion.getOrDefault("Atrasado", 0f),
                distribucion.getOrDefault("Ausente", 0f),
                distribucion.getOrDefault("Justificado", 0f)
        );

        ConteoAsistencias conteoAsistencias = new ConteoAsistencias(
                conteo.getOrDefault("Presente", 0),
                conteo.getOrDefault("Atrasado", 0),
                conteo.getOrDefault("Ausente", 0),
                conteo.getOrDefault("Justificado", 0)
        );

        ChartUtils.configurarPieChart(pieChart, porcentajes);
        ChartUtils.configurarBarChart(requireContext(), barChart, conteoAsistencias);
    }

}
