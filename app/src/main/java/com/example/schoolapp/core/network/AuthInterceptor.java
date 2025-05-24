package com.example.schoolapp.core.network;

import android.content.Context;
import android.util.Log;

import com.example.schoolapp.core.utils.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SharedPrefManager.getInstance(context).getAuthToken();

        Request originalRequest = chain.request();
        if (token == null) {
            Log.d("Interceptor", "Token nulo, enviando solicitud sin Authorization");
            return chain.proceed(originalRequest);
        }

        Log.d("Interceptor", "Usando token: " + token); // <-- IMPORTANTE
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
