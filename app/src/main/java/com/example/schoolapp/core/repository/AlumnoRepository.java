package com.example.schoolapp.core.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.core.network.ApiClient;
import com.example.schoolapp.core.network.ApiService;
import com.example.schoolapp.core.model.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRepository {
    private ApiService apiService;

    public AlumnoRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
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
