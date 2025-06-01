package com.example.schoolapp.features.inicio.model;

import java.io.Serializable;
import java.util.Map;

public class AsistenciaResumen implements Serializable {

    private int id_alumno;
    private String nombre_completo;
    private String grado;
    private float porcentaje_asistencia;
    private int conteo_faltas;
    private Map<String, Integer> conteo;
    private Map<String, Float> distribucion_porcentual;

    // Getters
    public int getId_alumno() {
        return id_alumno;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public String getGrado() {
        return grado;
    }

    public float getPorcentaje_asistencia() {
        return porcentaje_asistencia;
    }

    public int getConteo_faltas() {
        return conteo_faltas;
    }

    public Map<String, Integer> getConteo() {
        return conteo;
    }

    public Map<String, Float> getDistribucion_porcentual() {
        return distribucion_porcentual;
    }
}
