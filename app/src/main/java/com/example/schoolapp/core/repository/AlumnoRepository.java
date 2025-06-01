package com.example.schoolapp.core.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.core.model.AlumnoConApoderadoResponse;
import com.example.schoolapp.core.model.Apoderado;
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

    public void obtenerAlumnosConApoderado(
            MutableLiveData<List<Alumno>> alumnosLiveData,
            MutableLiveData<Apoderado> apoderadoLiveData,
            MutableLiveData<Boolean> isError,
            MutableLiveData<Boolean> isLoading) {

        apiService.obtenerAlumnosConApoderado().enqueue(new Callback<AlumnoConApoderadoResponse>() {
            @Override
            public void onResponse(Call<AlumnoConApoderadoResponse> call, Response<AlumnoConApoderadoResponse> response) {
                isLoading.postValue(false); // Finaliza el loading

                if (response.isSuccessful() && response.body() != null) {
                    alumnosLiveData.postValue(response.body().getAlumnos());

                    Apoderado apo = response.body().getApoderado();
                    if (apo != null) {
                        apoderadoLiveData.postValue(apo);
                    }

                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<AlumnoConApoderadoResponse> call, Throwable t) {
                isLoading.postValue(false); // Finaliza también si falla
                isError.postValue(true);
            }
        });
    }
}

