package com.example.schoolapp.core.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.core.repository.AlumnoRepository;

import java.util.List;

public class AlumnoViewModel extends ViewModel {
    private AlumnoRepository repository;
    private MutableLiveData<List<Alumno>> alumnosLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public void init(android.content.Context context) {
        if (repository == null) {
            repository = new AlumnoRepository(context);
        }
    }

    public LiveData<List<Alumno>> getAlumnos() {
        return alumnosLiveData;
    }

    public LiveData<Boolean> getError() {
        return isError;
    }

    public void cargarAlumnos() {
        if (repository != null) {
            repository.obtenerAlumnos(alumnosLiveData, isError);
        } else {
            isError.setValue(true);
        }
    }

}

