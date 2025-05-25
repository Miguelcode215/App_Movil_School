package com.example.schoolapp.features.inicio.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.schoolapp.R;
import com.example.schoolapp.core.viewmodel.AlumnoViewModel;
import com.example.schoolapp.features.inicio.adapter.ResumenAlumnoPagerAdapter;

public class InicioFragment extends Fragment {

    private AlumnoViewModel alumnoViewModel;
    private ViewPager2 viewPagerAlumnos;
    private View loadingOverlay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inicio_fragment_inicio, container, false); // Solo inflamos la vista
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a componentes UI
        viewPagerAlumnos = view.findViewById(R.id.viewPagerAlumnos);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        loadingOverlay.setVisibility(View.VISIBLE); // Mostrar la pantalla de carga

        // ViewModel y lógica de negocio
        alumnoViewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);
        alumnoViewModel.init(requireContext());
        alumnoViewModel.cargarAlumnos();

        alumnoViewModel.getAlumnos().observe(getViewLifecycleOwner(), alumnos -> {
            loadingOverlay.setVisibility(View.GONE); // Ocultar loading al obtener respuesta
            if (alumnos != null && !alumnos.isEmpty()) {
                ResumenAlumnoPagerAdapter adapter = new ResumenAlumnoPagerAdapter(requireActivity(), alumnos);
                viewPagerAlumnos.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "No hay alumnos a cargo", Toast.LENGTH_SHORT).show();
            }
        });

        alumnoViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error) {
                loadingOverlay.setVisibility(View.GONE); // Ocultar también en caso de error
                Toast.makeText(getContext(), "Error al obtener alumnos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
