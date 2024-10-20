package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Pedidos_insertar extends Fragment implements AdapterView.OnItemSelectedListener {
    NavController navController;
    TextView header;
    Button cancelar, ingresar;
    Spinner pedido_inv;
    ArrayAdapter inv_list_adap;

    String [] courses = {"C", "No puede ser", "Tengo sueño"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_insertar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Crea el spiner y le aplica OnItemSelectedListener, que le
        //cual item del spinner is clicked

        pedido_inv = view.findViewById(R.id.spin_Gped_inv);
        pedido_inv.setOnItemSelectedListener(this);

        //Crea instancia de ArrayAdapter
        //having the list odf courses

        inv_list_adap = new ArrayAdapter<> (getContext(), android.R.layout.simple_spinner_item,
                courses);

        //set un layout resource file para cada item del Spinner

        inv_list_adap.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        //Set the ArrayAdapter (inv_list_adapt) data
        //on the spiner whic binds data to spinner
        pedido_inv.setAdapter(inv_list_adap);







        header = view.findViewById(R.id.nuevo_pedido);

        //Se cra nevegador del fragment
        navController = Navigation.findNavController(view);

        //Se inicializa boton cancelar.
        cancelar = view.findViewById(R.id.Pedido_Cancelar);
        //Se declara la acciona l presionar boton
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_pedidos_insertar_to_gerente_Pedido_Main);
            }
        });




    }

    //Make a toar con el nombre del string
    //que esseleccionado en el spiner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),courses[i], Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}