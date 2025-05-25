package com.example.schoolapp.features.asistencias.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.asistencias.ui.ListaAsistenciasFragment;

import java.util.List;

public class AsistenciaAlumnoPagerAdapter extends FragmentStateAdapter {
    private List<Alumno> listaAlumnos;

    public AsistenciaAlumnoPagerAdapter(@NonNull Fragment fragment, List<Alumno> alumnos) {
        super(fragment);
        this.listaAlumnos = alumnos;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Alumno alumno = listaAlumnos.get(position);
        ListaAsistenciasFragment fragment = new ListaAsistenciasFragment();

        Bundle args = new Bundle();
        args.putSerializable("alumno", alumno);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }
}
