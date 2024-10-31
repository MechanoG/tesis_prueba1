package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment_gerente_inventario_insertar extends Fragment {

    private TextView header, cod_lab, desc_lab, precio_lab, cant_lab;

    private EditText codigo_in, desc_in, precio_in, cant_int;

    private Button cancelar, ingresar;

    //URL para enviar productos
    String url_enviar_pro = "http://10.0.2.2:80/tesis_con/public/productos/create";

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gerente_inventario_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Labels
        header=view.findViewById(R.id.producto_nuevo);
        cod_lab=view.findViewById(R.id.cod_label);
        desc_lab=view.findViewById(R.id.descri_label);
        precio_lab=view.findViewById(R.id.precio_label);
        cant_lab=view.findViewById(R.id.cant_label);

        //Inputs
        codigo_in=view.findViewById(R.id.cod_input);
        desc_in=view.findViewById(R.id.descrip_input);
        precio_in=view.findViewById(R.id.precio_input);
        cant_lab=view.findViewById(R.id.cant_input);

        //Botones
        cancelar=view.findViewById(R.id.cancelar);
        ingresar=view.findViewById(R.id.ingresar_producto);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar_pedido();
            }
        });




    }

    private void insertar_pedido(){
        //Se declaran los varoles.
        String codigo = codigo_in.getText().toString().trim();
        String descripcion = desc_in.getText().toString().trim();
        String precio = precio_in.getText().toString().trim();
        String cantidad = cant_lab.getText().toString().trim();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty()){

            Toast.makeText(getContext(), "Datos Validados", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Campos Vacios", Toast.LENGTH_SHORT).show();
        }


    }




}