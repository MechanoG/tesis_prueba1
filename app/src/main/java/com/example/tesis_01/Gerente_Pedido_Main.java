package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gerente_Pedido_Main extends Fragment {
    ArrayList<Pedidos_lista> pedidosLista;
    RecyclerView lista_pedidos;
    Button ingresar_pedido;

    //Se inicializan controlle y navhost para fragments
    NavController navController;
    NavHostFragment navHostFragment;



    //Variable para la url a donde se realizara la consulta
    String url="http://10.0.2.2:80/tesis_con/public/pedidos";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //se sobre escribe metodo oncreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_gerente__pedido__main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        
        lista_pedidos=view.findViewById(R.id.pedidos_lista);

        //Se crea el array para los pedidos
        pedidosLista = new ArrayList<Pedidos_lista>();
        mostrar_pedidos();
        buildRecycleview();

        /*

        lista_pedidos=view.findViewById(R.id.pedidos_lista);
        lista_pedidos.setHasFixedSize(true);
        lista_pedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        pedidosLista = new ArrayList<Pedidos_lista>();
        pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Vigente"));
        for (int i=0; i<=10; i++){
            pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Vigente"));

        }
        pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Ulti"));


        Pedidos_lista_Adapter adaptador_pedidos = new Pedidos_lista_Adapter(pedidosLista, getContext());
        lista_pedidos.setAdapter(adaptador_pedidos);

        */

        try{
            //se crea el nav controles
            //navController=NavHostFragment.findNavController(this);

            navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentPedidosGerente);
            navController = navHostFragment.getNavController();          //se crea el boton
            ingresar_pedido =view.findViewById(R.id.ir_nuevo_pedido);

            ingresar_pedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Accion", "Se presiono el boton");
                    navController.navigate(R.id.action_gerente_Pedido_Main_to_pedidos_insertar2);
                }
            });

        }catch (java.lang.IllegalStateException e){
            Toast.makeText(getContext(), "Eror View", Toast.LENGTH_SHORT).show();
            Log.d("Error", e.getMessage());

        }


    }



    //funcion que recupera los datos de la base de datos y los muestra en el recycle view
    private void mostrar_pedidos (){
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
                        int pedido_id_cli = responseObj.getInt("fk_id_cliente");
                        int pedido_id_user = responseObj.getInt("fk_id_usuario");
                        String s = responseObj.getString("total");
                        float pedido_total =  Float.parseFloat(s);
                                //String pedido_total = responseObj.getString("estado");
                        String estado = responseObj.getString("estado");

                        pedidosLista.add(new Pedidos_lista(pedido_id, pedido_total, pedido_id_cli, pedido_id_user, estado ));

                        buildRecycleview();

                        Log.d("Pedido_id", Integer.toString(pedido_id));
                        Log.d("Pedido_id_cli", Integer.toString(pedido_id_cli));
                        Log.d("Pedido_id_user", Integer.toString(pedido_id_user));
                        Log.d("Pedido Toral", s);
                        Log.d("Estado", estado);


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

    private void buildRecycleview(){
        //se inicia el adaptador de la clase
        Pedidos_lista_Adapter adaptador_pedidos = new Pedidos_lista_Adapter(pedidosLista, getContext());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        lista_pedidos.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        lista_pedidos.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        lista_pedidos.setAdapter(adaptador_pedidos);

    }

    private void actualizar(){
        //inserta nuevo elemento en las views;
        pedidosLista.add(new Pedidos_lista(11, 55.55f,69, 69, "Huevon" ));

        //actualiza el view.
        buildRecycleview();
    }

}