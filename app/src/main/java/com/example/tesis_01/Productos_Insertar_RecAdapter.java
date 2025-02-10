package com.example.tesis_01;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Productos_Insertar_RecAdapter extends
        RecyclerView.Adapter<Productos_Insertar_RecAdapter.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Producto> productos_recyclerview;
    private Pedidos_insertar fragment;

    //Hashmap para guarda la cantidad de productos
    private HashMap<String, Integer> cantidad_producto;

    public Productos_Insertar_RecAdapter(ArrayList<Producto> productos_recyclerview, HashMap<String, Integer> cantidad_producto, Pedidos_insertar fragment) {
        this.productos_recyclerview = productos_recyclerview;
        this.cantidad_producto = cantidad_producto;
        this.fragment = fragment;
    }



    //Referencias a productos_insertar_card
    @NonNull
    @Override
    public Productos_Insertar_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos_pedidos_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Productos_Insertar_RecAdapter.ViewHolder holder, int position) {
        Producto lista = productos_recyclerview.get(position);
        String cod_product = lista.getCodigo();


        holder.producto_cod.setText("COD: " + lista.getCodigo());
        holder.producto_des.setText(lista.getDescripcion());
        holder.producto_ex.setText("Existencias: "+Integer.toString((lista.getCantidad()))+" UNI");
        holder.producto_pre.setText("Precio: "+Float.toString(lista.getPrecio())+" $");

        int cantidad_actual = cantidad_producto.getOrDefault(cod_product, 0);
        holder.cantidad_ped.setText(String.valueOf(cantidad_actual));

        if (cantidad_actual == 0){
            holder.cantidad_ped.setError("La cantidad no puede ser 0");
        }

        holder.remo_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productos_recyclerview.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), productos_recyclerview.size());
                actualizarTotal();
            }
        });



        holder.cantidad_ped.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    int cantidad = Integer.parseInt(editable.toString());
                    int existencias = lista.getCantidad();
                    if (cantidad > existencias){
                        holder.cantidad_ped.setError("Execede el inventario");
                        cantidad_producto.remove(lista.getCodigo());



                    }else if (cantidad == 0) {
                        holder.cantidad_ped.setError("La cantidad no puede ser cero");
                        cantidad_producto.remove(lista.getCodigo());
                    }
                    else{
                        cantidad_producto.put(cod_product, cantidad);
                        actualizarTotal();
                    }
                }else{
                    //Si el campo se deja vacio se remueve la cantida del Hashmap
                    cantidad_producto.remove(cod_product, 1);
                }
            }


        });




    }

    @Override
    public int getItemCount() { return productos_recyclerview.size(); }

    //Se crean variables de vistas
    public class ViewHolder extends RecyclerView.ViewHolder{

        //Se crean las variables
        private TextView producto_cod, producto_des, producto_ex, producto_pre, costo;
        private EditText cantidad_ped;
        private Button remo_pro;



        public ViewHolder (@NonNull View itemView){
            super (itemView);
            producto_cod = itemView.findViewById(R.id.cod_producto);
            producto_des = itemView.findViewById(R.id.des_producto);
            producto_ex = itemView.findViewById(R.id.cantidad_pro);
            producto_pre = itemView.findViewById(R.id.precio_pro);
            cantidad_ped = itemView.findViewById(R.id.cantidad);
            remo_pro = itemView.findViewById(R.id.eliminar_pro);




        }

    }

    //Metodo para actualizar le total en el fragment principal
    private void actualizarTotal(){
        //Llama al metodo total pedido directam,ete del fragmento
        fragment.total_pedido();

    }

}
