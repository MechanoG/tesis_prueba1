package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class Fragment_modificar_empleado extends Fragment {

    private TextView nom_nuevo_emp,ape_nuevo_emp, ced_nuevo_emp,
            sex_nuevo_emp, tel_nuevo_emp, usu_nuevo_emp, cont_nuevo_emp;

    private EditText nom_inp, ape_inp, ced_inp, sex_inp, tel_inp, usu_inp,
            cont_inp, zona_inp;

    private Button cancelar, modificar;

    private int empId;

    //URL PARA INSERTAR EMPLEADO Y USUARIO
    //"http://10.0.2.2:80/tesis_con/public/usuarios/creatEmployUser";
    String url_detalles_empleados ="http://192.168.0.5/tesis_con/public/usuarios/detalle";
    String url_detalles_modificar ="http://192.168.0.5/tesis_con/public/usuarios/modEmpUser";

    NavController navController;

    String tipos = "Vendedor";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modificar_empleado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() !=null){
            empId = getArguments().getInt("empID");
        }

        obtener_detalles();

        //Labels
        nom_nuevo_emp=view.findViewById(R.id.nom_label);
        ape_nuevo_emp=view.findViewById(R.id.ape_label);
        ced_nuevo_emp=view.findViewById(R.id.ced_label);
        sex_nuevo_emp=view.findViewById(R.id.sex_label);
        tel_nuevo_emp=view.findViewById(R.id.tel_label);
        usu_nuevo_emp=view.findViewById(R.id.usu_label);
        cont_nuevo_emp=view.findViewById(R.id.cont_label);


        //Inputs
        nom_inp=view.findViewById(R.id.nom_input);
        ape_inp=view.findViewById(R.id.ape_input);
        ced_inp=view.findViewById(R.id.ced_input);
        sex_inp=view.findViewById(R.id.sex_input);
        tel_inp=view.findViewById(R.id.tel_input);
        usu_inp=view.findViewById(R.id.usu_input);
        cont_inp=view.findViewById(R.id.cont_input);
        zona_inp=view.findViewById(R.id.zona_input);

        //////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> tipo_pago =new ArrayList<>();

        tipo_pago.add("Vendedor");
        tipo_pago.add("Gerente");

        Spinner sel_tipoUser = view.findViewById(R.id.spinner_tipo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tipo_pago);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sel_tipoUser.setAdapter(adapter);

        sel_tipoUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipos = adapterView.getItemAtPosition(i).toString();
                Log.d("Spinner secelt:", tipos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String tipos = "Vendedor";


        //////////////////////////////////////////////////////////////////////////////////


        navController = Navigation.findNavController(view);

        //Botones
        cancelar = view.findViewById(R.id.bttnNE_volver);
        modificar=view.findViewById(R.id.mod_empleado);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_mod();
            }
        });

    }

    public void obtener_detalles(){

        RequestQueue queue = Volley.newRequestQueue(getContext());

        //Se crea un JSONObject con los datos que se desean enviar
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id", empId);

        }catch (JSONException e){
            e.printStackTrace();
        }

        //Crear solicitud post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url_detalles_empleados, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Mensaje", response.toString());

                try {
                    Log.d("Mensaje", response.toString());

                    JSONObject respuesta = response;

                    String user = respuesta.getString("usuario");
                    String cont=respuesta.getString("contraseña");
                    String tipo = respuesta.getString("tipo");
                    String zona=respuesta.getString("zona");
                    String nombre = respuesta.getString("nombre");
                    String apellido=respuesta.getString("apellido");
                    String ced = respuesta.getString("cedula");
                    String sex=respuesta.getString("sexo");
                    String tlfn = respuesta.getString("telefono");

                    nom_inp.setText(nombre);
                    ape_inp.setText(apellido);
                    ced_inp.setText(ced);
                    sex_inp.setText(sex);
                    tel_inp.setText(tlfn);
                    usu_inp.setText(user);
                    cont_inp.setText(cont);
                    zona_inp.setText(zona);

                }catch (JSONException e){
                    Log.d("Error", "Error " + String.valueOf(e));
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

    private void modificar_empleado(String tipos){
        //Se declaran los varoles.
        String nom_emp = nom_inp.getText().toString().trim();
        String ape_emp = ape_inp.getText().toString().trim();
        String ced_emp = ced_inp.getText().toString().trim();
        String sex_emp = sex_inp.getText().toString().trim();
        String tel_emp = tel_inp.getText().toString().trim();
        String usu_emp = usu_inp.getText().toString().trim();
        String cont_emp = cont_inp.getText().toString().trim();
        String tipo_emp = tipos;
        String zona_emp = zona_inp.getText().toString().trim();

        if(!nom_emp.isEmpty() && !ape_emp.isEmpty() &&
                !ced_emp.isEmpty() && !sex_emp.isEmpty() && !tel_emp.isEmpty() &&
                !usu_emp.isEmpty() && !cont_emp.isEmpty() && !tipo_emp.isEmpty()){

            RequestQueue queue = Volley.newRequestQueue(getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_user",empId);
                jsonObject.put("nom_empleado",nom_emp);
                jsonObject.put("ape_empleado",ape_emp);
                jsonObject.put("ced_empleado",ced_emp);
                jsonObject.put("sex_emp", sex_emp);
                jsonObject.put("tel_empleado",tel_emp);
                jsonObject.put("usu_empleado",usu_emp);
                jsonObject.put("cont_empleado",cont_emp);
                jsonObject.put("tipo_empleado",tipo_emp);
                jsonObject.put("zona_empleado",zona_emp);
            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_detalles_modificar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    navController.popBackStack();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });

            queue.add(jsonObjectRequest);

        }else {
            errorEmptyData();
            //Toast.makeText(getContext(), "Campos Vacios", Toast.LENGTH_SHORT).show();
        }

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

    private void errorEmptyData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error:");

        StringBuilder message = new StringBuilder();
        message.append("Por favor rellene todo los campos.");

        builder.setMessage(message.toString());

        builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();

    }

    private void dialog_mod(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("¿Modificar Empleado?");



        builder.setPositiveButton("Ingresar", (dialogInterface, which)->{
            modificar_empleado(tipos);
            Log.d("Recivido", "Cancelado" );


        });

        builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
        builder.show();
    }


}