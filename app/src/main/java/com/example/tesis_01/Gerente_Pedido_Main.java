package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Gerente_Pedido_Main extends Fragment {
    ArrayList<Pedidos_lista> pedidosLista;
    RecyclerView lista_pedidos;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
        */
    //se sobre escribe metodo oncreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gerente__pedido__main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lista_pedidos=view.findViewById(R.id.pedidos_lista);
        lista_pedidos.setHasFixedSize(true);
        lista_pedidos.setLayoutManager(new LinearLayoutManager(getContext()/*this*/));

        pedidosLista = new ArrayList<Pedidos_lista>();
        pedidosLista.add(new Pedidos_lista( "Por favor funciona"));

        for (int i=20; i>0; i-- ){
            pedidosLista.add(new Pedidos_lista("Soy el maximo mamahuevo"));
        }

        pedidosLista.add(new Pedidos_lista("Fin"));


        Pedidos_lista_Adapter adaptador_pedidos = new Pedidos_lista_Adapter(pedidosLista, getContext());
        lista_pedidos.setAdapter(adaptador_pedidos);
    }
}