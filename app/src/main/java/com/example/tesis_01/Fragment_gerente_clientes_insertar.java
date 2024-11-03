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

import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_gerente_clientes_insertar extends Fragment {

    private TextView cabezera, rif_lab, raz_lab, encar_lab, encar_num_lab;

    private EditText rif_in, raz_in, encar_in, encar_num_int;

    private Button cancelar, ingresar;

    NavController navController;

    //URL DE CONEXION A BASE DE DATOS
    String url_insertar_cliente = "http://10.0.2.2:80/tesis_con/public/clientes/create";

/*

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gerente_clientes_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Labels
        cabezera = view.findViewById(R.id.cliente_nuevo);
        rif_lab = view.findViewById(R.id.rift_label);
        raz_lab = view.findViewById(R.id.raz_soc_label);
        encar_lab = view.findViewById(R.id.encargado_label);
        encar_num_lab= view.findViewById(R.id.encargado_num_label);

        //Inputs
        rif_in = view.findViewById(R.id.rift_input);
        raz_in = view.findViewById(R.id.raz_soc_input);
        encar_in = view.findViewById(R.id.encargado_input);
        encar_num_int = view.findViewById(R.id.encargado_num_input);

        //Botones
        cancelar = view.findViewById(R.id.cancelar);
        ingresar = view.findViewById(R.id.ingresar_cliente);

        navController = Navigation.findNavController(view);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar_cliente();
                navController.navigate(R.id.action_fragment_gerente_clientes_insertar_to_fragment_gerente_clientes);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_fragment_gerente_clientes_insertar_to_fragment_gerente_clientes);
            }
        });
    }

    //Manda solicitud para insertar nuevo cliente

    private void insertar_cliente(){
        //Se declaran los varoles.
        String rif = rif_in.getText().toString().trim();
        String raz_soc = raz_in.getText().toString().trim();


        if(!rif.isEmpty() && !raz_soc.isEmpty()){
            Toast.makeText(getContext(),"Ingresar Cliente", Toast.LENGTH_SHORT).show();

            RequestQueue queue = Volley.newRequestQueue(getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("rif_cliente",rif);
                jsonObject.put("razon_social",raz_soc);

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_insertar_cliente, jsonObject, new Response.Listener<JSONObject>() {
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



}