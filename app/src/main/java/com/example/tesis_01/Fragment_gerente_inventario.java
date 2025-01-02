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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_gerente_inventario extends Fragment {
    //iNICIALIZA ELEMENTOS VISUALES

    Button retroceder, ingresar_producto;
    RecyclerView inventario_recy;

    //dECLARA ARRAY DE RECYCLEVIEW
    ArrayList<Producto> productos;

    MaterialToolbar appbar;

    //Url para obtener informacion de productos de la base de datos http://10.0.2.2:80/tesis_con/public/productos
    //"http://192.168.0.4/tesis_con/public/productos";
    String url_recibir_productos = "http://192.168.0.2/tesis_con/public/productos";


    //Se inicializan controlle y navhost para fragments
    NavController navController;
    NavHostFragment navHostFragment;

    //manejo del appbar



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





        inventario_recy = view.findViewById(R.id.inven_recyView);


        obtener_productos();
        build_products_recycleview();
        /*
        retroceder = view.findViewById(R.id.inv_volver);
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


         */
        try{
            //se crea el nav controles
            navController=NavHostFragment.findNavController(this);
                                                                                                //fragmentContInventarioGerente es el ide del fagmentr view de la activity
            navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContInventarioGerente);
            navController = navHostFragment.getNavController();          //se crea el boton
            ingresar_producto = view.findViewById(R.id.ingresar_pro);

            ingresar_producto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Accion", "Se presiono el boton");
                    navController.navigate(R.id.action_fragment_gerente_inventario_to_fragment_gerente_inventario_insertar);
                }
            });

        }catch (java.lang.IllegalStateException e){
            Toast.makeText(getContext(), "Eror View", Toast.LENGTH_SHORT).show();
            Log.d("Error", e.getMessage());

        }

        appbar = view.findViewById(R.id.topAppBar);

        appbar.setNavigationOnClickListener(v ->
                getActivity().finish()
        );



    }

    //Recibe la informacion de los productos
    private void obtener_productos(){           // Esto no rompe el spinner

        //Se crea nueva variable para  nuestro request que
        RequestQueue queue = Volley.newRequestQueue(getContext());


        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_recibir_productos, null, new Response.Listener<JSONArray>() {
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

                        int id= responseObj.getInt("id");
                        String codigo_producto = responseObj.getString("codigo");
                        String descripcion = responseObj.getString("descripcion");
                        int cantidad = responseObj.getInt("cantidad");
                        String s = responseObj.getString("precio");
                        float precio =  Float.parseFloat(s);

                        /*
                        Log.d("codigo_producto", codigo_producto);
                        Log.d("descripcion", descripcion);
                        Log.d("cantidad", Integer.toString(cantidad));
                        Log.d("precio", s);
                        */



                        //Informacion de los productos
                        productos.add(new Producto(id, precio, cantidad, descripcion, codigo_producto));

                        //Se pasa la informacion de la array de guardao al recycle view
                        build_products_recycleview();



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

    //Se encvarga de mandar el recycleview
    private void build_products_recycleview(){

        //se inicia el adaptador de la clase
        Gerente_Productos_RecAdapter productos_view = new Gerente_Productos_RecAdapter(productos, getContext());

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