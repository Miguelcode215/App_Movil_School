package com.example.schoolapp.features.inicio.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.inicio.ui.ResumenAlumnoFragment;

import java.util.List;

public class ResumenAlumnoPagerAdapter extends FragmentStateAdapter {

    private List<Alumno> alumnos;

    public ResumenAlumnoPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Alumno> alumnos) {
        super(fragmentActivity);
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Alumno alumno = alumnos.get(position);
        return ResumenAlumnoFragment.newInstance(alumno); // ← Aquí se crea un fragmento por alumno
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }
}
