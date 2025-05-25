package com.example.schoolapp.features.asistencias.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AsistenciaPorMes {
    @SerializedName("Fecha_asistencia")
    private String fecha;

    @SerializedName("Asistencia")
    private String estado;

    @SerializedName("Observaciones_asistencia")
    private String observaciones;

    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getObservaciones() { return observaciones; }

    public String getFechaFormateada() {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            Date date = originalFormat.parse(fecha);
            return targetFormat.format(date);
        } catch (Exception e) {
            return fecha; // fallback
        }
    }


}
