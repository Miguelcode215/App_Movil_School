package com.example.schoolapp.features.inicio.repository;

import android.content.Context;
import android.util.Log;

import com.example.schoolapp.core.network.ApiClient;
import com.example.schoolapp.core.network.ApiService;
import com.example.schoolapp.features.inicio.model.AsistenciaResumen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumenAsistenciaRepository {

    private final ApiService apiService;

    public ResumenAsistenciaRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public void obtenerResumenAsistencia(int idAlumno, ResumenCallback callback) {
        Log.d("ResumenAsistenciaRepo", "Llamando a resumenAsistencia para alumno: " + idAlumno);
        apiService.obtenerResumenAsistencia(idAlumno).enqueue(new Callback<AsistenciaResumen>() {
            @Override
            public void onResponse(Call<AsistenciaResumen> call, Response<AsistenciaResumen> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("ResumenAsistenciaRepo", "Respuesta vacía o error: " + response.code());
                    callback.onError(new Exception("Respuesta no exitosa o sin cuerpo"));
                }
            }

            @Override
            public void onFailure(Call<AsistenciaResumen> call, Throwable t) {
                Log.e("ResumenAsistenciaRepo", "Error de red", t);
                callback.onError(t);
            }
        });
    }

    // Interfaz callback para devolver el resultado al ViewModel
    public interface ResumenCallback {
        void onSuccess(AsistenciaResumen resumen);
        void onError(Throwable t);
    }
}
