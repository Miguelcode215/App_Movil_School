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

        // ViewModel y lógica de negocio
        alumnoViewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);
        alumnoViewModel.init(requireContext());
        if (alumnoViewModel.getAlumnos().getValue() == null) {
            alumnoViewModel.cargarAlumnos(); // Solo si no están cargados
        }

        alumnoViewModel.getAlumnos().observe(getViewLifecycleOwner(), alumnos -> {
            if (alumnos != null && !alumnos.isEmpty()) {
                ResumenAlumnoPagerAdapter adapter = new ResumenAlumnoPagerAdapter(requireActivity(), alumnos);
                viewPagerAlumnos.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "No hay alumnos a cargo", Toast.LENGTH_SHORT).show();
            }
        });

        alumnoViewModel.getIsError().observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "Error al obtener alumnos", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Nuevo: usar loading desde ViewModel
        alumnoViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loadingOverlay.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE);
        });
    }
}
