package com.lisis.charles.fastreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import Extras.DatabaseSQLiteHelper;

public class HistorialAccidentes extends AppCompatActivity {

    private Button btnAtras;

    private long user_id;
    private String date, hour;
    private ListView listaAccidentes;

    private ArrayAdapter<String> adaptador;
    private SimpleCursorAdapter adapter;
    private ArrayList<String> accidentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        btnAtras = (Button) findViewById(R.id.btAtrasHistAcc);
        listaAccidentes = (ListView) findViewById(R.id.listViewAccidentes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
        }

        fillArrayListOfVehicles();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaAccidentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date_accidente = listaAccidentes.getItemAtPosition(position).toString();

                String[] splited = date_accidente.split("\\s+");

                Intent myIntent = new Intent(HistorialAccidentes.this, MostrarAccidente.class);
                myIntent.putExtra("user_id", user_id);
                myIntent.putExtra("date", splited[0]);
                myIntent.putExtra("hour", splited[1]);
                startActivity(myIntent);

            }
        });

    }


    public void fillArrayListOfVehicles() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        accidentes = fastReportDB.getAllAccidentsByUserIdString(user_id);

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accidentes);
        listaAccidentes.setAdapter(adaptador);
    }



}
