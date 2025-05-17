package com.example.schoolapp.data.model;

public class Asistencia {
    private int id_Asistencia;
    private int id_Alumno;
    private String Fecha_asistencia;
    private String Observaciones_asistencia;
    private String Asistencia;

    public int getId_Asistencia() {
        return id_Asistencia;
    }

    public int getId_Alumno() {
        return id_Alumno;
    }

    public String getFecha_asistencia() {
        return Fecha_asistencia;
    }

    public String getObservaciones_asistencia() {
        return Observaciones_asistencia;
    }

    public String getAsistencia() {
        return Asistencia;
    }
}
