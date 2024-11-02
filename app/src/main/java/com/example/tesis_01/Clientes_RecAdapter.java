package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Clientes_RecAdapter extends RecyclerView.Adapter<Clientes_RecAdapter.ViewHolder> {

    ////Array en el que se guardaran cada elemento de la lista
    private ArrayList<Cliente> clientes_recyclerview;
    private Context context;

    public Clientes_RecAdapter(ArrayList<Cliente> clientes_recyclerview, Context context) {
        this.clientes_recyclerview = clientes_recyclerview;
        this.context = context;
    }

    //referencia a productos card


    @NonNull
    @Override
    public Clientes_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_card, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clientes_RecAdapter.ViewHolder holder, int position) {

        Cliente lista = clientes_recyclerview.get(position);

        holder.client_id.setText(String.valueOf(lista.getCliente_id()));
        holder.client_rif.setText(lista.getRif());
        holder.client_razsoc.setText(lista.getRazon_social());

    }

    @Override
    public int getItemCount() {return clientes_recyclerview.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Se crean variables para las vistas
        private TextView client_id, client_rif, client_razsoc;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            client_id = itemView.findViewById(R.id.id_cli_card);
            client_rif = itemView.findViewById(R.id.cli_rif_card);
            client_razsoc = itemView.findViewById(R.id.cli_razsoc_card);

        }
    }
}
