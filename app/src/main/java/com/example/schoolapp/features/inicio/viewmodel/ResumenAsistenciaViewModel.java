package com.example.schoolapp.features.inicio.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.features.inicio.model.AsistenciaResumen;
import com.example.schoolapp.features.inicio.repository.ResumenAsistenciaRepository;

public class ResumenAsistenciaViewModel extends AndroidViewModel {

    private final ResumenAsistenciaRepository repository;
    private final MutableLiveData<AsistenciaResumen> resumenLiveData = new MutableLiveData<>();

    public ResumenAsistenciaViewModel(@NonNull Application application) {
        super(application);
        repository = new ResumenAsistenciaRepository(application);
    }

    public LiveData<AsistenciaResumen> getResumenLiveData() {
        return resumenLiveData;
    }

    public void cargarResumenAsistencia(int idAlumno) {
        repository.obtenerResumenAsistencia(idAlumno, new ResumenAsistenciaRepository.ResumenCallback() {
            @Override
            public void onSuccess(AsistenciaResumen resumen) {
                resumenLiveData.postValue(resumen);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("ResumenAsistenciaVM", "Error al obtener resumen", t);
                resumenLiveData.postValue(null);
            }
        });
    }
}
