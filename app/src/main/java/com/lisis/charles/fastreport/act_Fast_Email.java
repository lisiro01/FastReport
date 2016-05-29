package com.lisis.charles.fastreport;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import db.DatabaseSQLiteHelper;
import db.PopUpHelper;

public class act_Fast_Email extends AppCompatActivity implements LocationListener {

    //FOTOS
    private static final int CONSTANTE = 0;
    private int numFoto; // 1 para foto1, 2 para foto2, 3 para foto3
    private ImageButton foto1, foto2, foto3;
    private byte[] image1, image2, image3;
    //FOTOS

    //MAPA
    protected LocationManager locationManager;
    private Location location;
    private boolean gpsActivo;
    private String date, hour, altitud, longitud;
    //MAPA

    //INTERFAZ
    private Button btnGuardar, btnEnviar, btnAtras, localizacion;
    private TextView tvFecha, tvHora;
    private EditText email;
    //INTERFAZ

    private long user_id;
    private long accident_id;
    private long user_accident_id;

    private ArrayAdapter<String> adaptador;
    private ArrayList<String> vehicles;
    private Spinner comboBox;

    private PopUpHelper popUpHelper;
    private Context context;

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
        localizacion = (Button) findViewById(R.id.button);
        comboBox = (Spinner) findViewById(R.id.spinner);
        email = (EditText) findViewById(R.id.etEmail);


        context = this;
        popUpHelper = new PopUpHelper();

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
                createAccident();
                popUpHelper.popUpNoAnswer(String.valueOf(accident_id), "Se ha guardado correctamente", context);
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

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/" + altitud + ","+ longitud + "/data=!4m4!2m3!3m1!2s40.4290314,-3.6591383!4b1?nogmmr=1"));
                startActivity(in);
            }
        });

        foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 1;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, CONSTANTE);
            }
        });

        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 2;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, CONSTANTE);
            }
        });

        foto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoto = 3;
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, CONSTANTE);
            }
        });
    }

    public void fillDateAndTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        tvFecha = (TextView) findViewById(R.id.tvFecha2);
        date = df.format(c.getTime());
        tvFecha.setText(date);

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");

        tvHora = (TextView) findViewById(R.id.tvHora2);
        hour = df2.format(c2.getTime());
        tvHora.setText(hour);

    }

    public void fillArrayListOfVehicles() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicles = fastReportDB.getAllVehiclesByUserIdString(user_id);

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicles);
        comboBox.setAdapter(adaptador);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (numFoto != 0) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap bmp = (Bitmap) extras.get("data");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);

                switch (numFoto) {
                    case 1:
                        image1 = bos.toByteArray();

                        foto1.setBackgroundResource(R.drawable.punto);

                        foto1.setImageBitmap(bmp);
                        break;
                    case 2:
                        image2 = bos.toByteArray();

                        foto2.setBackgroundResource(R.drawable.punto);
                        foto2.setImageBitmap(bmp);
                        break;
                    case 3:
                        image3 = bos.toByteArray();

                        foto3.setBackgroundResource(R.drawable.punto);
                        foto3.setImageBitmap(bmp);
                        break;
                }
            }
        } else {
            Toast.makeText(this, "Ha habido un problema con las fotos...", Toast.LENGTH_SHORT).show();
        }

    }

    public void getLocation() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        gpsActivo = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

        try {
            if (gpsActivo) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, 1);
                    }


                }
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 0, this);

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        altitud = String.valueOf(location.getLatitude());
                        longitud = String.valueOf(location.getLongitude());
                    }
                }

            }
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Ha habido un problema con la localizacion...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location){

        if(location != null) {
            altitud = String.valueOf(location.getLatitude());
            longitud = String.valueOf(location.getLongitude());
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){}
    @Override
    public void onProviderEnabled(String provider){}
    @Override
    public void onProviderDisabled(String provider){}



    public void createAccident() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Accident accident = new DB_Accident();
        accident.setUser_id(user_id);

        long vehicle_id = fastReportDB.findVehicleDB(comboBox.getSelectedItem().toString());

        Toast.makeText(context, "" + vehicle_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "" + date, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "" + hour, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "" + altitud, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "" + email.getText().toString(), Toast.LENGTH_SHORT).show();

        accident.setVehicle_id(vehicle_id);
        accident.setDate(date);
        accident.setHour(hour);
        accident.setLocation(altitud);
        accident.setImage1(image1);
        accident.setImage2(image2);
        accident.setImage3(image3);
        accident.setEmail_addressee(email.getText().toString());

        //Creating the new vehicle
        accident_id = fastReportDB.createAccidentDB(accident);

        //Creating the relationship betwen the vehicle and the user
        user_accident_id = fastReportDB.createUserAccidentDB(user_id, accident_id);


    }

}