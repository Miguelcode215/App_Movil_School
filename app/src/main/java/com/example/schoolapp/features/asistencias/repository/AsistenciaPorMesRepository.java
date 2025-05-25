package com.example.schoolapp.features.asistencias.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.core.network.ApiClient;
import com.example.schoolapp.core.network.ApiService;
import com.example.schoolapp.features.asistencias.model.AsistenciaPorMesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsistenciaPorMesRepository {
    private ApiService apiService;

    public AsistenciaPorMesRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public LiveData<AsistenciaPorMesResponse> obtenerAsistenciasPorMes(int idAlumno, int mes) {
        MutableLiveData<AsistenciaPorMesResponse> data = new MutableLiveData<>();

        apiService.obtenerAsistenciasPorMes(idAlumno, mes).enqueue(new Callback<AsistenciaPorMesResponse>() {
            @Override
            public void onResponse(Call<AsistenciaPorMesResponse> call, Response<AsistenciaPorMesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AsistenciaPorMesResponse> call, Throwable t) {
                data.postValue(null);
            }
        });

        return data;
    }
}

