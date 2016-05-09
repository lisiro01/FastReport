package com.lisis.charles.fastreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button btnEntrar;
    private TextView tvRegistrarse;
    private EditText userName, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btLogEntrar);
        tvRegistrarse = (TextView) findViewById(R.id.tvAunNo);
        userName = (EditText) findViewById(R.id.etUsuario);
        pass = (EditText) findViewById(R.id.etContraseña);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, VentanaPrincipal.class);
                in.putExtra("username", userName.toString());
                startActivity(in);

            }
        });

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Registro.class);
                startActivity(in);
            }
        });
    }


    public String getNombreUsuario (){
        return userName.toString();
    }
}
