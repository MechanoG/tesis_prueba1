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


public class Fragment_modificar_clientes extends Fragment {

    private int clienteId;

    String url_obtener_clie = "http://192.168.0.5/tesis_con/public/clientes/byId";

    private TextView cabezera, rif_lab, raz_lab, encar_lab, encar_num_lab;

    private EditText rif_in, raz_in, encar_in, encar_num_int;

    private Button cancelar, modificar;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modificar_clientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() !=null){
            clienteId = getArguments().getInt("clienteID");
        }

        obtener_cliente();

        //Labels
        cabezera = view.findViewById(R.id.cliente_nuevo);
        rif_lab = view.findViewById(R.id.rift_label);
        raz_lab = view.findViewById(R.id.raz_soc_label);
        encar_lab = view.findViewById(R.id.ced_label);
        encar_num_lab= view.findViewById(R.id.encargado_num_label);

        //Inputs
        rif_in = view.findViewById(R.id.nom_input);
        raz_in = view.findViewById(R.id.ape_input);
        encar_in = view.findViewById(R.id.encargado_input);
        encar_num_int = view.findViewById(R.id.ced_input);

        //Botones
        cancelar = view.findViewById(R.id.cancelar);
        modificar = view.findViewById(R.id.modificar_cliente);

        navController = Navigation.findNavController(view);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.popBackStack();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        }
    private void obtener_cliente(){
        //Se declaran los varoles.

           RequestQueue queue = Volley.newRequestQueue(getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id",clienteId);
            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_obtener_clie, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Mensaje", response.toString());

                    JSONObject jsonObject = response;

                    try {
                        rif_in.setText(jsonObject.getString("rif"));
                        raz_in.setText(jsonObject.getString("razon_social"));
                        encar_in.setText(jsonObject.getString("gerente"));
                        encar_num_int.setText(jsonObject.getString("telfefono"));


                    } catch (JSONException e){
                        e.printStackTrace();
                    }







                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                }
            });

            queue.add(jsonObjectRequest);





    }
}