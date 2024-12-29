package com.example.tesis_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_info_productos extends Fragment {

    //Se declaran los elemntos del fragment. botones y listeners
    private Button sel_fecha;
    private TextView fecha;

    private Spinner meses;
    private Spinner year;

    RecyclerView pro_statInf;

    ArrayList<Producto_statInfo> list_product;

    String fechConsul;


    private String[] mesesArray = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiempbre", "Octubre", "Noviembre", "Diciembre"};

    private String[] yearsArray = {"2024","2025","2026","2027","2028","2029","2030","2031","2032",
            "2033","2034","2035","2036","2037","2038","2039","2040"};

    String url_masvendidos = "http://192.168.0.3/tesis_con/public/productos/masvendidos";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Se inicializa recuvler viewoducto_statInfo
        pro_statInf = view.findViewById(R.id.pro_stat_recy);

        //Se crea el arrau de los pedidos
        list_product = new ArrayList<Producto_statInfo>();

        // on below line we are initializing our variables.
        sel_fecha = view.findViewById(R.id.masVendidos);
        sel_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fechConsul = fechaConsulta();
                Log.d("Fecha", fechConsul);
                Log.d("Sellama a los productos", "Se obtienen productos");
                obtener_productosStats();
                Log.d("Se llamo a los productos", "Se obtuvieron productos");
            }
        });

        fecha = view.findViewById(R.id.idTvSelectedate);

        //Spinners
        meses=view.findViewById(R.id.spinMeses);
        year = view.findViewById(R.id.spinYear);

        //
        ArrayAdapter mesesAd = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mesesArray);

        ArrayAdapter yearsAd = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                yearsArray);

        meses.setAdapter(mesesAd);
        year.setAdapter(yearsAd);





    }

    private String fechaConsulta(){
        //SE OBTIENe mes seleciionando la posiscion del espiner
        int mesPosicion = meses.getSelectedItemPosition() +1;
        String mesElegido = String.format("%02d", mesPosicion);

        //mes elegifo
        String yearElegido = year.getSelectedItem().toString();

        //Se formatea Fecha como YYYY-MM

        Log.d("Fecha", yearElegido + "-" +mesElegido);

        return yearElegido + "-" +mesElegido;

    }


    public void obtener_productosStats(){

        //Limpia la lista antes de realizar una nueva consulta
        list_product.clear();
        if (pro_statInf.getAdapter() !=null){
            //Notifica al adaptador para limpiar la vista
            pro_statInf.getAdapter().notifyDataSetChanged();
        }

        RequestQueue  queue = Volley.newRequestQueue(getContext());

        //Se crea json arrau como filtro
        JSONArray array = new JSONArray();

        //josn objet para fecha
        JSONObject jsonParam = new JSONObject();

        try {
            //Se agregan los aprasm strinf
            jsonParam.put("fecha", fechaConsulta());

        }catch (JSONException e){
            e.printStackTrace();
        }

        array.put(jsonParam);

        JsonArrayRequest request_json = new JsonArrayRequest(Request.Method.POST, url_masvendidos
                , array,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Se obtiene respuesta final
                            if (response != null && response.length() > 0) {
                                Log.d("Respuesta", response.toString());  // Mostrar la respuesta completa
                            } else {
                                Log.d("Respuesta", "Respuesta vacía recibida");
                            }

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Log.d("Objetio", jsonObject.toString() );

                                int idPro = jsonObject.getInt("id");
                                String codPro = jsonObject.getString("pro_cod");
                                String desPro = jsonObject.getString("pro_des");
                                int canVen = jsonObject.getInt("cantidad_ven");
                                String s = jsonObject.getString("pre_pro");
                                String z = jsonObject.getString("total_pro");
                                float precio =  Float.parseFloat(s);
                                float pedido_total =  Float.parseFloat(z);

                                list_product.add(new Producto_statInfo(idPro, codPro, desPro, canVen,
                                        precio,pedido_total));
                                buildRecycleview();

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String,String> headers = new HashMap<String,String>();
                //ADD headers
                return headers;
            }

        };
        queue.add(request_json);



    }

    private void buildRecycleview(){
        //se inicia el adaptador de la clase
        Info_stat_RecViewApadt adapInPro = new Info_stat_RecViewApadt(list_product, getContext(), getParentFragmentManager());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        pro_statInf.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        pro_statInf.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        pro_statInf.setAdapter(adapInPro);

    }





}