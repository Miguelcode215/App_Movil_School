package com.example.schoolapp.data.api;

import com.example.schoolapp.data.model.Alumno;
import com.example.schoolapp.data.model.Asistencia;
import com.example.schoolapp.data.model.LoginRequest;
import com.example.schoolapp.data.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login (@Body LoginRequest loginRequest);

    @GET("obtenerAlumnoMovil")
    Call<List<Alumno>> obtenerAlumnos();

    @GET("obtenerAsistencia/{id}")
    Call<List<Asistencia>> obtenerAsistenciaPorAlumno(@Path("id") int idAlumno);

}
