package com.lisis.charles.fastreport;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import db.DatabaseSQLiteHelper;

public class MostrarAccidente extends AppCompatActivity {


    private Button btnGuardar, btnEnviar, btnAtras, localizacion;
    private TextView tvFecha, tvHora;
    private EditText email;

    private ImageButton foto1, foto2, foto3;
    private byte[] image1, image2, image3;

    private ArrayAdapter<String> adaptador;
    private ArrayList<String> vehicles;
    private Spinner comboBox;

    private long user_id;
    private long accident_id;
    private String date, hour, altitud, longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_email);

        tvFecha = (TextView) findViewById(R.id.tvFecha2);
        tvHora = (TextView) findViewById(R.id.tvHora2);
        foto1 = (ImageButton) findViewById(R.id.btnAnadirFoto1);
        foto2 = (ImageButton) findViewById(R.id.btnAnadirFoto2);
        foto3 = (ImageButton) findViewById(R.id.btnAnadirFoto3);
        btnGuardar = (Button) findViewById(R.id.btGuardFE);
        btnEnviar = (Button) findViewById(R.id.btEnviar);
        btnAtras = (Button) findViewById(R.id.btAtrasFE);
        localizacion = (Button) findViewById(R.id.button);
        comboBox = (Spinner) findViewById(R.id.spinner);
        email = (EditText) findViewById(R.id.etEmail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
            date = extras.getString("date");
            hour = extras.getString("hour");
        }

        fillAccidentData();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/" + altitud + "," + longitud + "/data=!4m4!2m3!3m1!2s40.4290314,-3.6591383!4b1?nogmmr=1"));
                startActivity(in);
            }
        });
    }

    public void fillAccidentData(){

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        accident_id = fastReportDB.findAccidentDB(date, hour);
        DB_Accident accidente = fastReportDB.getAccidentDB(accident_id);

        if (accidente != null) {

            tvFecha.setText(accidente.getHour());
            tvHora.setText(accidente.getDate());
            foto1.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage1(), 0, accidente.getImage1().length));
            foto2.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage2(), 0, accidente.getImage2().length));
            foto3.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage3(), 0, accidente.getImage3().length));
            altitud = accidente.getLocation();
            longitud = accidente.getLocation();
            rellenaComboBox(accidente);
            email.setText(accidente.getEmail_addressee());
        }
    }

    public void rellenaComboBox(DB_Accident accidente) {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Vehicle vehicle = fastReportDB.getVehicleDB(accidente.getVehicle_id());

        vehicles.add(vehicle.getRegistrationNumber());

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicles);
        comboBox.setAdapter(adaptador);
    }

}
