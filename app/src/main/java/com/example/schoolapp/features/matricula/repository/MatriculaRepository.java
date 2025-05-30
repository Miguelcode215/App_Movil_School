// MatriculaRepository.java
package com.example.schoolapp.features.matricula.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.model.AlumnoConApoderadoResponse;
import com.example.schoolapp.core.network.ApiClient;
import com.example.schoolapp.core.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaRepository {

    private final ApiService apiService;

    public MatriculaRepository(Context context) {
        this.apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public void obtenerDatosMatricula(MutableLiveData<List<Alumno>> alumnosLiveData,
                                      MutableLiveData<String> nombreApoderado,
                                      MutableLiveData<Boolean> isError) {

        apiService.obtenerAlumnosConApoderado().enqueue(new Callback<AlumnoConApoderadoResponse>() {
            @Override
            public void onResponse(Call<AlumnoConApoderadoResponse> call, Response<AlumnoConApoderadoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alumnosLiveData.postValue(response.body().getAlumnos());
                    AlumnoConApoderadoResponse.Apoderado apo = response.body().getApoderado();
                    if (apo != null) {
                        nombreApoderado.postValue(apo.getNombreCompleto());
                    }
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<AlumnoConApoderadoResponse> call, Throwable t) {
                isError.postValue(true);
            }
        });
    }
}
