package com.example.tesis_01;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pedidos_insertar extends Fragment implements AdapterView.OnItemSelectedListener {
    NavController navController;
//
    TextView header, total_pedidos, empleado_txt;;
//
    Button cancelar, ingresar;

    private HashMap<String, Integer > producto_cantidad = new HashMap<>();

    //Espiners de la pantalla
    Spinner pedido_inv, cliente_pedido;
    //Espiner de Adaptador de espiner de productos
    ArrayAdapter inv_list_adap;
    //Adaptador de spinner de clientes
    ArrayAdapter cli_list_adap;


//    //Arrray que almacena los productos para el spinner de productos
    ArrayList<String> productos_spinner;
//
//    //Array que almacena los productos para guardado
    ArrayList <Producto> productos;

    //Array para mostrar los productos en el recycler view
    ArrayList <Producto> productos_recycleview;


    //Alamcenamiento del empleado
    ArrayList<Cliente> clientes;

    //Array del spiner de clientes
    ArrayList<String> clientes_spinner;

    //Se guarda el id del cliente
    int id_cliente_set;




    //Url para obtener informacion de la base de datos http://10.0.2.2:80/tesis_con/public/productos
    //"http://192.168.0.4/tesis_con/public/productos"; -> por local
    String url_recibir_productos = "http://192.168.0.4/tesis_con/public/productos";

    //URL para obtener la informacion de clientes de la base de datos http://10.0.2.2:80/tesis_con/public/clientes
    //"http://192.168.0.4/tesis_con/public/clientes";
    String url_recibir_clientes = "http://192.168.0.4/tesis_con/public/clientes";

    //URL para insertar los pedidos  a la base de datos. http://10.0.2.2:80/tesis_con/public/pedidos/create
    // "http://192.168.0.4/tesis_con/public/pedidos/create";
    String url_insertar_pedido =  "http://192.168.0.4/tesis_con/public/pedidos/create";


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

        //Inicializa  el view del recycle view
        recy_pedidos = view.findViewById(R.id.recycle_pedidos);

        //Inicializa el array del recycle view
        productos_recycleview = new ArrayList<Producto>();

        //Inicializa el array de datos del spinner de productos
        productos_spinner = new ArrayList<String>();

        //Inicializa el array de datos del spinner de clientes
        clientes_spinner = new ArrayList<String>();

        //Inicializa el array de datos
        productos = new ArrayList<Producto>(); //No es esto lo que cuelga spinner
        clientes = new ArrayList<Cliente>();

        //Se solicita la infformacion para los array
        obtener_productos();
        obtener_clientes();

        //Cream los espiner el spiner y le aplica OnItemSelectedListener, que le
        //cual item del spinner is clicked

        //Creacion y adaptacion de los spinner y OnItemSelectedListener
        //Del Spinner productos
        pedido_inv = view.findViewById(R.id.spin_Gped_inv);
        pedido_inv.setOnItemSelectedListener(this);

        //Del spinner cliente
        cliente_pedido=view.findViewById(R.id.spin_Gclientes);
        cliente_pedido.setOnItemSelectedListener(this);

        //Crea instancia de los ArrayAdapter
        //having the list odf courses

        //Adapter de spinner de productos
        inv_list_adap = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                productos_spinner);

        //Adapter de spinner de clientes
        cli_list_adap = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                clientes_spinner);


        //set un layout resource file para cada item del los Spinner
        //layout resource del espiner clientes
        inv_list_adap.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        //layout resource del espiner productos
        cli_list_adap.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        //Set the ArrayAdapter (inv_list_adapt) data }
        //Set productos adapter
        pedido_inv.setAdapter(inv_list_adap);
        //Set clientes adapter
        cliente_pedido.setAdapter(cli_list_adap);


        empleado_txt = view.findViewById(R.id.txtEmpleado);
        header = view.findViewById(R.id.nuevo_pedido);
        total_pedidos = view.findViewById(R.id.txtTotalPedido);

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

        ingresar = view.findViewById(R.id.realizar_pedido);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realizar_pedido();
            }
        });


        buildRecyclerView();
        id_usuario();
        //on the spiner whic binds data to spinner


    }

    //Make a toar con el nombre del string
    //que esseleccionado en el spiner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Identifica desde cual spinner se llama la funcion
        if(adapterView.getId()==R.id.spin_Gped_inv) {
            Toast.makeText(getActivity().getApplicationContext(), productos_spinner.get(i),
                    Toast.LENGTH_LONG).show();
            Log.d("Mensaje", "Se llamo a onItemSelected");
            productos_recycleview.add(productos.get(i));
            buildRecyclerView();
            total_pedido();

        }else{
            String mensaje = clientes.get(i).razon_social;
            Toast.makeText(getActivity().getApplicationContext(), mensaje,
                    Toast.LENGTH_LONG).show();
            id_cliente_set = clientes.get(i).cliente_id;
            empleado_txt.setText(String.valueOf(id_cliente_set));

        }

    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //Obtiene la informacion de los productos
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
                        inv_list_adap.notifyDataSetChanged();  //Fundamental, sino tendre problemas para poder llenar el spinner

                        //Informacion de los productos
                        productos.add(new Producto(codigo_producto, precio, cantidad, descripcion));

                        //Se pasa la informacion de la array de guardao al recycle view
                        buildRecyclerView();



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



                        Log.d("ID CLIENTE", String.valueOf(id_cliente));
                        Log.d("DESCRIPCION CLIENTE", rif);
                        Log.d("RAZON SOCIAL CLIENTE", razon_social);


                        String insertar =Integer.toString(id_cliente)+ "" + rif + "" + razon_social;

                        //Envio la informacion productos al spinner
                        clientes_spinner.add(insertar);
                        cli_list_adap.notifyDataSetChanged();  //Fundamental, sino tendre problemas para poder llenar el spinner


                        //Informacion de los productos
                        clientes.add(new Cliente(rif, razon_social, id_cliente));

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


    private void realizar_pedido(){
        float final_pedido = Float.parseFloat((String.valueOf(total_pedidos.getText())));

        RequestQueue queue = Volley.newRequestQueue(getContext());

        //Se crea un JSONObject con los datos que se desean enviar
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id_cliente",id_cliente_set);
            jsonObject.put("id_usuario",id_usuario());
            jsonObject.put("total_pedido",final_pedido);
        }catch (JSONException e){
            e.printStackTrace();
        }

        //Crear solicitud post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url_insertar_pedido, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Mensaje", response.toString());
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        queue.add(jsonObjectRequest);

    }


    private void buildRecyclerView(){
        //Se inicia el adaptador de de la clase
        total_pedido();

        Productos_Insertar_RecAdapter adaptadorProductos = new Productos_Insertar_RecAdapter(productos_recycleview, producto_cantidad, this);

        //Se agrega Layout managetr al recycleview
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recy_pedidos.setHasFixedSize(true);

        //Se asigna el layout manager al recycle view.
        recy_pedidos.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        recy_pedidos.setAdapter(adaptadorProductos);
    }



    void total_pedido(){
        if (productos_recycleview.isEmpty()){
            total_pedidos.setText(String.valueOf(0.00f));
        }
        else{
            float conteo = 0;
            for (Producto producto : productos_recycleview){
                int cantidad = producto_cantidad.getOrDefault(producto.getCodigo(),1);
                conteo += producto.getPrecio()*cantidad;
            }

            total_pedidos.setText(String.valueOf(conteo));
        }

    }

    private int id_usuario(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        int usuario_id = sharedPreferences.getInt("id", 0);

        String Et_id = Integer.toString(usuario_id);

        Log.d("ID Uduarios", Et_id);

        return usuario_id;
    }


}

