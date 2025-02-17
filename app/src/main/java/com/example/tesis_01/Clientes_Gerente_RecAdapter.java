package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Clientes_Gerente_RecAdapter extends RecyclerView.Adapter<Clientes_Gerente_RecAdapter.ViewHolder> {

    private ArrayList<Cliente> lista_clientes;
    private Context context;
    private  FragmentManager fragmentManager;
    private NavController navController;
    private Fragment_gerente_clientes fragment;

    String url_clientes_detalles = "https://23a8-37-19-221-239.ngrok-free.app/tesis_con/public/clientes/detalles";
    String url_eliminar_clientes =  "https://23a8-37-19-221-239.ngrok-free.app/tesis_con/public/clientes/eliminar";

    public Clientes_Gerente_RecAdapter(ArrayList<Cliente> lista_clientes, Context context, FragmentManager fragmentManager,
                                       NavController navController, Fragment_gerente_clientes frag) {
        this.lista_clientes = lista_clientes;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.navController = navController;
        this.fragment = frag;
    }

    @NonNull
    @Override
    public Clientes_Gerente_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_gerente_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clientes_Gerente_RecAdapter.ViewHolder holder, int position) {
        Cliente cliente = lista_clientes.get(position);

        holder.rif_cli.setText("RIF: " + cliente.getRif());
        holder.raz_soc.setText("Cliente: " + cliente.getRazon_social());

        holder.cli=cliente;

    }

    @Override
    public int getItemCount() {
        return lista_clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView rif_cli, raz_soc;
        MaterialButton info, modi, eliminar;

        Cliente cli;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            rif_cli=itemView.findViewById(R.id.cli_rif_card);
            raz_soc=itemView.findViewById(R.id.cli_razsoc_card);
            info=itemView.findViewById(R.id.infoClie);
            modi=itemView.findViewById(R.id.modclient);
            eliminar=itemView.findViewById(R.id.eliminarclient);

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();
                }
            });

            modi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pasarInfo();
                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elim_but();
                }
            });

        }

        private void elim_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Eliminar Cliente?");

            builder.setPositiveButton("Eliminar", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                elimClient();

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        public void obtener_detalles(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id",cli.getCliente_id());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_clientes_detalles, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("Mensaje", response.toString());

                        JSONObject respuesta = response;

                        String rif = respuesta.getString("rif");
                        String clie=respuesta.getString("razon_social");;
                        String con = respuesta.getString("gerente");
                        String telf=respuesta.getString("telfefono");
                        datos_cliente(rif, clie, con, telf);


                    }catch (JSONException e){
                        Log.d("Error", "Error " + String.valueOf(e));
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        private void datos_cliente(String rif, String clie, String con, String telf){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Detalles Cliente");

            StringBuilder message = new StringBuilder();
            message.append("RIF: ").append(rif).append("\n");
            message.append("Cliente: ").append(clie).append("\n");
            message.append("Contacto: ").append(con).append("\n");
            message.append("Teléfono: ").append(telf);

            builder.setMessage(message.toString());

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void pasarInfo(){
            Bundle bundle = new Bundle();
            bundle.putInt("clienteID", cli.getCliente_id());

            navController.navigate(R.id.action_fragment_gerente_clientes_to_fragment_modificar_clientes, bundle);

        }

        public void elimClient(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_cli",cli.getCliente_id());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_eliminar_clientes, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                    fragment.obtener_clientes();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        private void errorConexion(){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Error:");

            StringBuilder message = new StringBuilder();
            message.append("No se pudo establecer conexion.");

            builder.setMessage(message.toString());

            builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();

        }

    }
}
