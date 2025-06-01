package com.example.schoolapp.features.opciones.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolapp.R;
import com.example.schoolapp.features.autenticacion.ui.Login;
import com.example.schoolapp.features.opciones.viewmodel.CuentaViewModel;

public class OpcionesFragment extends Fragment {

    private Button btnMiCuenta, btnCerrarSesion;
    private CuentaViewModel cuentaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.opciones_fragment_opciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnMiCuenta = view.findViewById(R.id.btnMiCuenta);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        cuentaViewModel = new ViewModelProvider(this).get(CuentaViewModel.class);

        // Navegar a la vista de "Mi Cuenta"
        btnMiCuenta.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container, new MiCuentaFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Cerrar sesión (implementación real se hará más adelante)
        btnCerrarSesion.setOnClickListener(v -> {
            cuentaViewModel.cerrarSesion(requireContext());

            // Redirigir al Login y limpiar el backstack
            Intent intent = new Intent(requireContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Toast.makeText(requireContext(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
        });
    }
}
