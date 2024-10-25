package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class Pedidos_insertar extends Fragment implements AdapterView.OnItemSelectedListener {
    NavController navController;
    TextView header;
    Button cancelar, ingresar;
    Spinner pedido_inv;
    ArrayAdapter inv_list_adap;

    //Arrray que almacena los productos para el spinner
    ArrayList <String> productos_spinner;

    //Array que almacena los productos para guardado
    ArrayList <Producto> productos;

    //Array para mostrar los productos en el recycler view
    ArrayList <Producto> productos_recycleview;

    //Url para obtener informacion de la base de datos
    String url_recibir = "http://10.0.2.2:80/tesis_con/public/productos";

    //Recycleview
    RecyclerView recy_pedidos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicializa  el array del recycle view
        recy_pedidos = view.findViewById(R.id.recycle_pedidos);

        //Inicializa el array del recycle view
        productos_recycleview = new ArrayList<Producto>();

        //Inicializa el array de datos del spinner
        productos_spinner = new ArrayList<String>();

        //Inicializa el array de datos
        productos = new ArrayList<Producto>();

        //pRUEBA RECYCLE VIEW
        productos_recycleview.add(new Producto("Hola", 15.5f,55, "Saludo" ));
        productos_recycleview.add(new Producto("Hola", 15.5f,55, "Saludo" ));
        productos_recycleview.add(new Producto("Hola", 15.5f,55, "Saludo" ));
        productos_recycleview.add(new Producto("Hola", 15.5f,55, "Saludo" ));

        buildRecyclerView();

        //Se solicita la infformacion para los array
        obtener_productos();



        //Crear el reciclerview
        //productos_pedido = view.findViewById(R.id.productos_pedidos_recycle);


        //Crea el spiner y le aplica OnItemSelectedListener, que le
        //cual item del spinner is clicked

        pedido_inv = view.findViewById(R.id.spin_Gped_inv);
        pedido_inv.setOnItemSelectedListener(this);

        //Crea instancia de ArrayAdapter
        //having the list odf courses

        inv_list_adap = new ArrayAdapter (getContext(), android.R.layout.simple_spinner_item,
                productos_spinner);

        //on the spiner whic binds data to spinner
        pedido_inv.setAdapter(inv_list_adap);

        //set un layout resource file para cada item del Spinner

        inv_list_adap.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        //Set the ArrayAdapter (inv_list_adapt) data

        header = view.findViewById(R.id.nuevo_pedido);

        //Se cra nevegador del fragment
        navController = Navigation.findNavController(view);

        //Se inicializa boton cancelar.
        cancelar = view.findViewById(R.id.Pedido_Cancelar);
        //Se declara la acciona l presionar boton
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_pedidos_insertar_to_gerente_Pedido_Main);
            }
        });




    }

    //Make a toar con el nombre del string
    //que esseleccionado en el spiner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), productos_spinner.get(i), Toast.LENGTH_SHORT ).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Obtiene la informacion de los productos
    private void obtener_productos(){

        //Se crea nueva variable para  nuestro request que
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_recibir, null, new Response.Listener<JSONArray>() {
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
                        String insertar =codigo_producto+"   " + descripcion +"   "+ Integer.toString(cantidad) +
                                "   " + s;

                        //Envio la informacion productos al spinner
                        productos_spinner.add(insertar);

                        //Informacion de los productos
                        productos.add(new Producto(codigo_producto, precio, cantidad, descripcion));

                        log_productos();

                        /*
                        pedidosLista.add(new Pedidos_lista(pedido_id, pedido_total, pedido_id_cli, pedido_id_user, estado ));
                        buildRecycleview(); */

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

    private void log_productos() {
        for (int i=0; i<productos.size(); i++){

            String cod = productos.get(i).getCodigo();
            String desc = productos.get(i).getDescripcion();
            String can = Integer.toString(productos.get(i).getCantidad());
            String pre = Float.toString(productos.get(i).getPrecio());

            Log.d("codigo_producto", cod );
            Log.d("descripcion", desc );
            Log.d("cantidad", can);
            Log.d("precio", pre);

        }
    }

    private void buildRecyclerView(){
        //Se inicia el adaptador de de la clase
        Productos_RecAdapter adaptadorProductos = new Productos_RecAdapter(productos_recycleview, getContext());

        //Se agrega Layout managetr al recycleview
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recy_pedidos.setHasFixedSize(true);

        //Se asigna el layout manager al recycle view.
        recy_pedidos.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        recy_pedidos.setAdapter(adaptadorProductos);
    }

}