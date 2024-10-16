package com.example.tesis_01;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Pedidos_lista_Adapter extends RecyclerView.Adapter<Pedidos_lista_Adapter.ViewHolder> {

    private ArrayList<Pedidos_lista> lista_pedidos;
    private Context context;

    public Pedidos_lista_Adapter(ArrayList<Pedidos_lista> lista_pedidos, Context context) {
        this.lista_pedidos = lista_pedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public Pedidos_lista_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pedidos_lista_Adapter.ViewHolder holder, int position) {
        holder.textView.setText(lista_pedidos.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView2);
        }
    }
}
