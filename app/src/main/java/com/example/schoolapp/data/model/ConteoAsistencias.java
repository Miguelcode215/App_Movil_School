package com.example.schoolapp.data.model;

public class ConteoAsistencias {
    public int presente;
    public int atrasado;
    public int ausente;
    public int justificado;

    public ConteoAsistencias(int p, int a, int au, int j) {
        this.presente = p;
        this.atrasado = a;
        this.ausente = au;
        this.justificado = j;
    }
}