package com.example.schoolapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.data.model.Asistencia;
import com.example.schoolapp.data.repository.AsistenciaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsistenciaViewModel extends AndroidViewModel {

    private final AsistenciaRepository repository;
    private final Map<Integer, MutableLiveData<List<Asistencia>>> asistenciasPorAlumno = new HashMap<>();

    public AsistenciaViewModel(@NonNull Application application) {
        super(application);
        repository = new AsistenciaRepository(application);
    }

    public LiveData<List<Asistencia>> cargarAsistencias(int idAlumno) {
        // 🔒 Evita sobreescribir si ya existe
        if (asistenciasPorAlumno.containsKey(idAlumno)) {
            Log.d("AsistenciaVM", "LiveData ya existente para alumno " + idAlumno);
            return asistenciasPorAlumno.get(idAlumno);
        }

        Log.d("AsistenciaVM", "Creando nuevo LiveData y llamando Retrofit para alumno " + idAlumno);
        MutableLiveData<List<Asistencia>> liveData = new MutableLiveData<>();
        asistenciasPorAlumno.put(idAlumno, liveData);

        repository.obtenerAsistencias(idAlumno, new AsistenciaRepository.AsistenciasCallback() {
            @Override
            public void onSuccess(List<Asistencia> asistencias) {
                Log.d("AsistenciaVM", "Asistencias obtenidas: " + asistencias.size());
                liveData.postValue(asistencias);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("AsistenciaVM", "Error al obtener asistencias", t);
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public LiveData<List<Asistencia>> getAlumnosLiveData(int idAlumno) {
        return asistenciasPorAlumno.get(idAlumno);
    }

    public int calcularPorcentajeAsistencia(List<Asistencia> asistencias) {
        if (asistencias == null || asistencias.isEmpty()) return 0;

        int totalConsiderados = 0;
        float totalPuntaje = 0;

        for (Asistencia asistencia : asistencias) {
            String tipo = asistencia.getAsistencia();
            if (tipo == null) continue;

            tipo = tipo.toLowerCase();

            if ("presente".equals(tipo)) {
                totalPuntaje += 1.0;
                totalConsiderados++;
            } else if ("atrasado".equals(tipo)) {
                totalPuntaje += 0.5;
                totalConsiderados++;
            } else if ("ausente".equals(tipo)) {
                totalPuntaje += 0;
                totalConsiderados++;
            }
        }

        return totalConsiderados == 0 ? 0 : Math.round((totalPuntaje / totalConsiderados) * 100);
    }

    public int contarFaltas(List<Asistencia> asistencias) {
        if (asistencias == null) return 0;

        int faltas = 0;
        for (Asistencia asistencia : asistencias) {
            if ("ausente".equalsIgnoreCase(asistencia.getAsistencia())) {
                faltas++;
            }
        }
        return faltas;
    }
}
