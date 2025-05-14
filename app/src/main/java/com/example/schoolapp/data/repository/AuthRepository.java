package com.example.schoolapp.data.repository;

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

    public AuthRepository(){
        apiService = ApiClient.getCliente().create(ApiService.class); //Sin token al principio
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();

        apiService.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               if (response.isSuccessful() && response.body() != null) {
                   loginResponseLiveData.postValue(response.body());
               } else {
                   loginResponseLiveData.postValue(null);
               }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                loginResponseLiveData.postValue(null);
            }
        });

        return loginResponseLiveData;
    }
}
