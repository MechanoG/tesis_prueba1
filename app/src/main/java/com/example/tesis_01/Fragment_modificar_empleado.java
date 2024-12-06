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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    private Button cancelar, ingresar;

    private int empId;

    //URL PARA INSERTAR EMPLEADO Y USUARIO
    //"http://10.0.2.2:80/tesis_con/public/usuarios/creatEmployUser";
    String url_detalles_empleados ="http://192.168.0.5/tesis_con/public/usuarios/detalle";


    NavController navController;


     /*
    public Fragment_modificar_empleado() {
        // Required empty public constructor
    }

        public static Fragment_modificar_empleado newInstance(String param1, String param2) {
        Fragment_modificar_empleado fragment = new Fragment_modificar_empleado();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */

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

        String tipos = "Vendedor";


        //////////////////////////////////////////////////////////////////////////////////




        navController = Navigation.findNavController(view);

        //Botones
        cancelar = view.findViewById(R.id.bttnNE_volver);
        ingresar=view.findViewById(R.id.nuevo_empleado);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_fragment_gerente_empleados_insertar_to_fragment_gerente_empleados);
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insertar_empleado(tipos);
                //navController.navigate(R.id.action_fragment_gerente_empleados_insertar_to_fragment_gerente_empleados);

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

}