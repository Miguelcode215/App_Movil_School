package com.example.schoolapp.features.inicio.model;

public class PorcentajeAsistencias {
    public float Presente;
    public float Atrasado;
    public float Ausente;
    public float Justificado;

    public PorcentajeAsistencias(float p, float a, float au, float j) {
        this.Presente = p;
        this.Atrasado = a;
        this.Ausente = au;
        this.Justificado = j;
    }
}