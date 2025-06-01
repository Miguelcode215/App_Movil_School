package com.example.schoolapp.features.opciones.model;

import com.google.gson.annotations.SerializedName;

public class CambioPasswordRequest {
    @SerializedName("password_actual")
    private String passwordActual;

    @SerializedName("nueva_password")
    private String nuevaPassword;

    @SerializedName("nueva_password_confirmation")
    private String confirmarPassword;

    public CambioPasswordRequest(String actual, String nueva, String confirmar) {
        this.passwordActual = actual;
        this.nuevaPassword = nueva;
        this.confirmarPassword = confirmar;
    }
}

