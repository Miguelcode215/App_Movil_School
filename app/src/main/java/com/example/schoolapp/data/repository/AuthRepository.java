package com.example.schoolapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolapp.data.api.ApiClient;
import com.example.schoolapp.data.api.ApiService;
import com.example.schoolapp.data.model.LoginRequest;
import com.example.schoolapp.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private ApiService apiService;

    public AuthRepository(Context context) {
        apiService = ApiClient.getCliente(context).create(ApiService.class);
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();

        apiService.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LOGIN", "Código: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    loginResponseLiveData.postValue(response.body());
                    Log.d("LOGIN", "Login exitoso: " + response.body().getToken());
                } else {
                    loginResponseLiveData.postValue(null);
                    Log.d("LOGIN", "Fallo login: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                loginResponseLiveData.postValue(null);
                Log.e("LOGIN", "Error en onFailure: " + throwable.getMessage(), throwable);
            }
        });

        return loginResponseLiveData;
    }
}

