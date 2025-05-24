package com.example.schoolapp.features.autenticacion.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.features.autenticacion.model.LoginResponse;
import com.example.schoolapp.features.autenticacion.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private AuthRepository authRepository;

    public void init(Context context) {
        if (authRepository == null) {
            authRepository = new AuthRepository(context);
        }
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return authRepository.login(email, password);
    }
}

