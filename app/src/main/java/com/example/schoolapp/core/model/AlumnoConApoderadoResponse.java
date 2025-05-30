package com.example.schoolapp.core.model;

import java.util.List;

public class AlumnoConApoderadoResponse {
    private Apoderado apoderado;
    private List<Alumno> alumnos;

    public Apoderado getApoderado() {
        return apoderado;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public static class Apoderado {
        private String nombre;
        private String apellido;

        public String getNombreCompleto() {
            return nombre + " " + apellido;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }
    }
}
