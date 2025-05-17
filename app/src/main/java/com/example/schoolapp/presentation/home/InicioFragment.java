package com.example.schoolapp.presentation.home;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.schoolapp.adapter.AlumnoPagerAdapter;
import com.example.schoolapp.viewmodel.AlumnoViewModel;

import com.example.schoolapp.R;
import com.example.schoolapp.viewmodel.AsistenciaViewModel;

public class InicioFragment extends Fragment {

    private AlumnoViewModel alumnoViewModel;

    private ViewPager2 viewPagerAlumnos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        viewPagerAlumnos = vista.findViewById(R.id.viewPagerAlumnos);

        alumnoViewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);
        alumnoViewModel.init(requireContext());
        alumnoViewModel.cargarAlumnos();


        alumnoViewModel.getAlumnos().observe(getViewLifecycleOwner(), alumnos -> {
            if (alumnos != null && !alumnos.isEmpty()) {
                AlumnoPagerAdapter adapter = new AlumnoPagerAdapter(requireActivity(), alumnos);
                viewPagerAlumnos.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "No hay alumnos a cargo", Toast.LENGTH_SHORT).show();
            }
        });

        alumnoViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "Error al obtener alumnos", Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }
}

