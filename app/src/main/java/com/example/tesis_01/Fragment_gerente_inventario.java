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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Fragment_gerente_inventario extends Fragment {

    TextView cabecera;
    Button retroceder, ingresar;
    RecyclerView inventario_recy;

    ArrayList<Producto> productos;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gerente_inventario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productos = new ArrayList<Producto>();

        cabecera=view.findViewById(R.id.inventario_head);

        retroceder = view.findViewById(R.id.inv_volver);

        ingresar = view.findViewById(R.id.ingresar_pro);

        inventario_recy = view.findViewById(R.id.inven_recyView);

        productos.add(new Producto("Caraoatas", 15635.00f, 55, "1kILO CARAOTAS"));
        build_products_recycleview();

    }

    //Se encvarga de mandar el recycleview
    private void build_products_recycleview(){

        //se inicia el adaptador de la clase
        Productos_RecAdapter productos_view = new Productos_RecAdapter(productos, getContext());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        inventario_recy.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        inventario_recy.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        inventario_recy.setAdapter(productos_view);

    }



}