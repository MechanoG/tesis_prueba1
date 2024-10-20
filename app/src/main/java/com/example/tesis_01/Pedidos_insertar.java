package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Pedidos_insertar extends Fragment {
    NavController navController;
    TextView header;
    Button cancelar, ingresar;
  /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        header = view.findViewById(R.id.nuevo_pedido);

        //Se cra nevegador del fragment
        navController = Navigation.findNavController(view);

        //Se inicializa boton cancelar.
        cancelar = view.findViewById(R.id.Pedido_Cancelar);
        //Se declara la acciona l presionar boton
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_pedidos_insertar_to_gerente_Pedido_Main);
            }
        });



        super.onViewCreated(view, savedInstanceState);
    }
}