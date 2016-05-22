package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import db.DatabaseSQLiteHelper;

public class act_Fast_Email extends AppCompatActivity {



    private Button btnGuardar, btnEnviar, btnAtras;

    private long user_id;

    private ArrayAdapter<String> adaptador;
    private ArrayList<String> vehicles;
    private Spinner comboBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_email);

        btnGuardar = (Button) findViewById(R.id.btGuardFE);
        btnEnviar = (Button) findViewById(R.id.btEnviar);
        btnAtras = (Button) findViewById(R.id.btAtrasFE);
        comboBox = (Spinner) findViewById(R.id.spinner);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
        }

        fillArrayListOfVehicles();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicles);
        comboBox.setAdapter(adaptador);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void mostrarDialog()
    {
        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Guardado!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Se ha guardado correctamente");


        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });

        customDialog.show();
    }


    public void fillArrayListOfVehicles() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicles = fastReportDB.getAllVehiclesByUserIdString(user_id);
    }


}
