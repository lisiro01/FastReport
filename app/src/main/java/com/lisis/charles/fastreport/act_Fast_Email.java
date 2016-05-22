package com.lisis.charles.fastreport;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import db.DatabaseSQLiteHelper;

public class act_Fast_Email extends AppCompatActivity {


    private static final int constante = 0;
    private int numFoto; // 1 para foto1, 2 para foto2, 3 para foto3

    private ImageButton foto1, foto2, foto3;
    private Button btnGuardar, btnEnviar, btnAtras;
    private TextView tvFecha, tvHora;
    private EditText email;

    private long user_id;

    private ArrayAdapter<String> adaptador;
    private ArrayList<String> vehicles;
    private Spinner comboBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_email);

        foto1 = (ImageButton) findViewById(R.id.btnAnadirFoto1);
        foto2 = (ImageButton) findViewById(R.id.btnAnadirFoto2);
        foto3 = (ImageButton) findViewById(R.id.btnAnadirFoto3);
        btnGuardar = (Button) findViewById(R.id.btGuardFE);
        btnEnviar = (Button) findViewById(R.id.btEnviar);
        btnAtras = (Button) findViewById(R.id.btAtrasFE);
        comboBox = (Spinner) findViewById(R.id.spinner);
        email = (EditText) findViewById(R.id.etEmail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
        }

        numFoto = 0;

        fillDateAndTime();
        fillArrayListOfVehicles();

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

        foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 1;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, constante);
            }
        });

        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 2;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, constante);
            }
        });

        foto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 3;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, constante);
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


    public void fillDateAndTime(){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        tvFecha = (TextView) findViewById(R.id.tvFecha2);
        tvFecha.setText(df.format(c.getTime()));

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");

        tvHora = (TextView) findViewById(R.id.tvHora2);
        tvHora.setText(df2.format(c2.getTime()));

    }

    public void fillArrayListOfVehicles() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicles = fastReportDB.getAllVehiclesByUserIdString(user_id);

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicles);
        comboBox.setAdapter(adaptador);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(numFoto != 0) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap bmp = (Bitmap) extras.get("data");
                switch (numFoto){
                    case 1:
                        foto1.setBackgroundResource(R.drawable.punto);
                        foto1.setImageBitmap(bmp); break;
                    case 2:
                        foto2.setBackgroundResource(R.drawable.punto);
                        foto2.setImageBitmap(bmp); break;
                    case 3:
                        foto3.setBackgroundResource(R.drawable.punto);
                        foto3.setImageBitmap(bmp); break;
                }
            }
        }
        else {
            Toast.makeText(this, "Ha habido un problema con las fotos...", Toast.LENGTH_SHORT).show();
        }

    }

    public void createAccident() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Accident accident = new DB_Accident();
        accident.setUser_id(user_id);

        long vehicle_id = fastReportDB.findVehicleDB(comboBox.getSelectedItem().toString());

        accident.setVehicle_id(vehicle_id);
        accident.setDate(tvFecha.toString());
        accident.setLocation("localizacion"+vehicle_id);
        accident.setEmail_addressee(email.getText().toString());

        //Creating the new vehicle
        long accident_id = fastReportDB.createAccidentDB(accident);


    }

}
