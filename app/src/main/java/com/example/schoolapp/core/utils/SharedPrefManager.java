package com.example.schoolapp.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "schoolapp_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_ROLE = "user_role";

    private static SharedPrefManager instance;
    private final SharedPreferences prefs;

    // Constructor privado (Singleton)
    private SharedPrefManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Singleton instance
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    // Guardar token
    public void saveAuthToken(String token) {
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    // Obtener token
    public String getAuthToken() {
        return prefs.getString(KEY_AUTH_TOKEN, null);
    }

    // Guardar rol
    public void saveRole(String role) {
        prefs.edit().putString(KEY_ROLE, role).apply();
    }

    // Obtener rol
    public String getRole() {
        return prefs.getString(KEY_ROLE, null);
    }

    // Limpiar datos (logout)
    public void clear() {
        prefs.edit().clear().apply();
    }
}
