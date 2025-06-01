package com.example.schoolapp.features.opciones.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.core.utils.SharedPrefManager;
import com.example.schoolapp.features.opciones.model.CuentaUsuario;
import com.example.schoolapp.features.opciones.repository.CuentaRepository;

public class CuentaViewModel extends ViewModel {

    private CuentaRepository repository;
    private final MutableLiveData<CuentaUsuario> cuentaLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> passwordSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();


    public void init(Context context) {
        if (repository == null) {
            repository = new CuentaRepository(context);
        }
    }

    public LiveData<CuentaUsuario> getCuentaUsuario() {
        return cuentaLiveData;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getPasswordSuccess() {
        return passwordSuccess;
    }

    public LiveData<String> getPasswordError() {
        return passwordError;
    }

    public void cargarCuentaUsuario() {
        if (repository != null) {
            repository.obtenerCuentaUsuario(cuentaLiveData, isError, isLoading);
        } else {
            isError.setValue(true);
        }
    }

    public void cambiarPassword(String actual, String nueva, String confirmar) {
        if (repository != null) {
            repository.cambiarPassword(
                    actual,
                    nueva,
                    confirmar,
                    passwordSuccess,
                    passwordError,
                    isLoading
            );
        }
    }

    public void cerrarSesion(Context context) {
        SharedPrefManager.getInstance(context).clear();
    }

}
