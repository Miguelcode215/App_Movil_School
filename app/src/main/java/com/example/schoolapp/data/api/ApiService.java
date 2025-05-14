package com.example.schoolapp.data.api;

import com.example.schoolapp.data.model.LoginRequest;
import com.example.schoolapp.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login (@Body LoginRequest loginRequest);

}
