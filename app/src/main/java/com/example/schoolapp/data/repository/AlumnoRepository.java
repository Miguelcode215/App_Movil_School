package com.example.schoolapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.data.api.ApiClient;
import com.example.schoolapp.data.api.ApiService;
import com.example.schoolapp.data.model.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRepository {
    private ApiService apiService;

    public AlumnoRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public LiveData<List<Alumno>> obtenerAlumnos() {
        MutableLiveData<List<Alumno>> alumnosLiveData = new MutableLiveData<>();

        apiService.obtenerAlumnos().enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alumnosLiveData.setValue(response.body());
                } else {
                    Log.e("AlumnoRepository", "Respuesta vacía o error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                Log.e("AlumnoRepository", "Error al obtener alumnos", t);
            }
        });

        return alumnosLiveData;
    }
    // AlumnoRepository.java
    public void obtenerAlumnos(MutableLiveData<List<Alumno>> alumnosLiveData,
                               MutableLiveData<Boolean> isError) {
        apiService.obtenerAlumnos().enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alumnosLiveData.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                isError.postValue(true);
            }
        });
    }

}
