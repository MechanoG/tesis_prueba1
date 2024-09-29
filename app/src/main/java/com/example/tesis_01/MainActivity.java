package com.example.tesis_01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//importa elementos a usar de la biblioteca volley
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button gatillo;
        TextView salida;



        //Se declara e inicializa la url del archivo del servidor
        RequestQueue queue = Volley.newRequestQueue(this);


        //Se inicializa el boton y el get text
        gatillo = findViewById(R.id.button01);
        salida = findViewById(R.id.prueba);

        //Se define la funcion del boton gatillo
        gatillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se declara la url de el archivo php necesario para la conexion
                String con="http://10.0.2.2:80/php/db_conexion.php";
                        /*"http://10.0.2.2:80/php/tesis_con.php/public";*/

                StringRequest req = new StringRequest(Request.Method.GET, con,
                        new Response.Listener<String>(){
                    @Override
                    public  void onResponse(String response){
                        Toast.makeText(getApplicationContext(), "Conexion realizada",Toast.LENGTH_SHORT).show();
                        Log.d("Mensaje" , "Conexion realizada");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), "Conexion fallida",Toast.LENGTH_SHORT).show();
                        salida.setText(error.toString());
                        Log.i("Error", error.toString());
                    }
                });

                queue.add(req);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /*
    private void ejecutar_peticion (String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Log.d("Respuesta", response);
                //Muestra un mensaje al usuario si la conexion fue exitosa
                Toast.makeText(getApplicationContext(), "Conexion realizada",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), "Conexion fallida",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

     */
}