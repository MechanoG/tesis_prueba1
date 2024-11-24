package com.example.tesis_01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.producto_des.setText("Nombre " + producto.getDescripcion());
        holder.producto_can.setText("Existencias " + Integer.toString(producto.getCantidad()));
        holder.producto_pre.setText("Precio: " + Float.toString(producto.getPrecio()) + "$");



    }

    @Override
    public int getItemCount() {
        return productos_recyclerview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView producto_cod, producto_des, producto_can, producto_pre;

        Button restar, sumar, eliminar;


        public ViewHolder (@NonNull View itemView){
            super (itemView);

            producto_cod = itemView.findViewById(R.id.cod_producto);
            producto_des = itemView.findViewById(R.id.ape_emp);
            producto_can =itemView.findViewById(R.id.ced_emp);
            producto_pre=itemView.findViewById(R.id.precio_pro);
            sumar = itemView.findViewById(R.id.agregar_pro);
            restar = itemView.findViewById(R.id.quitar_pro);
            eliminar = itemView.findViewById(R.id.elimiar_producto);


            sumar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Botton", "Agregar existencias");

                }
            });

            restar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Botton", "Remover existencias");
                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Botton", "Elimar producto");
                }
            });


        }



    }
}
