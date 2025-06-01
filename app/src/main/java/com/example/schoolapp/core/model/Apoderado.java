package com.example.schoolapp.core.model;

public class Apoderado {
    private String nombre;
    private String apellido;
    private String telefono;

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }
}
