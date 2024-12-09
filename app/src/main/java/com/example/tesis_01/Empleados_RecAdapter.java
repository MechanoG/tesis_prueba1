package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Empleados_RecAdapter extends RecyclerView.Adapter<Empleados_RecAdapter.ViewHolder> {

    String url_detalles_empleados ="https://0f1b-212-8-252-183.ngrok-free.app/tesis_con/public/usuarios/detalle";

    ////Array en el que se guardaran cada elemento de la lista
    private ArrayList<Empleado> empleados_recyclerview;
    private Context context;
    private FragmentManager fragmentManager;
    private NavController navController;



    public Empleados_RecAdapter(ArrayList<Empleado> empleados_recyclerview, Context context,
                                FragmentManager fragmentManager, NavController navController) {
        this.empleados_recyclerview = empleados_recyclerview;
        this.context = context;
        this.fragmentManager=fragmentManager;
        this.navController=navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empleado_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Empleados_RecAdapter.ViewHolder holder, int position) {

        Empleado lista = empleados_recyclerview.get(position);



        holder.emp_nombre.setText("Empleado: " + lista.getNombre() +" "+ lista.getApellido() );

        holder.emp_cedula.setText( "Cedula: " + lista.getCedula());
        holder.emp_usu.setText("Usuario: " + lista.getUsuario());
        holder.emp_tipo.setText("Tipo: " + lista.getTipo());
        holder.empleado = lista;




    }

    @Override
    public int getItemCount() { return empleados_recyclerview.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Se crean variables para las vistas
        private TextView emp_nombre, emp_cedula, emp_usu, emp_tipo;

        Empleado empleado;
        Button empEli, empInfo, empMod;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            emp_nombre = itemView.findViewById(R.id.nom_emp);
            emp_cedula = itemView.findViewById(R.id.ced_emp);
            emp_usu = itemView.findViewById(R.id.user_emp);
            emp_tipo = itemView.findViewById(R.id.tipo_emp);
            empInfo =itemView.findViewById(R.id.infoEmp);
            empMod = itemView.findViewById(R.id.modEmp);
            empEli = itemView.findViewById(R.id.eliminarEmp);

            empEli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elim_but();
                }
            });

            empInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();

                }
            });

            empMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pasarInfo();
                }
            });



        }

        private void elim_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Eliminar Empleado?");



            builder.setPositiveButton("Eliminar", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        public void obtener_detalles(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id",empleado.getId());

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

                        datos_empleado(user, cont, tipo, zona, nombre, apellido, ced,sex,tlfn);

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


        private void datos_empleado(String user, String cont, String tipo, String zona,
        String nombre, String apellido, String ced, String sex, String tlfn){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Detalles Empleado");

            StringBuilder message = new StringBuilder();
            message.append("Nombre: ").append(nombre).append(" ").append(apellido).append("\n");
            message.append("Cedula: ").append(ced).append("\n");
            message.append("Genero: ").append(sex).append("\n");
            message.append("Teléfono: ").append(tlfn).append("\n");
            message.append("Usuario: ").append(user).append("\n");
            message.append("Contraseña: ").append(cont).append("\n");
            message.append("Tipo: ").append(tipo).append("\n");
            message.append("Zona: ").append(zona);

            builder.setMessage(message.toString());




            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void pasarInfo(){
            Bundle bundle = new Bundle();
            bundle.putInt("empID", empleado.getId());

            navController.navigate(R.id.action_fragment_gerente_empleados_to_fragment_modificar_empleado, bundle);

        }


    }


}
