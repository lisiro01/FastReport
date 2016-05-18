package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import db.DatabaseSQLiteHelper;

/**
 * Created by Lis on 14/5/16.
 */
public class act_Vehiculo extends AppCompatActivity {

    private EditText marca, modelo, numMat, aseguradora, numPol;
    private Button bTatras, bTguardar;
    private long vehicle_id, user_id, user_vehicle_id;



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
        }


        bTatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), act_Lista_Vehiculos.class);
                startActivity(in);
            }
        });

        bTguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVehicleInfo();
                popUpSavedVehicle();
            }
        });


    }



    public void popUpSavedVehicle() {

        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Guardado!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Se ha guardado correctamente el vehículo");
        customDialog.show();

        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                customDialog.dismiss();
                Intent myIntent = new Intent(getApplicationContext(), act_Lista_Vehiculos.class);
                startActivity(myIntent);
            }
        });


    }

    public void createVehicleInfo(){

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_Vehicle vehicle= new DB_Vehicle();
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



}












