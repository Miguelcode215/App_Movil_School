package com.example.schoolapp.features.inicio.model;

public class PorcentajeAsistencias {
    public float presente;
    public float atrasado;
    public float ausente;
    public float justificado;

    public PorcentajeAsistencias(float p, float a, float au, float j) {
        this.presente = p;
        this.atrasado = a;
        this.ausente = au;
        this.justificado = j;
    }
}