package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_gerente_inventario_insertar extends Fragment {

    private TextView header, cod_lab, desc_lab, precio_lab, cant_lab;

    private EditText codigo_in, desc_in, precio_in, cant_int;

    private Button cancelar, ingresar;



    //URL para enviar productos
    //"http://192.168.0.4/tesis_con/public/productos/create";
    private String url_enviar_pro = "https://0f1b-212-8-252-183.ngrok-free.app/tesis_con/public/productos/create";

    NavController navController;

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
        header=view.findViewById(R.id.cliente_nuevo);
        cod_lab=view.findViewById(R.id.nom_label);
        desc_lab=view.findViewById(R.id.ape_label);
        precio_lab=view.findViewById(R.id.ced_label);
        cant_lab=view.findViewById(R.id.tel_label);

        //Inputs
        codigo_in=view.findViewById(R.id.nom_input);
        desc_in=view.findViewById(R.id.ape_input);
        precio_in=view.findViewById(R.id.ced_input);
        cant_int=view.findViewById(R.id.sex_input);

        //Botones
        cancelar=view.findViewById(R.id.cancelar);

        navController = Navigation.findNavController(view);

        ingresar=view.findViewById(R.id.ingresar_cliente);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar_producto();
                navController.navigate(R.id.action_fragment_gerente_inventario_insertar_to_fragment_gerente_inventario);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_fragment_gerente_inventario_insertar_to_fragment_gerente_inventario);
            }
        });

    }

    private void insertar_producto(){
        //Se declaran los varoles.
        String codigo = codigo_in.getText().toString().trim();
        String descripcion = desc_in.getText().toString().trim();
        String precio = precio_in.getText().toString().trim();
        String cantidad = cant_int.getText().toString().trim();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty()){

            float preciop = Float.parseFloat(precio);
            int cantidadp = Integer.parseInt(cantidad);

            RequestQueue queue = Volley.newRequestQueue(getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("cod_producto",codigo);
                jsonObject.put("des_producto",descripcion);
                jsonObject.put("pre_producto",preciop);
                jsonObject.put("can_producto", cantidadp);
            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_enviar_pro, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                }
            });

            queue.add(jsonObjectRequest);

        }else {
            Toast.makeText(getContext(), "Campos Vacios", Toast.LENGTH_SHORT).show();
        }


    }
    /*
    private void clear(){


        codigo_in=view.findViewById(R.id.cod_input);
        desc_in=view.findViewById(R.id.descrip_input);
        precio_in
        cant_int

    }*/




}