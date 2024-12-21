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

public class Info_stat_RecViewApadt extends RecyclerView.Adapter<Info_stat_RecViewApadt.ViewHolder> {

    //Array en el que se
    private ArrayList<Producto_statInfo> list_product;
    private Context context;
    private FragmentManager fragmentManager;

    public Info_stat_RecViewApadt(ArrayList<Producto_statInfo> list_product, Context context, FragmentManager fragmentManager) {
        this.list_product = list_product;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Info_stat_RecViewApadt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_stat_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Info_stat_RecViewApadt.ViewHolder holder, int position) {
        Producto_statInfo producto = list_product.get(position);

        holder.cod.setText("COD-PRO: " + producto.getCod_pro());
        holder.des.setText("Producto" + producto.getDes_pro());
        holder.cant.setText("Vendidos:" + Integer.toString(producto.getCant_ven()) + " UNI");
        holder.total.setText("Total: " + "1040 $"/*Float.toString(producto.getCant_ven())*/);




    }

    @Override
    public int getItemCount() {
        return list_product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Variables de las vistas
        TextView cod, des, cant, total;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            cod = itemView.findViewById(R.id.cod_pro);
            des = itemView.findViewById(R.id.des_pro);
            cant = itemView.findViewById(R.id.cant_ven);

            total = itemView.findViewById(R.id.total_pro);



        }

    }
}
