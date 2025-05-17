package com.example.schoolapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.schoolapp.data.model.Alumno;
import com.example.schoolapp.presentation.home.ResumenAlumnoFragment;

import java.util.List;

public class AlumnoPagerAdapter extends FragmentStateAdapter {

    private List<Alumno> alumnos;

    public AlumnoPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Alumno> alumnos) {
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
