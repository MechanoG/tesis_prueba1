package com.example.tesis_01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu_gerente extends AppCompatActivity {

    //Inicio textViews
    TextView id, empleado, usuario, contraseña, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_gerente);

        //Inicializa textviwews
        id = findViewById(R.id.sp_id);
        empleado = findViewById(R.id.sp_idemp);
        usuario = findViewById(R.id.sp_usuario);
        contraseña = findViewById(R.id.sp_contraseña);
        tipo = findViewById(R.id.sp_tipo);


        //Carga informacion del usuario
        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref",MODE_PRIVATE);

        String Et_id = Integer.toString(sharedPreferences.getInt("id", 0));
        String Et_empid = Integer.toString(sharedPreferences.getInt("id_empleado", 0));
        String Et_usuario = sharedPreferences.getString("usuario", "");
        String Et_cont = sharedPreferences.getString("contraseña", "");
        String Et_tipo = sharedPreferences.getString("tipo", "");

        //Muestra la informacion del usuario
        id.setText(Et_id);
        empleado.setText(Et_empid);
        usuario.setText(Et_usuario);
        contraseña.setText(Et_cont);
        tipo.setText(Et_tipo);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}