package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Clase adaptador de pedidos, que se encarga de controlar la visualizacion de cada pedidos individual
public class Pedidos_lista_Adapter extends RecyclerView.Adapter<Pedidos_lista_Adapter.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Pedidos_lista> lista_pedidos;
    private Context context;


    public Pedidos_lista_Adapter(ArrayList<Pedidos_lista> lista_pedidos, Context context) {
        this.lista_pedidos = lista_pedidos;
        this.context = context;
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

        holder.id_ped.setText(Integer.toString(lista.getId_pe()));
        holder.id_cli.setText(Integer.toString(lista.getId_cli()));
        holder.id_use.setText(Integer.toString(lista.getId_use()));
        holder.total_ped.setText(Float.toString(lista.getPe_total()));
        holder.estado.setText(lista.getEstado());


    }

    @Override
    public int getItemCount() {
        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Se crean variables para las vistas
         private TextView id_ped, id_cli, id_use, total_ped, estado;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //se inicializan las vistas con sus ids
            id_ped = itemView.findViewById(R.id.id_pedido);
            id_cli = itemView.findViewById(R.id.id_cliente);
            id_use =itemView.findViewById(R.id.id_user);
            total_ped=itemView.findViewById(R.id.total_pedido);
            estado=itemView.findViewById(R.id.estado_pedido);
        }
    }
}
