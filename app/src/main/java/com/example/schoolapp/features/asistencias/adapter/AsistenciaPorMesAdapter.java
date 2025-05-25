package com.example.schoolapp.features.asistencias.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.features.asistencias.model.AsistenciaPorMes;

import java.util.List;

public class AsistenciaPorMesAdapter extends RecyclerView.Adapter<AsistenciaPorMesAdapter.AsistenciaViewHolder> {

    private List<AsistenciaPorMes> listaAsistencias;

    public AsistenciaPorMesAdapter(List<AsistenciaPorMes> listaAsistencias) {
        this.listaAsistencias = listaAsistencias;
    }

    @NonNull
    @Override
    public AsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asistencias_item_asistencia, parent, false);
        return new AsistenciaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciaViewHolder holder, int position) {
        AsistenciaPorMes asistencia = listaAsistencias.get(position);
        holder.textFecha.setText(asistencia.getFechaFormateada()); // Asegúrate de formatear la fecha
        holder.textEstado.setText(asistencia.getEstado());

        String observaciones = asistencia.getObservaciones();
        if (observaciones != null && !observaciones.trim().isEmpty()) {
            holder.textObservaciones.setVisibility(View.VISIBLE);
            holder.textObservaciones.setText(observaciones);
        } else {
            holder.textObservaciones.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listaAsistencias != null ? listaAsistencias.size() : 0;
    }

    public void actualizarLista(List<AsistenciaPorMes> nuevasAsistencias) {
        this.listaAsistencias = nuevasAsistencias;
        notifyDataSetChanged();
    }

    static class AsistenciaViewHolder extends RecyclerView.ViewHolder {

        TextView textFecha, textEstado, textObservaciones;

        public AsistenciaViewHolder(@NonNull View itemView) {
            super(itemView);
            textFecha = itemView.findViewById(R.id.textFecha);
            textEstado = itemView.findViewById(R.id.textEstado);
            textObservaciones = itemView.findViewById(R.id.textObservaciones);
        }
    }
}
