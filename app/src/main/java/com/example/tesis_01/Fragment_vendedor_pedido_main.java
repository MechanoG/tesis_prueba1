package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class Fragment_vendedor_pedido_main extends Fragment {

    //Array que almacena los pedidos
    ArrayList<Pedidos_lista> pedidosLista;

    RecyclerView lista_pedidos;
    Button ingresar_pedido;

    //Se inicializan controlle y navhost para fragments
    NavController navController;
    NavHostFragment navHostFragment;

    MaterialToolbar appbar;

    //Variable para la url a donde se realizara la consulta devolvera unicamente pediods de carlos
    String url="https://23a8-37-19-221-239.ngrok-free.app/tesis_con/public/pedidos/pedidosid";
    //"http://192.168.0.4/tesis_con/public/pedidos";
//"http://10.0.2.2:80/tesis_con/public/pedidos"



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendedor_pedido_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //iNICIALIZA EL RECYCLREVIREW
        lista_pedidos=view.findViewById(R.id.pedidos_lista_ven);

        //Se crea el array para los pedidos
        pedidosLista = new ArrayList<Pedidos_lista>();

        mostrar_pedidos();

        try{
            //se crea el nav controles
            navController=NavHostFragment.findNavController(this);

            navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentPedidosVendedor);
            navController = navHostFragment.getNavController();          //se crea el boton */
            ingresar_pedido =view.findViewById(R.id.ir_nuevo_pedido_ven);

            ingresar_pedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Accion", "Se presiono el boton");
                    navController.navigate(R.id.action_fragment_vendedor_pedido_main_to_pedidos_insertar2);
                }
            });

            //manejo del appbar
            appbar = view.findViewById(R.id.topAppBar);

            appbar.setNavigationOnClickListener(v ->
                    getActivity().finish()
            );




        }catch (java.lang.IllegalStateException e){
            Toast.makeText(getContext(), "Eror View", Toast.LENGTH_SHORT).show();
            Log.d("Error", e.getMessage());

        }
        appbar = view.findViewById(R.id.topAppBar);

        appbar.setNavigationOnClickListener(v ->
                getActivity().finish()
        );



    }

    //funcion que recupera los datos de la base de datos y los muestra en el recycle view
    void mostrar_pedidos (){
        //Limpia la lista antes de llevar a cao una nueva consulta.
        pedidosLista.clear();
        if (lista_pedidos.getAdapter() != null){
            //identifica el adaptador para limpiar la vista.
            lista_pedidos.getAdapter().notifyDataSetChanged();
        }

        //Se crea nueva variable para  nuestro request que
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
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

                        int pedido_id = responseObj.getInt("id");
                        String ped_clie = responseObj.getString("cliente");
                        String ven_nom = responseObj.getString("nombre");
                        String ven_ape = responseObj.getString("apellido");
                        String ped_ven = ven_nom + "" + ven_ape;
                        String s = responseObj.getString("total");
                        float pedido_total =  Float.parseFloat(s);
                        String tipoPago = responseObj.getString("tipo_pago");
                        String vencimiento = responseObj.getString("vencimiento");
                        String estado = responseObj.getString("estado");





                        pedidosLista.add(new Pedidos_lista(pedido_id, estado,vencimiento, pedido_total,
                                tipoPago,ped_ven,ped_clie));

                        buildRecycleview();


                    }catch (JSONException e){
                        e.printStackTrace();
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                errorConexion();

                Log.d("Error", "" + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void buildRecycleview(){
        //se inicia el adaptador de la clase
        Pedidos_lista_AdapterVend adaptador_pedidos = new Pedidos_lista_AdapterVend(pedidosLista, getContext(), getParentFragmentManager(), this,
                navController);

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        lista_pedidos.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        lista_pedidos.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        lista_pedidos.setAdapter(adaptador_pedidos);

    }

    private void errorConexion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error:");

        StringBuilder message = new StringBuilder();
        message.append("No se pudo establecer conexion.");

        builder.setMessage(message.toString());

        builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();

    }


}