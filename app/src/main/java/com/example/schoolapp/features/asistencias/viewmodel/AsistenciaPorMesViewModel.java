package com.example.schoolapp.features.asistencias.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.features.asistencias.model.AsistenciaPorMesResponse;
import com.example.schoolapp.features.asistencias.repository.AsistenciaPorMesRepository;

public class AsistenciaPorMesViewModel extends AndroidViewModel {

    private final AsistenciaPorMesRepository repository;
    private final MutableLiveData<AsistenciaPorMesResponse> asistenciaResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>(false);

    public AsistenciaPorMesViewModel(@NonNull Application application) {
        super(application);
        repository = new AsistenciaPorMesRepository(application);
    }

    public void cargarAsistencias(int idAlumno, int mes) {
        isLoading.setValue(true);
        isError.setValue(false);

        repository.obtenerAsistenciasPorMes(idAlumno, mes).observeForever(response -> {
            isLoading.setValue(false);
            if (response != null) {
                asistenciaResponse.setValue(response);
            } else {
                isError.setValue(true);
            }
        });
    }

    public LiveData<AsistenciaPorMesResponse> getAsistenciaResponse() {
        return asistenciaResponse;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }
}
