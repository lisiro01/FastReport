package com.lisis.charles.fastreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class VentanaPrincipal extends AppCompatActivity {


    private Button btnFastEm, btnDatPers, btnMisVeh, btnHisAcc, btnAtras;
    private long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnFastEm = (Button) findViewById(R.id.btFastEmail);
        btnDatPers = (Button) findViewById(R.id.btDatPersPRINC);
        btnMisVeh = (Button) findViewById(R.id.btMisVeh);
        btnHisAcc = (Button) findViewById(R.id.btHistAcc);
        btnAtras = (Button) findViewById(R.id.btAtrasPRINC);

        //Para coger lo que nos envia la otra clase (user_id )

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             user_id = extras.getLong("user_id");
        }

        btnFastEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), FastEmail.class);
                startActivity(in);
            }
        });

        btnDatPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), DatosPersonales.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnMisVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), act_lista_Vehiculos.class);
                startActivity(in);
            }
        });

        btnHisAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), HistorialAccidentes.class);
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
