package com.example.schoolapp.presentation.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolapp.MainActivity;
import com.example.schoolapp.R;
import com.example.schoolapp.utils.SharedPrefManager;
import com.example.schoolapp.viewmodel.AuthViewModel;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Inicializamos los componentes txt
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_inicio);

        // Inicializamos el ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btn_login.setOnClickListener(v -> {
            String emailInput = email.getText().toString().trim();
            String passInput = password.getText().toString().trim();

            Log.d("DEBUG", emailInput + passInput);

            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Por Favor complete los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamamos al loguin de ViewModel
            authViewModel.login(emailInput, passInput).observe(this, loginResponse -> {
                Log.d("LOGIN", "Intentando login con: " + emailInput + " / " + passInput);
                if (loginResponse != null && loginResponse.getToken() != null) {
                    //Guardamos el token y el rol
                    SharedPrefManager.getInstance(this).saveAuthToken(loginResponse.getToken());
                    SharedPrefManager.getInstance(this).saveRole(loginResponse.getRole());

                    Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // despues se redirige al Dashboard(MainActivity)
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}