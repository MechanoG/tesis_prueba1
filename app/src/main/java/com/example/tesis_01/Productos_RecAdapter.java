package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Productos_RecAdapter extends RecyclerView.Adapter<Productos_RecAdapter.ViewHolder>{

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Producto> productos_recyclerview;
    private Context context;


    public Productos_RecAdapter(ArrayList<Producto> productos_recyclerview, Context context) {
        this.productos_recyclerview = productos_recyclerview;
        this.context = context;
    }

    //referencia a productos card
    @NonNull
    @Override
    public Productos_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Productos_RecAdapter.ViewHolder holder, int position) {

        Producto lista = productos_recyclerview.get(position);

        holder.producto_cod.setText(lista.getCodigo());
        holder.producto_des.setText(lista.getDescripcion());
        holder.producto_can.setText(Integer.toString(lista.getCantidad()));
        holder.producto_pre.setText(Float.toString(lista.getPrecio()));

    }

    @Override
    public int getItemCount() { return productos_recyclerview.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

    //Se crean variables de vistas
        private TextView producto_cod, producto_des, producto_can, producto_pre;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            //se inicializan vistas con ids

            producto_cod = itemView.findViewById(R.id.nom_emp);
            producto_des = itemView.findViewById(R.id.ape_emp);
            producto_can =itemView.findViewById(R.id.ced_emp);
            producto_pre=itemView.findViewById(R.id.precio_pro);

        }
    }
}
