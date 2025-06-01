package com.example.schoolapp.features.opciones.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolapp.R;
import com.example.schoolapp.core.viewmodel.AlumnoViewModel;
import com.example.schoolapp.features.opciones.viewmodel.CuentaViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MiCuentaFragment extends Fragment {

    private TextView textNombreApoderado, textNumeroTelefono, textUsuarioNombre, textUsuarioEmail;
    private Button btnCambiarPassword;

    private AlumnoViewModel alumnoViewModel;
    private CuentaViewModel cuentaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.opciones_fragment_mi_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UI
        textNombreApoderado = view.findViewById(R.id.textNombreApoderado);
        textNumeroTelefono = view.findViewById(R.id.textNumeroTelefono);
        textUsuarioNombre = view.findViewById(R.id.textUsuarioNombre);
        textUsuarioEmail = view.findViewById(R.id.textUsuarioEmail);
        btnCambiarPassword = view.findViewById(R.id.btnCambiarPassword);
        View loadingOverlay = view.findViewById(R.id.loadingOverlay);

        // ViewModels
        alumnoViewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);
        cuentaViewModel = new ViewModelProvider(this).get(CuentaViewModel.class);
        cuentaViewModel.init(requireContext());
        cuentaViewModel.cargarCuentaUsuario();

        // Observadores
        alumnoViewModel.getApoderado().observe(getViewLifecycleOwner(), apoderado -> {
            textNombreApoderado.setText(apoderado.getNombreCompleto());
            textNumeroTelefono.setText(apoderado.getTelefono());
        });

        cuentaViewModel.getCuentaUsuario().observe(getViewLifecycleOwner(), cuenta -> {
            if (cuenta != null) {
                textUsuarioNombre.setText(cuenta.getName());
                textUsuarioEmail.setText(cuenta.getEmail());
            }
        });

        cuentaViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (loadingOverlay != null) {
                loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        cuentaViewModel.getPasswordSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                mostrarMensaje("Contraseña actualizada correctamente");
            }
        });

        cuentaViewModel.getPasswordError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                mostrarMensaje(errorMsg);
            }
        });

        // Botón cambiar contraseña
        btnCambiarPassword.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.opciones_dialog_cambiar_password, null);

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .setCancelable(false)
                    .create();

            // Referencias
            TextInputEditText inputActual = dialogView.findViewById(R.id.inputPasswordActual);
            TextInputEditText inputNueva = dialogView.findViewById(R.id.inputPasswordNueva);
            TextInputEditText inputConfirmar = dialogView.findViewById(R.id.inputPasswordConfirmar);
            Button btnConfirmar = dialogView.findViewById(R.id.btnConfirmar);
            Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
            View loadingOverlayDialog = dialogView.findViewById(R.id.loadingOverlayDialog); // debe existir

            // Observa el loading dentro del diálogo
            cuentaViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
                if (loadingOverlayDialog != null) {
                    loadingOverlayDialog.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
            });

            btnCancelar.setOnClickListener(view1 -> dialog.dismiss());

            btnConfirmar.setOnClickListener(view1 -> {
                String actual = inputActual.getText() != null ? inputActual.getText().toString().trim() : "";
                String nueva = inputNueva.getText() != null ? inputNueva.getText().toString().trim() : "";
                String confirmar = inputConfirmar.getText() != null ? inputConfirmar.getText().toString().trim() : "";

                if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                    mostrarMensaje("Todos los campos son obligatorios");
                    return;
                }

                if (!nueva.equals(confirmar)) {
                    mostrarMensaje("La nueva contraseña no coincide con la confirmación");
                    return;
                }

                cuentaViewModel.cambiarPassword(actual, nueva, confirmar);
            });

            // Cerrar diálogo al éxito
            cuentaViewModel.getPasswordSuccess().observe(getViewLifecycleOwner(), success -> {
                if (success != null && success) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        });
    }

    private void mostrarMensaje(String mensaje) {
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), mensaje, android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}
