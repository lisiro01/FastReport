package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import db.DatabaseSQLiteHelper;

/**
 * Created by Lis on 14/5/16.
 */
public class act_Vehiculo extends AppCompatActivity {

    private EditText marca, modelo, numMat, aseguradora, numPol;
    private Button bTatras, bTguardar;
    private long vehicle_id, user_id, user_vehicle_id;
    private int saveOrMod;

    private String numMatricula;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechiculo);

        bTatras = (Button) findViewById(R.id.btAtrasVeh);
        bTguardar = (Button) findViewById(R.id.btGuardarVeh);
        marca = (EditText) findViewById(R.id.etMarca);
        modelo = (EditText) findViewById(R.id.etModel);
        numMat = (EditText) findViewById(R.id.etMat);
        aseguradora = (EditText) findViewById(R.id.etAseg);
        numPol = (EditText) findViewById(R.id.etNumPol);


        //Para coger el id del usuario que nos envia la clase lista de vehiculos
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
            saveOrMod = extras.getInt("saveOrMod");
            if(saveOrMod == 1){
                numMatricula = extras.getString("mat_vehiculo");
            }
        }


        bTatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bTguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Verify if you want to save a new vehicle or modify an existing vehicle
                if (saveOrMod == 2) {
                    if (!checkIfVehicleExist(numMat.getText().toString())) {
                        createVehicle();
                        popUpSavedVehicle();
                    } else {
                        popUpErrorVehicle(); //TODO: limpiar campos
                    }

                } else if (saveOrMod == 1) {
                    updateVehicle(vehicle_id);
                    popUpSavedVehicle();

                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public boolean checkIfVehicleExist(String reg_number) {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicle_id = fastReportDB.findVehicleDB(reg_number);

        if (vehicle_id != -1)
            return true;
        return false;
    }


    public void createVehicle() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Vehicle vehicle = new DB_Vehicle();
        vehicle.setBrand(marca.getText().toString());
        vehicle.setInsurance(aseguradora.getText().toString());
        vehicle.setModel(modelo.getText().toString());
        vehicle.setPolicyNumber(numPol.getText().toString());
        vehicle.setRegistrationNumber(numMat.getText().toString());

        //Creating the new vehicle
        vehicle_id = fastReportDB.createVehicleDB(vehicle);

        //Creating the relationship betwen the vehicle and the user
        user_vehicle_id = fastReportDB.createUserVehicleDB(user_id, vehicle_id);

    }

    public void updateVehicle(long vehicle_id) {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Vehicle vehicle = new DB_Vehicle();
        vehicle.setBrand(marca.getText().toString());
        vehicle.setInsurance(aseguradora.getText().toString());
        vehicle.setModel(modelo.getText().toString());
        vehicle.setPolicyNumber(numPol.getText().toString());
        vehicle.setRegistrationNumber(numMat.getText().toString());

        //Updating the vehicle
        fastReportDB.updateVehicleDB(vehicle, vehicle_id);

    }


    //Fills all the edit texts with the vehicle´s data
    public void fillVehicleData() {

        numMat.setText(numMatricula);

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicle_id = fastReportDB.findVehicleDB(numMatricula);
        DB_Vehicle vehicle = fastReportDB.getVehicleDB(vehicle_id);

        if (vehicle != null) {
            //Fills all edit texts
            marca.setText(vehicle.getBrand());
            modelo.setText(vehicle.getModel());
            numMat.setText(vehicle.getRegistrationNumber());
            aseguradora.setText(vehicle.getInsurance());
            numPol.setText(vehicle.getPolicyNumber());
        }
    }


    public void popUpSavedVehicle() {
        final Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(false);
        customDialog.setContentView(R.layout.pop_up_notificar);
        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Éxito!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("El vehículo se ha guardado correctamente");
        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog.dismiss();
                finish();
            }
        });
        customDialog.show();
    }


    //PopUp showed when the vehicle already exists
    public void popUpErrorVehicle() {
        final Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(false);
        customDialog.setContentView(R.layout.pop_up_notificar);
        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Error!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("EL vehículo ya existe");
        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "act_Vehiculo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lisis.charles.fastreport/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        if(saveOrMod == 1)
            fillVehicleData();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "act_Vehiculo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lisis.charles.fastreport/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}












