package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_gerente_clientes extends Fragment {
    TextView cabecera;
    Button ingresar_cliente, retroceder;
    RecyclerView clientes_recy;

    //Array clientes
    ArrayList <Cliente> clientes;

    //Se inicializan controlle y navhost para fragments
    NavController navController;
    NavHostFragment navHostFragment;

    //URL para obtener la informacion de clientes de la base de datos http://10.0.2.2:80/tesis_con/public/clientes
    // "http://192.168.0.4/tesis_con/public/clientes";

    String url_recibir_clientes = "http://192.168.0.3/tesis_con/public/clientes";

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
        return inflater.inflate(R.layout.fragment_gerente_clientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clientes = new ArrayList<Cliente>();

        cabecera = view.findViewById(R.id.emple_head);

        clientes_recy = view.findViewById(R.id.emple_recy);

        ingresar_cliente = view.findViewById(R.id.bttn_volver);

        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContClientGerente);
        navController = navHostFragment.getNavController();

        ingresar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_fragment_gerente_clientes_to_fragment_gerente_clientes_insertar);
            }
        });


        retroceder= view.findViewById(R.id.bttn_nuevo);
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        obtener_clientes();
        build_clientes_recycleview();

    }

    //Obtiene la informacion de los clientes
    private void obtener_clientes(){           // Esto no rompe el spinner

        //Se crea nueva variable para  nuestro request que
        RequestQueue queue = Volley.newRequestQueue(getContext());


        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_recibir_clientes, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++) {
                    //Se crea nuevo objeto json
                    //se toma cada objeto del json array
                    try{
                        //obtenemos cada objeto del json object
                        JSONObject responseObj = response.getJSONObject(i);
                        //Obtenemos la respuesta de la api in formato json
                        //abajo extraemos un string con su key value from our json object
                        //extraemos todos los datos from our json

                        int id_cliente = responseObj.getInt("id");
                        String rif = responseObj.getString("rif");
                        String razon_social = responseObj.getString("razon_social");


                        //Informacion de los productos
                        clientes.add(new Cliente(rif, razon_social, id_cliente));

                        build_clientes_recycleview ();


                    }catch (JSONException e){
                        e.printStackTrace();
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }



    private void build_clientes_recycleview (){
        Clientes_RecAdapter clientes_view = new Clientes_RecAdapter(clientes, getContext());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        clientes_recy.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        clientes_recy.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        clientes_recy.setAdapter(clientes_view);


    }






}