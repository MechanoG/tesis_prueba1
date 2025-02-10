package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.appbar.MaterialToolbar;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class Fragment_pantallaPagar extends Fragment {

    private int pedId;

    String url_obtener_inf_ped = "http://192.168.0.5/tesis_con/public/pedidos/pedidos_detalle";
    String getUrl_pedidos_pagar = "http://192.168.0.5/tesis_con/public/pedidos/pagar";

    private EditText fact, mod_pag, total, client;
    private Button cancelar, pagar;


    NavController navController;

    MaterialToolbar appbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pantalla_pagar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            pedId = getArguments().getInt("pedidoId");
        }

        obtener_detalles();

        //Inputs
        fact = view.findViewById(R.id.comp_input);
        mod_pag = view.findViewById(R.id.modInput);
        total = view.findViewById(R.id.cant_input);
        client = view.findViewById(R.id.clie_input);

        //Botones
        cancelar = view.findViewById(R.id.BotonCancelar);
        pagar = view.findViewById(R.id.BotonPagar);

        navController = Navigation.findNavController(view);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        pagar = view.findViewById(R.id.BotonPagar);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagar_but();

            }
        });

        appbar = view.findViewById(R.id.topAppBar);
        appbar.setNavigationOnClickListener(
                v -> navController.popBackStack());

    }

    public void obtener_detalles(){

        RequestQueue queue = Volley.newRequestQueue(getContext());

        //Se crea un JSONObject con los datos que se desean enviar
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id_ped", pedId);

        }catch (JSONException e){
            e.printStackTrace();
        }

        //Crear solicitud post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url_obtener_inf_ped, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Mensaje", response.toString());

                JSONObject jsonobject = response;

                try {
                    total.setText(jsonobject.getString("total"));
                    client.setText(jsonobject.getString("cli_raz"));


                } catch (JSONException e){
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //errorConexion();
                Log.e("Error", error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }




    private void pagar_but(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Pagar pedido?");



        builder.setPositiveButton("Pagar", (dialogInterface, which)->{
            pagar_pedido();
            Log.d("Recivido", "Pagar" );



        });

        builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
        builder.show();

    }

    private void pagar_pedido(){
        //Se declaran valores
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //Se crea JSONobject con los datos a enviar
        JSONObject jsonObject = new JSONObject();
        String fac = fact.getText().toString().trim();
        String modPag = mod_pag.getText().toString().trim();

        if(!fac.isEmpty() && !modPag.isEmpty()){

            try {
                jsonObject.put("id_ped", pedId);
                jsonObject.put("fac_ped", fac);
                jsonObject.put("mod_ped", modPag);

            }catch (JSONException e){
                e.printStackTrace();
            }
            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_pagar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    navController.popBackStack();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });

            queue.add(jsonObjectRequest);

        }else{
            errorEmptyData();
        }




    }

    private void errorEmptyData(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error:");

        StringBuilder message = new StringBuilder();
        message.append("Por favor rellene todo los campos.");

        builder.setMessage(message.toString());

        builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();

    }



    private void errorConexion(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error:");

        StringBuilder message = new StringBuilder();
        message.append("No se pudo establecer conexion.");

        builder.setMessage(message.toString());

        builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();

    }


}