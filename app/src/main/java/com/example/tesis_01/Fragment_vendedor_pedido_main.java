package com.example.tesis_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

public class Fragment_vendedor_pedido_main extends Fragment {

    NavController navController;
    NavHostFragment navHostFragment;

    MaterialToolbar appbar;


    //Variable para la url a donde se realizara la consulta
    String url="http://192.168.0.4/tesis_con/public/pedidos";
    //"http://192.168.0.4/tesis_con/public/pedidos";
//"http://10.0.2.2:80/tesis_con/public/pedidos"






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendedor_pedido_main, container, false);
    }
}