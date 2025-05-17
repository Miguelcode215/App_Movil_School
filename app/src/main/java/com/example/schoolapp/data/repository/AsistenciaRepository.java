package com.example.schoolapp.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.schoolapp.data.api.ApiClient;
import com.example.schoolapp.data.api.ApiService;
import com.example.schoolapp.data.model.Asistencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsistenciaRepository {

    private final ApiService apiService;

    public AsistenciaRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public void obtenerAsistencias(int idAlumno, AsistenciasCallback callback) {
        Log.d("AsistenciaRepo", "Llamando Retrofit para alumno: " + idAlumno);
        apiService.obtenerAsistenciaPorAlumno(idAlumno).enqueue(new Callback<List<Asistencia>>() {
            @Override
            public void onResponse(Call<List<Asistencia>> call, Response<List<Asistencia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("AsistenciaRepo", "Respuesta vacía o error: " + response.code());
                    callback.onError(new Exception("Respuesta no exitosa o sin cuerpo"));
                }
            }

            @Override
            public void onFailure(Call<List<Asistencia>> call, Throwable t) {
                Log.e("AsistenciaRepo", "Error de red", t);
                callback.onError(t);
            }
        });
    }

    public interface AsistenciasCallback {
        void onSuccess(List<Asistencia> asistencias);
        void onError(Throwable t);
    }
}
