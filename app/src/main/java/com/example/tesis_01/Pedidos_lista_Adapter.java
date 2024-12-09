package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Clase adaptador de pedidos, que se encarga de controlar la visualizacion de cada pedidos individual
public class Pedidos_lista_Adapter extends RecyclerView.Adapter<Pedidos_lista_Adapter.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Pedidos_lista> lista_pedidos;
    private Context context;
    private FragmentManager fragmentManager;

    public Pedidos_lista_Adapter(ArrayList<Pedidos_lista> lista_pedidos, Context context, FragmentManager fragmentManager) {
        this.lista_pedidos = lista_pedidos;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    //Resulta en la referencia a la interfaz "pedidos card de cada" pedidos
    @NonNull
    @Override
    public Pedidos_lista_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_card,parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Pedidos_lista_Adapter.ViewHolder holder, int position) {
        //colocando datos para las vistas del recycle view


        Pedidos_lista lista = lista_pedidos.get(position);

        String estad;

        holder.id_ped.setText("Pedido-" + Integer.toString(lista.getId_pe()));
        holder.raz_cli.setText("Cliente: " + lista.getCliente());
        holder.vend.setText("Vendedor: " + lista.getVendedor());
        holder.total_ped.setText("Total: " + Float.toString(lista.getPe_total()));
        holder.tipo_pago.setText("Tipo de Pago: " + lista.getTi_pago());
        holder.vencimeinto.setText("Se Vence: " + lista.getVencimiento());

        // Restablecer colores predeterminados antes de aplicar cambios
        holder.estado.setBackgroundColor(Color.TRANSPARENT); // Fondo transparente por defecto
        holder.estado.setTextColor(Color.BLACK); // Texto negro por defecto


        estad = lista.getEstado().trim();
        holder.estado.setText("Estado: " + estad);
        if (estad.equals("Vencido")){
            holder.estado.setBackgroundColor(Color.parseColor("#7C0000"));
            holder.estado.setTextColor(Color.WHITE);

        } else if (estad.equals("Pagado")) {
            holder.estado.setBackgroundColor(Color.parseColor("#32930F"));
            holder.estado.setTextColor(Color.WHITE);
            holder.itemView.findViewById(R.id.cancelar_pedido).setBackgroundColor(Color.GRAY);
            holder.itemView.findViewById(R.id.cancelar_pedido).setEnabled(false);

        }else{
            holder.estado.setBackgroundColor(Color.TRANSPARENT); // Fondo transparente por defecto
            holder.estado.setTextColor(Color.BLACK); // Texto negro por defecto

        }






    }

    @Override
    public int getItemCount() {

        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Se crean variables para las vistas

         private TextView id_ped, raz_cli, vend, total_ped, tipo_pago,
        vencimeinto, estado;

         Pedidos_lista pedido;

        String url_pedidos_detalle = "https://0f1b-212-8-252-183.ngrok-free.app/tesis_con/public/pedidos/pedidos_detalle";
        String getUrl_pedidos_pagar = "https://0f1b-212-8-252-183.ngrok-free.app/tesis_con/public/pedidos/pagar";
        String getUrl_pedidos_eliminar = "https://0f1b-212-8-252-183.ngrok-free.app/tesis_con/public/pedidos/eliminar";

        String riggerBut;

        int pos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //se inicializan las vistas con sus ids
            id_ped = itemView.findViewById(R.id.id_pedido);
            raz_cli = itemView.findViewById(R.id.cliente);
            vend = itemView.findViewById(R.id.vende);
            total_ped=itemView.findViewById(R.id.total_pedido);
            tipo_pago = itemView.findViewById(R.id.tipo_pago);
            vencimeinto = itemView.findViewById(R.id.vencimiento);
            estado=itemView.findViewById(R.id.estado_pedido);







            itemView.findViewById(R.id.informacion_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();
                }
            });

            itemView.findViewById(R.id.pagar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pagar_but();
                }
            });

            itemView.findViewById(R.id.cancelar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel_but();
                }
            });

            itemView.findViewById(R.id.elimiar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elim_but();
                }
            });

        }

        private void pagar_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Pagar pedido?");



            builder.setPositiveButton("Pagar", (dialogInterface, which)->{

                Log.d("Recivido", "Pagar" );
                pagarPedido();





            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void cancel_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Cancelar Pedido?");



            builder.setPositiveButton("Si", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                eliminarPedido();

            });

            builder.setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void elim_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Eliminar Pedido?");



            builder.setPositiveButton("Eliminar", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                eliminarPedido();

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        public void obtener_detalles(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
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

        public void pagarPedido(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_pagar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        public void eliminarPedido(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_eliminar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();



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
