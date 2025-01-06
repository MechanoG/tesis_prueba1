package com.example.tesis_01;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.ArrayList;

//Clase adaptador de pedidos, que se encarga de controlar la visualizacion de cada pedidos individual
public class Pedidos_lista_Adapter extends RecyclerView.Adapter<Pedidos_lista_Adapter.ViewHolder> {

    //Array en el que se guardaran cada elemento de la lista
    private ArrayList<Pedidos_lista> lista_pedidos;
    private Context context;
    private FragmentManager fragmentManager;
    private Gerente_Pedido_Main fragment;
    private String archivo_notaVenta = "NotaVenta";


    public Pedidos_lista_Adapter(ArrayList<Pedidos_lista> lista_pedidos, Context context, FragmentManager fragmentManager, Gerente_Pedido_Main frag) {
        this.lista_pedidos = lista_pedidos;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.fragment = frag;
    }


    //Resulta en la referencia a la interfaz "pedidos card de cada" pedidos
    @NonNull
    @Override
    public Pedidos_lista_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_card,parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Pedidos_lista_Adapter.ViewHolder holder, int position) {
        //colocando datos para las vistas del recycle view

        Pedidos_lista lista = lista_pedidos.get(position);

        String estad;

        holder.id_ped.setText("Pedido-" + Integer.toString(lista.getId_pe()));
        holder.raz_cli.setText("Cliente: " + lista.getCliente());
        holder.vend.setText("Vendedor: " + lista.getVendedor());
        holder.total_ped.setText("Total: " + Float.toString(lista.getPe_total()));
        holder.tipo_pago.setText("Tipo de Pago: " + lista.getTi_pago());
        holder.vencimeinto.setText("Se Vence: " + lista.getVencimiento());

        holder.pedido = lista;

        // Restablecer colores predeterminados antes de aplicar cambios
        holder.estado.setBackgroundColor(Color.TRANSPARENT); // Fondo transparente por defecto
        holder.estado.setTextColor(Color.BLACK); // Texto negro por defecto

        estad = lista.getEstado().trim();
        holder.estado.setText("Estado: " + estad);
        if (estad.equals("Vencido")){
            holder.estado.setBackgroundColor(Color.parseColor("#7C0000"));
            holder.estado.setTextColor(Color.WHITE);

        } else if (estad.equals("Pagado")) {
            holder.estado.setBackgroundColor(Color.parseColor("#32930F"));
            holder.estado.setTextColor(Color.WHITE);
            holder.itemView.findViewById(R.id.cancelar_pedido).setBackgroundColor(Color.GRAY);
            holder.itemView.findViewById(R.id.cancelar_pedido).setEnabled(false);
            holder.itemView.findViewById(R.id.pagar_pedido).setBackgroundColor(Color.GRAY);
            holder.itemView.findViewById(R.id.pagar_pedido).setEnabled(false);

        }else{
            holder.estado.setBackgroundColor(Color.TRANSPARENT); // Fondo transparente por defecto
            holder.estado.setTextColor(Color.BLACK); // Texto negro por defecto
        }
    }

    @Override
    public int getItemCount() {
        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Se crean variables para las vistas

         private TextView id_ped, raz_cli, vend, total_ped, tipo_pago,
        vencimeinto, estado;

         Pedidos_lista pedido;

        String url_pedidos_detalle = "http://192.168.0.2/tesis_con/public/pedidos/pedidos_detalle";
        String getUrl_pedidos_pagar = "http://192.168.0.2/tesis_con/public/pedidos/pagar";
        String getUrl_pedidos_eliminar = "http://192.168.0.2/tesis_con/public/pedidos/eliminar";
        String getUrl_pedidos_cancelar = "http://192.168.0.2/tesis_con/public/pedidos/cancel";


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //se inicializan las vistas con sus ids
            id_ped = itemView.findViewById(R.id.id_pedido);
            raz_cli = itemView.findViewById(R.id.cliente);
            vend = itemView.findViewById(R.id.vende);
            total_ped=itemView.findViewById(R.id.total_pedido);
            tipo_pago = itemView.findViewById(R.id.tipo_pago);
            vencimeinto = itemView.findViewById(R.id.vencimiento);
            estado=itemView.findViewById(R.id.estado_pedido);

            itemView.findViewById(R.id.informacion_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtener_detalles();
                }
            });

            itemView.findViewById(R.id.pagar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pagar_but();
                }

            });

            itemView.findViewById(R.id.cancelar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel_but();
                }
            });

            itemView.findViewById(R.id.elimiar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elim_but();
                }
            });

            itemView.findViewById(R.id.pedidoReporte).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reporte_info();
                }
            });

        }

        private void pagar_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Pagar pedido?");



            builder.setPositiveButton("Pagar", (dialogInterface, which)->{

                Log.d("Recivido", "Pagar" );
                pagarPedido();

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void cancel_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Cancelar Pedido?");



            builder.setPositiveButton("Si", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                cancelarPedido();

            });

            builder.setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        private void elim_but(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("¿Eliminar Pedido?");



            builder.setPositiveButton("Eliminar", (dialogInterface, which)->{

                Log.d("Recivido", "Cancelado" );
                eliminarPedido();

            });

            builder.setNegativeButton("Cancelar", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();

        }

        public void obtener_detalles(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_pedidos_detalle, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Fragment_pedidosDetalles_Dialog dialog = Fragment_pedidosDetalles_Dialog.newInstance(respuesta);
                    dialog.show(fragmentManager, "detalles");
                    


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        public void pagarPedido(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped", pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_pagar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                    fragment.mostrar_pedidos();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        public void reporte_info(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url_pedidos_detalle, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());

                    try {
                        JSONObject responseJson = response;

                        String id_ped = responseJson.getString("id_pedido");
                        String raz_soc= responseJson.getString("cli_raz");
                        String rif = responseJson.getString("cli_rif");
                        String zona = responseJson.getString("ped_zon");
                        String telefono= responseJson.getString("cli_tel");
                        String fech_ini = responseJson.getString("fech_rea");
                        String fech_venc = responseJson.getString("fech_lim");

                        String subtotal = responseJson.getString("subtotal");
                        float subto = Float.parseFloat(subtotal);

                        String total = responseJson.getString("total");

                        JSONArray productosA = responseJson.getJSONArray("productos");

                        notaEntrega(id_ped, raz_soc, rif, zona, telefono,fech_ini,fech_venc, subto, total,
                                productosA);

                    }catch (JSONException e){
                        Log.e("DialogFragment", "Error al parsear Json" + e.getMessage());
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        public void eliminarPedido(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_ped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_eliminar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();

                    fragment.mostrar_pedidos();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorConexion();
                    Log.e("Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }

        public void cancelarPedido(){

            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

            //Se crea un JSONObject con los datos que se desean enviar
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("idped",pedido.getId_pe());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //Crear solicitud post
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, getUrl_pedidos_cancelar, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Mensaje", response.toString());
                    String respuesta = response.toString();

                    Toast.makeText(context.getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                    fragment.mostrar_pedidos();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error", error.toString());
                    errorConexion();
                }
            });
            queue.add(jsonObjectRequest);
        }


        private void errorConexion(){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Error:");

            StringBuilder message = new StringBuilder();
            message.append("No se pudo establecer conexion.");

            builder.setMessage(message.toString());

            builder.setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();

        }

        private void notaEntrega(String id, String raz_soc, String rif, String zona,
                                 String telf, String fec_ini, String fec_lim, float subtotal, String total,
                                 JSONArray  productos){
            try{

                OutputStream outputStream = null;
                Uri pdfUri = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    ContentResolver resolver = itemView.getContext().getContentResolver();
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "NotadeVenta" +String.valueOf(id)+".pdf");
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/NotasDeVenta");

                    //Verificar si ya esxiste el arcivo
                    Cursor cursor = resolver.query(
                            MediaStore.Files.getContentUri("external"),
                            null,
                            MediaStore.MediaColumns.RELATIVE_PATH + "=? AND " +
                                    MediaStore.MediaColumns.DISPLAY_NAME + "=?",
                            new String[]{Environment.DIRECTORY_DOWNLOADS+"/NotadeVenta", "NotadeVenta.pdf"},
                            null
                    );

                    if (cursor !=null && cursor.getCount() > 0){
                        //Archivo existe
                        int count = 1;
                        String newName;
                        do{
                            newName = "NotadeVenta" + count +".pdf";
                            count++;
                            cursor = resolver.query(
                                    MediaStore.Files.getContentUri("external"),
                                    null,
                                    MediaStore.MediaColumns.RELATIVE_PATH + "=? AND " +
                                            MediaStore.MediaColumns.DISPLAY_NAME + "=?",
                                    new String[]{Environment.DIRECTORY_DOWNLOADS+"/NotadeVenta", newName},
                                    null);
                        }while (cursor != null && cursor.getCount()>0);

                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, newName);
                    }

                    if (cursor!=null){
                        cursor.close();
                    }

                    pdfUri= resolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
                    if (pdfUri != null){
                        outputStream = resolver.openOutputStream(pdfUri);
                    }
                }else{
                    //Para versiones anteriores a Android 10, usar
                    String filePath = itemView.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/NotaVebta.pdf";
                    outputStream = new FileOutputStream(filePath);
                    Toast.makeText(itemView.getContext(), "PDF guardado en: " + filePath, Toast.LENGTH_LONG).show();
                }

                if (outputStream == null){
                    Toast.makeText(itemView.getContext(), "No se pudo crear el archivo PDF", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Crear el documento PDF
                PdfWriter writer = new PdfWriter(outputStream);
                com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);

                //Inicializar documento
                Document document = new Document(pdfDoc);

                //Añadir event handler para pie de paginaa
                FooterHandler footerHandler = new FooterHandler(subtotal, total);
                pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);

                //Tabla contenerdora
                Table mainContainer = new Table(UnitValue.createPercentArray(new float[]{1}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setBorder(new SolidBorder(1)); // agrega borde al cuadro

                //Agregar encabezado titulo y encabezado
                Cell titlecell = new Cell()
                        .add(new Paragraph("EL OLAM TECH"))
                        .setFontSize(28)
                        .setBold()
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                mainContainer.addCell(titlecell);

                //Agregar esubtitulo
                Cell subtitlecell = new Cell()
                        .add(new Paragraph("MATURÍN, EDO.MONAGAS"))
                        .setFontSize(16)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                mainContainer.addCell(subtitlecell);

                Table infoTablesContainer = new Table(UnitValue.createPercentArray(new float[]{1,1}))
                       .setWidth(UnitValue.createPercentValue(100))
                       .setBorder(Border.NO_BORDER); // Sin borders en el contenedor


                //Detalles del cliente
                Table clientTable = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setBorder(Border.NO_BORDER);

                Cell razCell = new Cell()
                        .add(new Paragraph("Razón Social:"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(razCell);

                Cell raz2Cell = new Cell()
                        .add(new Paragraph(raz_soc))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(raz2Cell);

                //clientTable.addCell("Razón Social:").setBorder(Border.NO_BORDER);
                //clientTable.addCell(raz_soc).setBorder(Border.NO_BORDER);

                Cell rifCell = new Cell()
                        .add(new Paragraph("RIF:"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(rifCell);

                Cell rif2Cell = new Cell()
                        .add(new Paragraph(rif))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(rif2Cell);

                //clientTable.addCell("RIF:").setBorder(Border.NO_BORDER);
                //clientTable.addCell(rif).setBorder(Border.NO_BORDER);

                Cell zonCell = new Cell()
                        .add(new Paragraph("Zona:"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(zonCell);

                Cell zon2Cell = new Cell()
                        .add(new Paragraph(zona))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(zon2Cell);

                //clientTable.addCell("Zona:").setBorder(Border.NO_BORDER);
                //clientTable.addCell(zona).setBorder(Border.NO_BORDER);

                Cell telCell = new Cell()
                        .add(new Paragraph("Teléfono:"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(telCell);

                Cell tel2Cell = new Cell()
                        .add(new Paragraph(telf))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                clientTable.addCell(tel2Cell);

                //clientTable.addCell("Teléfono:").setBorder(Border.NO_BORDER);
                //clientTable.addCell(telf).setBorder(Border.NO_BORDER);

                //containernota
                Table notecontainer = new Table(UnitValue.createPercentArray(new float[]{1}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setBorder(Border.NO_BORDER); // agrega borde al cuadro

                Cell notaEnt = new Cell()
                        .setBold()
                        .add(new Paragraph("NOTA ENTREGA NRO."))
                        .setFontSize(14)
                        .setTextAlignment(TextAlignment.CENTER).
                        setBorder(Border.NO_BORDER);
                notecontainer.addCell(notaEnt);

                Cell nota2Ent = new Cell()
                        .add(new Paragraph(id))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER).
                        setBorder(Border.NO_BORDER);
                notecontainer.addCell(nota2Ent);

                Cell conta = new Cell()
                        .setBold()
                        .add(new Paragraph("CONTADO"))
                        .setFontSize(14)
                        .setTextAlignment(TextAlignment.CENTER).
                        setBorder(Border.NO_BORDER);
                notecontainer.addCell(conta);


                //informacion de la nota
                Table noteTable = new Table(UnitValue.createPercentArray(new float[]{1,1}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setBorder(Border.NO_BORDER);

                Cell fechaini = new Cell()
                        .add(new Paragraph("Fecha Realización:"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                noteTable.addCell(fechaini);

                Cell fechaini2 = new Cell()
                        .add(new Paragraph(fec_ini))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                noteTable.addCell(fechaini2);

                Cell fechaVen = new Cell()
                        .add(new Paragraph("Fecha Vencimiento"))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                noteTable.addCell(fechaVen);

                Cell fechaVen2 = new Cell()
                        .add(new Paragraph(fec_lim))
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.LEFT).
                        setBorder(Border.NO_BORDER);
                noteTable.addCell(fechaVen2);

                //añadirnota a notatable
                notecontainer.addCell(new Cell().add(noteTable).setBorder(Border.NO_BORDER));

                //AÑADIR TABALAS A infotables
                infoTablesContainer.addCell(new Cell().add(clientTable).setBorder(Border.NO_BORDER));
                infoTablesContainer.addCell(new Cell().add(notecontainer).setBorder(Border.NO_BORDER));

                //añadir el contenedor de la tablas al maintable
                mainContainer.addCell(new Cell().add(infoTablesContainer).setBorder(Border.NO_BORDER));

                document.add(mainContainer);
                document.add(new Paragraph("\n"));




                //Tabla de productos
                Table productTable = new Table(UnitValue.createPercentArray(new float[]{2, 5, 2, 2,2,}))
                        .setWidth(UnitValue.createPercentValue(100));

                //Encabezado de la tabla
                productTable.addHeaderCell("Código Producto");
                productTable.addHeaderCell("Descripción");
                productTable.addHeaderCell("Cantidad");
                productTable.addHeaderCell("Precio Unitario");
                productTable.addHeaderCell("Total");

                //Productos
                for (int i =0; i < productos.length(); i++){
                    JSONObject producto = productos.getJSONObject(i);
                    String codigo_producto = producto.getString("codigo_producto");
                    productTable.addCell(codigo_producto);

                    String descripcion = producto.getString("des_producto");
                    productTable.addCell(descripcion);

                    int cantidad = producto.getInt("can_producto");
                    productTable.addCell(String.valueOf(cantidad));

                    String s = producto.getString("pre_producto");
                    float precio =  Float.parseFloat(s);
                    String precio2 = String.valueOf(precio);
                    productTable.addCell(precio2);

                    float totals = precio * cantidad;
                    productTable.addCell(String.valueOf(totals));

                }
                document.add(productTable);
                document.add(new Paragraph("\n"));

                //Totales
                Table totalTable = new Table(UnitValue.createPercentArray(new float[]{6, 2}))
                        .setWidth(UnitValue.createPercentValue(100));

                totalTable.addCell("Subtotal:");
                totalTable.addCell(String.valueOf(subtotal));

                totalTable.addCell("Total:");
                totalTable.addCell(total);
                document.add(totalTable);

                document.close();
                outputStream.close();
                Toast.makeText(itemView.getContext(), "PDF generado correctamente", Toast.LENGTH_SHORT).show();


                //tabla productos
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //Clase para manejo del pie de pagina
        class FooterHandler implements IEventHandler{
            private final float sub;
            private final String tot;

            public  FooterHandler(float subto, String totalPed){
                this.sub = subto;
                this.tot = totalPed;
            }

            @Override
            public void handleEvent (Event event) {
                PdfDocumentEvent pdfEvent = (PdfDocumentEvent) event;
                PdfPage page = pdfEvent.getPage();
                PdfDocument pdfDoc = pdfEvent.getDocument();

                //Tamño de la pagina
                Rectangle pagesize = page.getPageSize();

                // nuevo PdfCanvas para dibujar en la pagina.
                PdfCanvas canvas = new PdfCanvas(page);

                //Canvas de pie de pagina
                Canvas footerCanvas = new Canvas(canvas, pagesize);

                //Tablas de los totales
               /* Table totalTable = new Table(UnitValue.createPercentArray(new float[]{6, 2}))
                        .setWidth(UnitValue.createPercentValue(100));
                totalTable.addCell("Subtotal:");
                totalTable.addCell(String.valueOf(sub));
                totalTable.addCell("Total:");
                totalTable.addCell(tot);*/

                //Dibujar la tabla en la parte inferior de la pagina
                footerCanvas.add(new Paragraph("Pie de pagina")
                        .setFixedPosition(
                                pagesize.getLeft() +36,
                                pagesize.getBottom() + 36,
                                pagesize.getWidth() - 72
                        ));
                footerCanvas.close();

            }

        }
    }
}
