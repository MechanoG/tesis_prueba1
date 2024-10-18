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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class Gerente_Pedido_Main extends Fragment {
    ArrayList<Pedidos_lista> pedidosLista;
    RecyclerView lista_pedidos;

    //Variable para la url a donde se realizara la consulta
    String url="http://localhost/tesis_con/public/pedidos";

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
        pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Vigente"));
        for (int i=0; i<=10; i++){
            pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Vigente"));

        }
        pedidosLista.add(new Pedidos_lista(1, 565.1f, 1, 1, "Ulti"));


        Pedidos_lista_Adapter adaptador_pedidos = new Pedidos_lista_Adapter(pedidosLista, getContext());
        lista_pedidos.setAdapter(adaptador_pedidos);
    }

    /*
    //funcion que recupera los datos de la base de datos y los muestra en el recycle view
    private void mostrar_pedidos (){
        //Se crea nueva variable para  nuestro request que
        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json

        String url="http://localhost/tesis_con/public/pedidos";
        RequestQueue conect = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, Response.Listener<JSONArray>(){

        })


    } */
}