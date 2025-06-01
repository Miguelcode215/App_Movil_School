package com.example.schoolapp.features.inicio.model;

public class ConteoAsistencias {
    public int Presente;
    public int Atrasado;
    public int Ausente;
    public int Justificado;

    public ConteoAsistencias(int p, int a, int au, int j) {
        this.Presente = p;
        this.Atrasado = a;
        this.Ausente = au;
        this.Justificado = j;
    }
}