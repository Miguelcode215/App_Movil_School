package com.example.schoolapp.core.network;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.inicio.model.AsistenciaResumen;
import com.example.schoolapp.features.autenticacion.model.LoginRequest;
import com.example.schoolapp.features.autenticacion.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login (@Body LoginRequest loginRequest);

    @GET("obtenerAlumnoMovil")
    Call<List<Alumno>> obtenerAlumnos();

    @GET("resumenAsistencia/{id}")
    Call<AsistenciaResumen> obtenerResumenAsistencia(@Path("id") int idAlumno);

}
