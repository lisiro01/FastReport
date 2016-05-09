package com.lisis.charles.fastreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Principal;


public class MainActivity extends AppCompatActivity {

    private Button btnEntrar;
    private TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btLogEntrar);
        tvRegistrarse = (TextView) findViewById(R.id.tvAunNo);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, VentanaPrincipal.class);
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
}
