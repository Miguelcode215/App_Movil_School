package com.example.schoolapp.features.opciones.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.core.network.ApiClient;
import com.example.schoolapp.core.network.ApiService;
import com.example.schoolapp.features.opciones.model.CambioPasswordRequest;
import com.example.schoolapp.features.opciones.model.CuentaUsuario;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaRepository {
    private ApiService apiService;

    public CuentaRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public void obtenerCuentaUsuario(MutableLiveData<CuentaUsuario> cuentaLiveData,
                                     MutableLiveData<Boolean> isError,
                                     MutableLiveData<Boolean> isLoading) {
        isLoading.postValue(true);

        apiService.obtenerCuentaUsuario().enqueue(new Callback<CuentaUsuario>() {
            @Override
            public void onResponse(Call<CuentaUsuario> call, Response<CuentaUsuario> response) {
                isLoading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    cuentaLiveData.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<CuentaUsuario> call, Throwable t) {
                isLoading.postValue(false);
                isError.postValue(true);
            }
        });
    }

    public void cambiarPassword(String actual, String nueva, String confirmar,
                                MutableLiveData<Boolean> success,
                                MutableLiveData<String> error,
                                MutableLiveData<Boolean> isLoading) {

        isLoading.postValue(true);

        Map<String, String> body = new HashMap<>();
        body.put("password_actual", actual);
        body.put("nueva_password", nueva);
        body.put("nueva_password_confirmation", confirmar); // Laravel lo espera así

        apiService.cambiarPassword(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    isLoading.postValue(false);
                    if (response.isSuccessful()) {
                        success.postValue(true);
                    } else {
                        error.postValue("Error al actualizar la contraseña. Revise los datos.");
                    }
                }, 500); // ⏱ medio segundo de retraso
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    isLoading.postValue(false);
                    error.postValue("Fallo de conexión o error inesperado.");
                }, 500); // también aplicar retardo en caso de error
            }
        });
    }


}
