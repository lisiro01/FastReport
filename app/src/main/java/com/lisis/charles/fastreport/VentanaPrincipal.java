package com.lisis.charles.fastreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class VentanaPrincipal extends AppCompatActivity {


    private Button btnFastEm, btnDatPers, btnMisVeh, btnHisAcc, btnAtras;
    private String nomUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnFastEm = (Button) findViewById(R.id.btFastEmail);
        btnDatPers = (Button) findViewById(R.id.btDatPersPRINC);
        btnMisVeh = (Button) findViewById(R.id.btMisVeh);
        btnHisAcc = (Button) findViewById(R.id.btHistAcc);
        btnAtras = (Button) findViewById(R.id.btAtrasPRINC);

        //Para coger lo que nos envia la otra clase (username )
        Bundle param = getIntent().getExtras();

        if(param != null){
            nomUser = param.getString("username");
        }


        btnFastEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VentanaPrincipal.this, FastEmail.class);
                startActivity(in);
            }
        });

        btnDatPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VentanaPrincipal.this, DatosPersonales.class);
                startActivity(in);
            }
        });

        btnMisVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VentanaPrincipal.this, MisVehiculos.class);
                startActivity(in);
            }
        });

        btnHisAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VentanaPrincipal.this, HistorialAccidentes.class);
                startActivity(in);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
