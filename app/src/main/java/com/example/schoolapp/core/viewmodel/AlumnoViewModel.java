package com.example.schoolapp.core.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.model.Apoderado;
import com.example.schoolapp.core.repository.AlumnoRepository;

import java.util.List;

public class AlumnoViewModel extends ViewModel {
    private AlumnoRepository repository;
    private MutableLiveData<List<Alumno>> alumnosLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Apoderado> apoderadoLiveData = new MutableLiveData<>();

    public void init(android.content.Context context) {
        if (repository == null) {
            repository = new AlumnoRepository(context);
        }
    }

    public LiveData<Apoderado> getApoderado() {
        return apoderadoLiveData;
    }

    public LiveData<List<Alumno>> getAlumnos() {
        return alumnosLiveData;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void cargarAlumnos() {
        isError.setValue(false);
        isLoading.setValue(true);

        if (repository != null) {
            repository.obtenerAlumnosConApoderado(
                    alumnosLiveData,
                    apoderadoLiveData,
                    isError,
                    isLoading);
        } else {
            isError.setValue(true);
            isLoading.setValue(false);
        }
    }

}


