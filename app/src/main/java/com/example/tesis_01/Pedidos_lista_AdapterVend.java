package com.example.tesis_01;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pedidos_lista_AdapterVend extends RecyclerView.Adapter<Pedidos_lista_AdapterVend.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Pedidos_lista> lista_pedidos;
    private Context context;
    private FragmentManager fragmentManager;

    public Pedidos_lista_AdapterVend(ArrayList<Pedidos_lista> lista_pedidos, Context context, FragmentManager fragmentManager) {
        this.lista_pedidos = lista_pedidos;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    //Resulta en la referencia a la interfaz "pedidos card de cada" pedidos
    @NonNull
    @Override
    public Pedidos_lista_AdapterVend.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidosvendcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pedidos_lista_AdapterVend.ViewHolder holder, int position) {
        //colocando datos para las vistas del recycle view


        Pedidos_lista lista = lista_pedidos.get(position);

        holder.id_ped.setText("Pedido-" + Integer.toString(lista.getId_pe()));
        holder.raz_cli.setText("Cliente: " + lista.getCliente());
        holder.vend.setText("Vendedor: " + lista.getVendedor());
        holder.total_ped.setText("Total: " + Float.toString(lista.getPe_total()));
        holder.tipo_pago.setText("Tipo de Pago: " + lista.getTi_pago());
        holder.vencimeinto.setText("Se Vence: " + lista.getVencimiento());
        holder.estado.setText("Estado: " + lista.getEstado());
        holder.pedido = lista;

    }

    @Override
    public int getItemCount() {

        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Se crean variables para las vistas

        private TextView id_ped, raz_cli, vend, total_ped, tipo_pago,
                vencimeinto, estado;

        Pedidos_lista pedido;

        String url_pedidos_detalle = "http://192.168.0.5/tesis_con/public/pedidos/pedidos_detalle";


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //se inicializan las vistas con sus ids
            id_ped = itemView.findViewById(R.id.id_pedido);
            raz_cli = itemView.findViewById(R.id.cliente);
            vend = itemView.findViewById(R.id.vende);
            total_ped = itemView.findViewById(R.id.total_pedido);
            tipo_pago = itemView.findViewById(R.id.tipo_pago);
            vencimeinto = itemView.findViewById(R.id.vencimiento);
            estado = itemView.findViewById(R.id.estado_pedido);

            itemView.findViewById(R.id.informacion_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();
                }
            });

        }

        public void obtener_detalles() {

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id_ped", pedido.getId_pe());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_pedidos_detalle, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Fragment_pedidosDetalles_Dialog dialog = Fragment_pedidosDetalles_Dialog.newInstance(respuesta);
                    dialog.show(fragmentManager, "detalles");


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
}