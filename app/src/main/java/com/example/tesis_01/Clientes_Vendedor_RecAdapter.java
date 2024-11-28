package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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

public class Clientes_Vendedor_RecAdapter extends RecyclerView.Adapter<Clientes_Vendedor_RecAdapter.ViewHolder> {

    ////Array en el que se guardaran cada elemento de la lista
    private ArrayList<Cliente> clientes_recyclerview;
    private Context context;
    private FragmentManager fragmentManager;

    String url_clientes_detalles = "http://192.168.0.5/tesis_con/public/clientes/detalles";

    public Clientes_Vendedor_RecAdapter(ArrayList<Cliente> clientes_recyclerview, Context context, FragmentManager fragmentManager) {
        this.clientes_recyclerview = clientes_recyclerview;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    //referencia a productos card


    @NonNull
    @Override
    public Clientes_Vendedor_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_vendedor_card, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clientes_Vendedor_RecAdapter.ViewHolder holder, int position) {

        Cliente cliente = clientes_recyclerview.get(position);

        holder.client_rif.setText(cliente.getRif());
        holder.client_razsoc.setText(cliente.getRazon_social());
        holder.cli=cliente;

    }

    @Override
    public int getItemCount() {return clientes_recyclerview.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Se crean variables para las vistas
        private TextView  client_rif, client_razsoc;

        MaterialButton info;

        Cliente cli;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            client_rif = itemView.findViewById(R.id.cli_rif_card);
            client_razsoc = itemView.findViewById(R.id.cli_razsoc_card);
            info=itemView.findViewById(R.id.infoClie);

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();

                }
            });

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



    }
}
