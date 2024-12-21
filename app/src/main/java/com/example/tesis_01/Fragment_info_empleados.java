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

public class Fragment_info_empleados extends Fragment {

    //Se declaran los elemntos del fragment. botones y listeners
    private Button sel_fecha;
    private TextView fecha;

    private Spinner meses;
    private Spinner year;

    RecyclerView emp_statInf;

    ArrayList<Empleado_statInfo> list_emp;

    String fechConsul;

    private String[] mesesArray = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiempbre", "Octubre", "Noviembre", "Diciembre"};

    private String[] yearsArray = {"2024","2025","2026","2027","2028","2029","2030","2031","2032",
            "2033","2034","2035","2036","2037","2038","2039","2040"};

    String url_clie_stats = "http://192.168.0.3/tesis_con/public/usuarios/maseficientes";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_info_empleados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onViewCreated(view, savedInstanceState);

        //Se inicializa recuvler viewoducto_statInfo
        emp_statInf = view.findViewById(R.id.emp_stat_recy);

        //Se crea el arrau de los pedidos
        list_emp = new ArrayList<Empleado_statInfo>();



        // on below line we are initializing our variables.
        sel_fecha = view.findViewById(R.id.masVendidos);
        sel_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fechConsul = fechaConsulta();
                Log.d("Fecha", fechConsul);
                obtener_clientStats();
                buildRecycleview();


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

        return yearElegido + "-" +mesElegido;

    }


    public void obtener_clientStats(){

        RequestQueue queue = Volley.newRequestQueue(getContext());

        //Se crea json arrau como filtro
        JSONArray array = new JSONArray();

        //josn objet para fecha
        JSONObject jsonParam = new JSONObject();

        try {
            //Se agregan los aprasm strinf
            jsonParam.put("fecha", "2024-11");

        }catch (JSONException e){
            e.printStackTrace();
        }

        array.put(jsonParam);

        JsonArrayRequest request_json = new JsonArrayRequest(Request.Method.POST, url_clie_stats, array,
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

                                Log.d("Objeto", jsonObject.toString() );

                                int idEmp = jsonObject.getInt("id");
                                String empNam = jsonObject.getString("nombre") + " " + jsonObject.getString("apellido") ;
                                String empUser = jsonObject.getString("usuario");
                                String zona = jsonObject.getString("zona");
                                int empPed = jsonObject.getInt("ped_rea");
                                String s = jsonObject.getString("total_pedidos");
                                float total =  Float.parseFloat(s);


                                list_emp.add(new Empleado_statInfo(idEmp, empNam, empUser, zona, empPed, total));

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                //ADD headers
                return headers;
            }
            //Important

            /*
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                String responseString;
                JSONArray array = new JSONArray();
                if (response != null) {

                    try {
                        responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject obj = new JSONObject(responseString);
                        (array).put(obj);
                    } catch (Exception ex) {
                    }
                }
                //return array;
                return Response.success(array, HttpHeaderParser.parseCacheHeaders(response));
            } */
        };
        queue.add(request_json);



    }

    private void buildRecycleview(){
        //se inicia el adaptador de la clase
        Emp_stat_RecViewAdap adapEmpStat = new Emp_stat_RecViewAdap(list_emp, getContext(), getParentFragmentManager());

        //agregar layout manager
        //al recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        emp_statInf.setHasFixedSize(true);

        //se le da el layout managerr al recycle view
        emp_statInf.setLayoutManager(manager);

        //Se establece el adaptador al recycle View
        emp_statInf.setAdapter(adapEmpStat);

    }
}