package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_pedidosDetalles_Dialog extends DialogFragment {

    private String detalles;  // representa la id con la que se solicitara la informacion;
    private ImageView cierre;      //Boton para cerrar dialog

    //Se declaran los textview;
    TextView ped_id, vend, clie_rif, clie_raz, ped_zona,form_pag, fec_ini,
             fec_lim, fec_pag, estado, subtotal, total, mod_pag, fact, iva;

    ArrayList<Producto> pro_p_pedido;

    RecyclerView productos_recy;

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
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

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

        //Se referecian los textview


        ped_id = view.findViewById(R.id.pedidoId);
        vend = view.findViewById(R.id.vendedor);
        clie_rif = view.findViewById(R.id.clienteRif);
        clie_raz = view.findViewById(R.id.clienteRaz);
        ped_zona = view.findViewById(R.id.Zona);
        form_pag = view.findViewById(R.id.forma_pago);
        fec_ini = view.findViewById(R.id.fec_ini);
        fec_lim = view.findViewById(R.id.fec_lim);
        fec_pag = view.findViewById(R.id.fec_pag);
        estado = view.findViewById(R.id.estado);
        subtotal = view.findViewById(R.id.subtotal);
        iva=view.findViewById(R.id.iva);
        total = view.findViewById(R.id.total);
        productos_recy = view.findViewById(R.id.pro_lis);
        mod_pag= view.findViewById(R.id.modPag);
        fact= view.findViewById(R.id.fact);


        cierre = view.findViewById(R.id.closeDialog);

        cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(detalles);

            //Extraer valores Json y asignarlos a cada textview
            ped_id.setText("   PED-" + jsonObject.optString("id_pedido", "N/A"));
            vend.setText("   Vendedor: " + jsonObject.optString("ven_nom", "N/A"));
            clie_rif.setText("   Rif Cliente: " + jsonObject.optString("cli_rif", "N/A"));
            clie_raz.setText("   Cliente: " + jsonObject.optString("cli_raz", "N/A"));
            ped_zona.setText("   Zona: " + jsonObject.optString("ped_zon", "N/A"));
            form_pag.setText("   Tipo Pago: " + jsonObject.optString("ped_pag", "N/A"));
            fec_ini.setText("   Realizada: " + jsonObject.optString("fech_rea", "N/A"));
            fec_lim.setText("   Vence: " + jsonObject.optString("fech_lim", "N/A"));
            fec_pag.setText("   Fecha Pago: " + jsonObject.optString("fech_pag", "N/A"));
            estado.setText("   Estado: " + jsonObject.optString("estado", "N/A"));
            subtotal.setText("   Subtotal: " + jsonObject.optString("subtotal", "N/A"));
            iva.setText("   IVA: " + jsonObject.optString("IVA", "N/A"));
            total.setText("   Total: " + jsonObject.optString("total", "N/A"));
            mod_pag.setText("   Modo Pago: " + jsonObject.optString("mod_pag", "N/A"));
            fact.setText("   Comprobante: " + jsonObject.optString("comp", "N/A"));;

            JSONArray productosA = jsonObject.getJSONArray("productos");

            Log.d("elementos en el array", String.valueOf(productosA.length()));

            pro_p_pedido = new ArrayList<Producto>();

            for ( int i = 0; i < productosA.length(); i++ ){
                JSONObject producto = productosA.getJSONObject(i);
                Log.d("Producto", producto.getString("des_producto"));

                int id= producto.getInt("id_producto");
                String codigo_producto = producto.getString("codigo_producto");
                String descripcion = producto.getString("des_producto");
                int cantidad = producto.getInt("can_producto");
                String s = producto.getString("pre_producto");
                float precio =  Float.parseFloat(s);


                pro_p_pedido.add(new Producto(id, precio, cantidad, descripcion, codigo_producto));
                build_products_recycleview();
            }

            Log.d("Array", "el lardo del arraylist es:" + Integer.toString(pro_p_pedido.size()));

            build_products_recycleview();

        }catch (JSONException e){
            Log.e("DialogFragment", "Error al parsear Json" + e.getMessage());
        }



    }

    private void build_products_recycleview(){

        //se inicia el adaptador de la clase
        Vendedor_Productos_RecAdapter productos_view = new Vendedor_Productos_RecAdapter(pro_p_pedido, getContext());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        productos_recy.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        productos_recy.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        productos_recy.setAdapter(productos_view);

    }


}