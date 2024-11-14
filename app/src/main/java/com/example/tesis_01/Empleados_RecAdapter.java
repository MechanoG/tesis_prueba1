package com.example.tesis_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Empleados_RecAdapter extends RecyclerView.Adapter<Empleados_RecAdapter.ViewHolder> {

    ////Array en el que se guardaran cada elemento de la lista
    private ArrayList<Empleado> empleados_recyclerview;
    private Context context;


    public Empleados_RecAdapter(ArrayList<Empleado> empleados_recyclerview, Context context) {
        this.empleados_recyclerview = empleados_recyclerview;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empleado_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Empleados_RecAdapter.ViewHolder holder, int position) {

        Empleado lista = empleados_recyclerview.get(position);

        holder.emp_nombre.setText(lista.getNombre());
        holder.emp_ape.setText(lista.getApellido());
        holder.emp_cedula.setText(lista.getCedula());
        holder.emp_sexo.setText(lista.getSexo());
        holder.emp_telf.setText(lista.getTelf());
        holder.emp_usu.setText(lista.getUsuario());
        holder.emp_cont.setText(lista.getContraseña());
        holder.emp_tipo.setText(lista.getTipo());

    }

    @Override
    public int getItemCount() { return empleados_recyclerview.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Se crean variables para las vistas
        private TextView emp_nombre, emp_ape, emp_cedula, emp_sexo, emp_telf, emp_usu, emp_cont, emp_tipo;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            emp_nombre = itemView.findViewById(R.id.cod_producto);
            emp_ape = itemView.findViewById(R.id.ape_emp);
            emp_cedula = itemView.findViewById(R.id.ced_emp);
            emp_sexo = itemView.findViewById(R.id.sex_emp);
            emp_telf = itemView.findViewById(R.id.tel_emp);
            emp_usu = itemView.findViewById(R.id.user_emp);
            emp_cont = itemView.findViewById(R.id.con_emp);
            emp_tipo = itemView.findViewById(R.id.tipo_emp);


        }



    }


}
