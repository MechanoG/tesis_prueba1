package com.example.tesis_01;

import android.content.Context;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class Emp_stat_RecViewAdap extends RecyclerView.Adapter<Emp_stat_RecViewAdap.ViewHolder> {

    private ArrayList<Empleado_statInfo> list_emp;
    private Context context;
    private FragmentManager fragmentManager;

    public Emp_stat_RecViewAdap(ArrayList<Empleado_statInfo> list_emp, Context context, FragmentManager fragmentManager) {
        this.list_emp = list_emp;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_stat_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Empleado_statInfo empleado = list_emp.get(position);

        holder.namEmp.setText("Empleado:" + empleado.getEmp_nam());
        holder.useEmp.setText("Usuario: " + empleado.getEmp_user());
        holder.zonEmp.setText("Zona: " + empleado.getZona());
        holder.pedEmp.setText("Pedidos: " + empleado.getEmp_ped());
        holder.totEmp.setText("Total: " + empleado.getEmp_total());
    }

    @Override
    public int getItemCount() {
        return list_emp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

            TextView namEmp, useEmp, zonEmp, pedEmp, totEmp;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            namEmp = itemView.findViewById(R.id.nam_emp);
            useEmp = itemView.findViewById(R.id.user_emp);
            zonEmp= itemView.findViewById(R.id.zona_emp);
            pedEmp = itemView.findViewById(R.id.ped_emp);
            totEmp = itemView.findViewById(R.id.total_emp);

        }
;

    }

}
