package com.lisis.charles.fastreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class act_Ventana_Principal extends AppCompatActivity {


    private Button btnFastEm, btnDatPers, btnMisVeh, btnHisAcc, btnAtras;
    private long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

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
                Intent myIntent = new Intent(getApplicationContext(), act_Fast_Email.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnDatPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), act_Datos_Personales.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnMisVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), act_Lista_Vehiculos2.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnHisAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), HistorialAccidentes.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acerca_de, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.info:
                Intent in = new Intent(getApplicationContext(), act_Acerca_de.class);
                startActivity(in);
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }
}
