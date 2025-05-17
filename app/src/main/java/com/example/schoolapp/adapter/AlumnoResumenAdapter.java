package com.example.schoolapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.data.model.Alumno;

import java.util.List;
import java.util.Map;

public class AlumnoResumenAdapter extends RecyclerView.Adapter<AlumnoResumenAdapter.ViewHolder> {

    private final List<Alumno> alumnos;
    private final Map<Integer, Integer> mapaPorcentajes;
    private final Map<Integer, Integer> mapaFaltas;

    public AlumnoResumenAdapter(List<Alumno> alumnos,
                                Map<Integer, Integer> mapaPorcentajes,
                                Map<Integer, Integer> mapaFaltas) {
        this.alumnos = alumnos;
        this.mapaPorcentajes = mapaPorcentajes;
        this.mapaFaltas = mapaFaltas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumno alumno = alumnos.get(position);
        int idAlumno = alumno.getId_Alumno();

        int porcentaje = mapaPorcentajes.getOrDefault(idAlumno, 0);
        int faltas = mapaFaltas.getOrDefault(idAlumno, 0);

        holder.textNombre.setText(alumno.getNombreCompleto());
        holder.textGrado.setText(alumno.getGrados().getNombre_grado());
        holder.textPorcentaje.setText(porcentaje + "%");
        holder.textFaltas.setText(faltas + " faltas");
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textGrado, textPorcentaje, textFaltas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreCompleto);
            textGrado = itemView.findViewById(R.id.textGrado);
            textPorcentaje = itemView.findViewById(R.id.textAsistenciaPorcentaje);
            textFaltas = itemView.findViewById(R.id.textCantidadFaltas);
        }
    }
}
