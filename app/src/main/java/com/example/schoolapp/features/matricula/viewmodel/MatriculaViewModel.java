// MatriculaViewModel.java
package com.example.schoolapp.features.matricula.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.repository.AlumnoRepository;

import java.util.List;

public class MatriculaViewModel extends ViewModel {

    private AlumnoRepository repository;
    private final MutableLiveData<List<Alumno>> alumnosLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> nombreApoderado = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public void init(Context context) {
        if (repository == null) {
            repository = new AlumnoRepository(context);
        }
    }

    public void cargarDatosMatricula() {
        isLoading.setValue(true);
        isError.setValue(false);

        repository.obtenerAlumnosConApoderado(alumnosLiveData, nombreApoderado, isError, isLoading);

        // Ocultamos loading después de cargar (puede mejorarse con callback)
        alumnosLiveData.observeForever(list -> isLoading.setValue(false));
    }

    public LiveData<List<Alumno>> getAlumnos() {
        return alumnosLiveData;
    }

    public LiveData<String> getNombreApoderado() {
        return nombreApoderado;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}