package com.example.schoolapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolapp.data.model.LoginResponse;
import com.example.schoolapp.data.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return authRepository.login(email, password);
    }
}
