package com.lisis.charles.fastreport;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import Extras.DatabaseSQLiteHelper;
import Extras.GenerateMailHelper;

public class MostrarAccidente extends AppCompatActivity {


    private Button btnGuardar, btnEnviar, btnAtras, localizacion;
    private TextView tvFecha, tvHora, tvCoche;
    private EditText email;

    private ImageButton foto1, foto2, foto3;
    private byte[] image1, image2, image3;

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
        tvCoche = (TextView) findViewById(R.id.tvMuestraCoche);
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

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();
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

            tvFecha.setText(accidente.getDate());
            tvHora.setText(accidente.getHour());

            foto1.setBackgroundResource(R.drawable.punto);
            if(accidente.getImage1()!=null)
                foto1.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage1(), 0, accidente.getImage1().length));

            foto2.setBackgroundResource(R.drawable.punto);
            if(accidente.getImage2()!=null)
                foto2.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage2(), 0, accidente.getImage2().length));

            foto3.setBackgroundResource(R.drawable.punto);
            if(accidente.getImage3()!=null)
                foto3.setImageBitmap(BitmapFactory.decodeByteArray(accidente.getImage3(), 0, accidente.getImage3().length));


            if(accidente.getLocation() != null) {

                String localizacion = accidente.getLocation();;

                String[] splited = localizacion.split("\\s+");

                altitud = splited[0];
                longitud = splited[1];
            }

            rellenaComboBox(accidente);

            email.setText(accidente.getEmail_addressee());

            btnGuardar.setVisibility(View.GONE);
        }
    }

    public void rellenaComboBox(DB_Accident accidente) {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Vehicle vehicle = fastReportDB.getVehicleDB(accidente.getVehicle_id());

        tvCoche.setText(vehicle.getRegistrationNumber());

        comboBox.setVisibility(View.INVISIBLE);
    }

    public void enviarCorreo(){

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());


        DB_Accident accident = fastReportDB.getAccidentDB(accident_id);
        DB_User user = fastReportDB.getUserDB(accident.getUser_id());
        DB_Vehicle vehicle = fastReportDB.getVehicleDB(accident.getVehicle_id());

        GenerateMailHelper gcm = new GenerateMailHelper(user, vehicle, accident);

        String mail = gcm.generarMail();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Accidente a dia " + accident.getDate() + " a las " + accident.getHour());
        emailIntent.putExtra(Intent.EXTRA_TEXT, mail);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviando correo"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
