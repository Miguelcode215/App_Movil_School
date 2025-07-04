package com.example.schoolapp.core.network;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.model.AlumnoConApoderadoResponse;
import com.example.schoolapp.features.asistencias.model.AsistenciaPorMesResponse;
import com.example.schoolapp.features.inicio.model.AsistenciaResumen;
import com.example.schoolapp.features.autenticacion.model.LoginRequest;
import com.example.schoolapp.features.autenticacion.model.LoginResponse;
import com.example.schoolapp.features.opciones.model.CambioPasswordRequest;
import com.example.schoolapp.features.opciones.model.CuentaUsuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login (@Body LoginRequest loginRequest);

    @GET("obtenerAlumnoMovil")
    Call<AlumnoConApoderadoResponse> obtenerAlumnosConApoderado();


    @GET("resumenAsistencia/{id}")
    Call<AsistenciaResumen> obtenerResumenAsistencia(@Path("id") int idAlumno);

    @GET("asistenciasPorMes/{id_alumno}/{mes}")
    Call<AsistenciaPorMesResponse> obtenerAsistenciasPorMes(
            @Path("id_alumno") int idAlumno,
            @Path("mes") int mes
    );

    @GET("user")
    Call<CuentaUsuario> obtenerCuentaUsuario();

    @PUT("ActualizarPassword")
    Call<Void> cambiarPassword(@Body Map<String, String> body);


}
