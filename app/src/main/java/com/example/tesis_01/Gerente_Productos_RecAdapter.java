package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class Gerente_Productos_RecAdapter extends RecyclerView.Adapter<Gerente_Productos_RecAdapter.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Producto> productos_recyclerview;
    private Context context;

    public Gerente_Productos_RecAdapter(ArrayList<Producto> productos_recyclerview, Context context) {
        this.productos_recyclerview = productos_recyclerview;
        this.context = context;
    }

    @NonNull
    @Override
    public Gerente_Productos_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_card_gerentes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Gerente_Productos_RecAdapter.ViewHolder holder, int position) {

        Producto producto =  productos_recyclerview.get(position);

        holder.producto_cod.setText("COD: " + producto.getCodigo());
        holder.producto_des.setText("Descripcion: " + producto.getDescripcion());
        holder.producto_can.setText("Existencias: " + Integer.toString(producto.getCantidad()));
        holder.producto_pre.setText("Precio: " + Float.toString(producto.getPrecio()) + "$");
        holder.pro =producto;


    }

    @Override
    public int getItemCount() {
        return productos_recyclerview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView producto_cod, producto_des, producto_can, producto_pre;

        Button restar, sumar, eliminar, modif;

        Producto pro;

        String url_prodcuto_eliminar = "http://192.168.0.3/tesis_con/public/productos/eliminar";
        String url_producto_aumentar = "http://192.168.0.3/tesis_con/public/productos/aumentar";
        String url_producto_reducir = "http://192.168.0.3/tesis_con/public/productos/remover";
        String url_producto_cambiar_precio = "http://192.168.0.3/tesis_con/public/productos/precio";

        public ViewHolder (@NonNull View itemView){
            super (itemView);

            producto_cod = itemView.findViewById(R.id.cod_producto);
            producto_des = itemView.findViewById(R.id.ape_emp);
            producto_can =itemView.findViewById(R.id.ced_emp);
            producto_pre=itemView.findViewById(R.id.precio_pro);
            sumar = itemView.findViewById(R.id.agregar_pro);
            restar = itemView.findViewById(R.id.quitar_pro);
            eliminar = itemView.findViewById(R.id.elimiar_producto);
            modif = itemView.findViewById(R.id.Modificar);




            sumar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Botton", "Agregar existencias");
                    masProDia();

                }
            });

            restar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("Botton", "Remover existencias");
                    menosProDia();
                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Botton", "Eliminar producto");
                    elim_but();
                }
            });

            modif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modPrecio();
                }
            });


        }

        private void masProDia(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ingrese cantidad a agregar");

            //Cuadro de texto
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Aceptar", (dialogInterface, which)->{
                String numero = input.getText().toString().trim();
                Log.d("Recivido", numero );

                int cant = Integer.parseInt(numero);
                auementarProducto(cant);

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void menosProDia(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ingrese cantidad a quitar");

            //Cuadro de texto
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Aceptar", (dialogInterface, which)->{
                String numero = input.getText().toString();
                Log.d("Recivido", numero );
                int cant = Integer.parseInt(numero);
                removerProducto(cant);



            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }


        private void elim_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Eliminar Producto?");



            builder.setPositiveButton("Eliminar", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                eliminarProducto();

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }
        private void modPrecio(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ingrese nuevo precio");

            //Cuadro de texto
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Aceptar", (dialogInterface, which)->{
                String numero = input.getText().toString();
                Log.d("Recivido", numero );
                int nuePrecio = Integer.parseInt(numero);
                nuevoPrecio(nuePrecio);





            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }


        public void eliminarProducto(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_pro",pro.getId());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_prodcuto_eliminar, jsonObject, new Response.Listener<JSONObject>() {
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

        public void auementarProducto(int cant){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_pro",pro.getId());
                jsonObject.put("addCan",cant);

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_producto_aumentar, jsonObject, new Response.Listener<JSONObject>() {
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

        public void removerProducto(int cant){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_pro",pro.getId());
                jsonObject.put("addCan",cant);

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_producto_reducir, jsonObject, new Response.Listener<JSONObject>() {
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

        public void nuevoPrecio(int precio){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_pro",pro.getId());
                jsonObject.put("new_pre", precio);

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_producto_cambiar_precio, jsonObject, new Response.Listener<JSONObject>() {
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
