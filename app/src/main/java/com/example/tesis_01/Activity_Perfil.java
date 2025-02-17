package com.example.tesis_01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Perfil extends AppCompatActivity {

    TextView emp, cedula, genero,telefono,usuario,contraseña, tipo, zona;
    MaterialToolbar appbar;



    int id_user;

    String url_detalles_empleados ="https://23a8-37-19-221-239.ngrok-free.app/tesis_con/public/usuarios/detalle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref",MODE_PRIVATE);

        id_user = sharedPreferences.getInt("id", 0);

        Log.d("Id usuario", Integer.toString(id_user));

        emp = findViewById(R.id.per_emp);
        cedula = findViewById(R.id.per_cedu);
        genero = findViewById(R.id.per_gen);
        telefono = findViewById(R.id.per_tel);
        usuario = findViewById(R.id.per_user);
        contraseña = findViewById(R.id.per_cont);
        tipo = findViewById(R.id.per_tip);
        zona = findViewById(R.id.per_zon);

        datos_perfil();


        appbar = findViewById(R.id.topAppBar);

        appbar.setNavigationOnClickListener(v ->
                finish()
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void datos_perfil() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //Se crea un JSONObject con los datos que se desean enviar
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id_user);

        } catch (JSONException e) {
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
                    String cont = respuesta.getString("contraseña");
                    String tip = respuesta.getString("tipo");
                    String zon = respuesta.getString("zona");
                    String nombre = respuesta.getString("nombre");
                    String apellido = respuesta.getString("apellido");
                    String ced = respuesta.getString("cedula");
                    String sex = respuesta.getString("sexo");
                    String tlfn = respuesta.getString("telefono");

                    emp.setText("Nombre: " +nombre +" "+apellido);
                    cedula.setText("Cedula: " + ced);
                    genero.setText("Genero: " + sex);
                    telefono.setText("Telefono: " + tlfn);
                    usuario.setText("Usuario: " + user);
                    contraseña.setText("Contraseña: " + cont);
                    tipo.setText("Tipo: " + tip);
                    zona.setText("Zona: " + zon);



                } catch (JSONException e) {
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