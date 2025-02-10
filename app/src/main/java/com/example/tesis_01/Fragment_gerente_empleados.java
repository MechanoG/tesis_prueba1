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
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_gerente_empleados extends Fragment {

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    */

    //iNICIALIZA ELEMENTOS VISUALES
    TextView cabecera;
    Button ingresar_empleado;
    RecyclerView empleado_recy;

    //dECLARA ARRAY DE RECYCLEVIEW
    ArrayList<Empleado> empleados;

    //Url para obtener informacion de empleados de la base de datos http://10.0.2.2:80/tesis_con/public/usuarios/user_employ
    //"http://192.168.0.4/tesis_con/public/usuarios/user_employ";
    String url_recibir_empleados = "http://192.168.0.5/tesis_con/public/usuarios/user_employ";



    //Se inicializan controlle y navhost para fragments
    NavController navController;
    NavHostFragment navHostFragment;

    MaterialToolbar appbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gerente_empleados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empleados = new ArrayList<Empleado>();


        empleado_recy = view.findViewById(R.id.emple_recy);

        ingresar_empleado = view.findViewById(R.id.bttn_nuevo);



        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContEmpleadosGerente);
        navController = navHostFragment.getNavController();

        ingresar_empleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_fragment_gerente_empleados_to_fragment_gerente_empleados_insertar);


            }
        });

        /*
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
            }
        }); */

        obtener_empleados();
        build_emp_recycleview();

        appbar = view.findViewById(R.id.topAppBar);

        appbar.setNavigationOnClickListener(v ->
                getActivity().finish()
        );

    }

    //Recibe la informacion de los productos
     void obtener_empleados(){           // Esto no rompe el spinner
        //limpia la lista antes de hacer una consulta
         empleados.clear();
         if (empleado_recy.getAdapter()!= null){
             empleado_recy.getAdapter().notifyDataSetChanged();

         }

        //Se crea nueva variable para  nuestro request que
        RequestQueue queue = Volley.newRequestQueue(getContext());


        //en forma de un array asi que estamos haciendo un json array quest
        //debajo de esa linea hacemos un json array
        //request y entonces extraemos data de cada objeto json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_recibir_empleados, null, new Response.Listener<JSONArray>() {
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
                        int id_emp = responseObj.getInt("id");
                        String nombre_emp = responseObj.getString("nombre");
                        String apell_emp = responseObj.getString("apellido");
                        String cedula_emp = responseObj.getString("cedula");
                        String sexo_emp = responseObj.getString("sexo");
                        String telefono_emp = responseObj.getString("telefono");
                        String usuario_emp = responseObj.getString("usuario");
                        String cont_emp = responseObj.getString("contraseña");
                        String tipo_emp = responseObj.getString("tipo");



                        //Informacion de los productos
                        empleados.add(new Empleado(id_emp, usuario_emp, cont_emp,tipo_emp, nombre_emp,
                                apell_emp, cedula_emp, sexo_emp, telefono_emp));

                        //Se pasa la informacion de la array de guardao al recycle view
                        build_emp_recycleview();



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
    private void build_emp_recycleview(){

        //se inicia el adaptador de la clase
        Empleados_RecAdapter empleados_view = new Empleados_RecAdapter(empleados, getContext(), getParentFragmentManager(),
                navController, this);

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        empleado_recy.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        empleado_recy.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        empleado_recy.setAdapter(empleados_view);

    }
}