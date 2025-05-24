package com.example.schoolapp.core.model;

import java.io.Serializable;

public class Alumno implements Serializable{
    private int id_Alumno;
    private String Nombre;
    private String Apellido;
    private String DNI;
    private String FechaNacimiento;
    private String Estado_Alumno;
    private int id_Apoderado;
    private int id_Grado_anio;
    private GradoAnio grados;

    // Getters y Setters
    public int getId_Alumno() { return id_Alumno; }
    public String getNombre() { return Nombre; }
    public String getApellido() { return Apellido; }
    public String getNombreCompleto() { return Nombre + " " + Apellido; }
    public int getId_Grado_anio() { return id_Grado_anio; }
    public GradoAnio getGrados() {
        return grados;
    }

}
