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
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false); // 🔄 nuevo

    public ResumenAsistenciaViewModel(@NonNull Application application) {
        super(application);
        repository = new ResumenAsistenciaRepository(application);
    }

    public LiveData<AsistenciaResumen> getResumenLiveData() {
        return resumenLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void cargarResumenAsistencia(int idAlumno) {
        isLoading.setValue(true); // ⏳ iniciar loading

        repository.obtenerResumenAsistencia(idAlumno, new ResumenAsistenciaRepository.ResumenCallback() {
            @Override
            public void onSuccess(AsistenciaResumen resumen) {
                isLoading.postValue(false); // ✅ termina loading
                resumenLiveData.postValue(resumen);
            }

            @Override
            public void onError(Throwable t) {
                isLoading.postValue(false); // ❌ también termina loading si falla
                Log.e("ResumenAsistenciaVM", "Error al obtener resumen", t);
                resumenLiveData.postValue(null);
            }
        });
    }
}
