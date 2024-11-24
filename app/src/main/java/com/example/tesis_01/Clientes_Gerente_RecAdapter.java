package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Clientes_Gerente_RecAdapter extends RecyclerView.Adapter<Clientes_Gerente_RecAdapter.ViewHolder> {

    private ArrayList<Cliente> lista_clientes;
    private Context context;
    private  FragmentManager fragmentManager;

    public Clientes_Gerente_RecAdapter(ArrayList<Cliente> lista_clientes, Context context, FragmentManager fragmentManager) {
        this.lista_clientes = lista_clientes;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Clientes_Gerente_RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_gerente_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clientes_Gerente_RecAdapter.ViewHolder holder, int position) {
        Cliente cliente = lista_clientes.get(position);

        holder.rif_cli.setText("RIFT: " + cliente.getRif());
        holder.raz_soc.setText("Cliente: " + cliente.getRazon_social());

        holder.cli=cliente;

    }

    @Override
    public int getItemCount() {
        return lista_clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView rif_cli, raz_soc;
        MaterialButton info, modi, eliminar;

        Cliente cli;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            rif_cli=itemView.findViewById(R.id.cli_rif_card);
            raz_soc=itemView.findViewById(R.id.cli_razsoc_card);
            info=itemView.findViewById(R.id.infoClie);
            modi=itemView.findViewById(R.id.modclient);
            eliminar=itemView.findViewById(R.id.eliminarclient);



        }


    }
}
