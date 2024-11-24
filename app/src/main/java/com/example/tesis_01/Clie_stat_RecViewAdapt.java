package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Clie_stat_RecViewAdapt extends RecyclerView.Adapter<Clie_stat_RecViewAdapt.ViewHolder> {

    private ArrayList<Cliente_statInfo> list_clie;
    private Context context;
    private FragmentManager fragmentManager;

    public Clie_stat_RecViewAdapt(ArrayList<Cliente_statInfo> list_clie, Context context, FragmentManager fragmentManager) {
        this.list_clie = list_clie;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Clie_stat_RecViewAdapt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clie_stat_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clie_stat_RecViewAdapt.ViewHolder holder, int position) {
        Cliente_statInfo cliente = list_clie.get(position);

        holder.rif.setText("RIF: " + cliente.getRif());
        holder.razSo.setText("Cliente: " + cliente.getRaz_soc());
        holder.canPed.setText("Pedidos: " + cliente.getPedidos());
        holder.totalCom.setText("Total: " + cliente.getTotal_com());
    }

    @Override
    public int getItemCount() {
        return list_clie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rif, razSo, canPed, totalCom;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            rif = itemView.findViewById(R.id.rif_cli);
            razSo = itemView.findViewById(R.id.raz_so_cli);
            canPed= itemView.findViewById(R.id.can_ped_clie);
            totalCom = itemView.findViewById(R.id.total_com_cli);

        }
    }
}
