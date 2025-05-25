package com.example.schoolapp.features.asistencias.ui;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.schoolapp.R;
import com.example.schoolapp.core.viewmodel.AlumnoViewModel;
import com.example.schoolapp.features.asistencias.adapter.AsistenciaAlumnoPagerAdapter;

public class AsistenciaFragment extends Fragment {

    private AlumnoViewModel alumnoViewModel;
    private ViewPager2 viewPager;
    private TextView textTitulo;
    private View loadingOverlay; // <-- igual que en el módulo de inicio

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asistencias_fragment_asistencia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPagerAsistencia);
        textTitulo = view.findViewById(R.id.textTituloAsistencia);
        loadingOverlay = view.findViewById(R.id.loadingOverlay); // <-- aquí capturas la vista incluida

        alumnoViewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);
        alumnoViewModel.init(requireContext());

        loadingOverlay.setVisibility(View.VISIBLE); // Mostrar mientras carga

        alumnoViewModel.cargarAlumnos();

        alumnoViewModel.getAlumnos().observe(getViewLifecycleOwner(), alumnos -> {
            loadingOverlay.setVisibility(View.GONE); // Ocultar cuando termina
            if (alumnos != null && !alumnos.isEmpty()) {
                AsistenciaAlumnoPagerAdapter adapter = new AsistenciaAlumnoPagerAdapter(this, alumnos);
                viewPager.setAdapter(adapter);
            }
        });

        alumnoViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && error) {
                loadingOverlay.setVisibility(View.GONE); // También ocultar si hay error
                Toast.makeText(getContext(), "Error al cargar los alumnos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

