package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_pedidosDetalles_Dialog extends DialogFragment {

    String detalles;  // representa la id con la que se solicitara la informacion;

    public static Fragment_pedidosDetalles_Dialog newInstance(String detalles){
        Fragment_pedidosDetalles_Dialog fragment = new Fragment_pedidosDetalles_Dialog();
        Bundle args = new Bundle();
        args.putSerializable("detalles", detalles);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detalles = getArguments().getString("detalles");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_detalles__dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Log.d("Respuesta devolley", detalles);



    }


}