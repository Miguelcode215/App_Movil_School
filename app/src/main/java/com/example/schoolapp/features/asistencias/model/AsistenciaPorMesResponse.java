package com.example.schoolapp.features.asistencias.model;

import java.util.List;

public class AsistenciaPorMesResponse {
    private AlumnoInfo alumno;
    private int mes;
    private int anio;
    private List<AsistenciaPorMes> asistencias;

    public AlumnoInfo getAlumno() { return alumno; }
    public int getMes() { return mes; }
    public int getAnio() { return anio; }
    public List<AsistenciaPorMes> getAsistencias() { return asistencias; }

    public static class AlumnoInfo {
        private int id;
        private String nombre_completo;
        private String grado;

        public int getId() { return id; }
        public String getNombreCompleto() { return nombre_completo; }
        public String getGrado() { return grado; }
    }
}

