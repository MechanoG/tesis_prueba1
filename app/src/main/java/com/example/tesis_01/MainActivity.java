package com.example.tesis_01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button gatillo;
    /*TextView salida;*/
    EditText user;
    EditText cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Se declara e inicializa la url del archivo del servidor
        RequestQueue queue = Volley.newRequestQueue(this);


        //Se inicializa el boton y el textview
        gatillo = findViewById(R.id.button01);
       /* salida = findViewById(R.id.prueba);*/
        user = findViewById(R.id.in_user);
        cont = findViewById(R.id.in_pass);


        //Se define la funcion del boton gatillo
        gatillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = user.getText().toString();
                String contra = cont.getText().toString();

                //Se evita que se dejen los paneles de usuario y contraseña vacias
                if (!usuario.isEmpty() && !contra.isEmpty()) {

                    //Se declara la url de el archivo php necesario para la conexion
                    String con ="http://10.0.2.2:80/tesis_con/public/usuarios/login";
                    /*"http://10.0.2.2:80/tesis_con/public"; */
                    /*"http://10.0.2.2:80/php/db_conexion.php";*/


                    StringRequest req = new StringRequest(Request.Method.POST, con,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Conexion realizada", Toast.LENGTH_SHORT).show();
                                    Log.d("Mensaje", "Conexion realizada");

                                    try{
                                        //Creao objeto json para obtener el resultado del server
                                        JSONObject jsonObject=new JSONObject(response);

                                        //Graba los datos del usuario
                                        SharedPreferences sharedPreferences =
                                                getSharedPreferences("MySharedPref",MODE_PRIVATE);
                                        SharedPreferences.Editor miEdit = sharedPreferences.edit();
                                        miEdit.putInt("id", jsonObject.getInt("id"));
                                        miEdit.putInt("id_empleado", jsonObject.getInt("fk_id_empleado"));

                                        miEdit.putString("usuario", jsonObject.getString("usuario"));
                                        miEdit.putString("contraseña", jsonObject.getString("contraseña"));
                                        miEdit.putString("tipo", jsonObject.getString("tipo"));
                                        miEdit.apply();

                                        //Se determina el tipo de usuario de la aplicacion
                                        String credencial = sharedPreferences.getString("tipo", "");

                                        if (credencial.equals("vendedor")){
                                            Log.d("Mensaje", "Se verifiva el acceso al vendedor");
                                        } else if (credencial.equals("Gerente")) {
                                            Log.d("Mensaje", "Se verifiva el acceso al Gerente");
                                        }else{
                                            Log.d("Mensaje", "Proceso no completado");
                                        }

                                        /* Manda advertencia a terminal con la infomracion de sharedpreferences

                                        int usu = sharedPreferences.getInt("id",0);
                                        int empId = sharedPreferences.getInt("id_empleado",0);


                                        Log.d("Mensaje", Integer.toString(usu));
                                        Log.d("Mensaje", Integer.toString(empId));
                                        Log.d("Mensaje", sharedPreferences.getString("usuario", ""));
                                        Log.d("Mensaje", sharedPreferences.getString("contraseña", ""));
                                        Log.d("Mensaje", sharedPreferences.getString("tipo", ""));
                                        */

                                    }catch (JSONException e){
                                        //JSON error
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Json error" + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                        Log.d("Json error", e.toString());
                                    }catch (ClassCastException e){
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "ClassCastError" + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                        Log.d("Json error", e.toString());
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Conexion fallida", Toast.LENGTH_SHORT).show();
                            Log.i("Error", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String , String> getParams(){
                            //Se crea un mapa para almacenar
                            //clave y contraseña
                            Map<String, String> params = new HashMap<String, String>();

                            //Se passan nuestra clave
                            //an el valor de nuetros parametros
                            params.put("usuario", usuario);
                            params.put("contraseña", contra);

                            //Se devuelven los parametros
                            return params;
                        }

                    };

                    queue.add(req);

                }else{
                    Toast.makeText(getApplicationContext(), "No se pueden dejar campos vacios",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




}