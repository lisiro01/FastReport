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

public class act_Datos_Personales extends AppCompatActivity {


    private Button btnGuardar, btnAtras;
    private EditText nomb, apell, dir, tel, numLic, fechaVenc;
    private GoogleApiClient client;
    private long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);


        btnGuardar = (Button) findViewById(R.id.btGuardarDatos);
        btnAtras = (Button) findViewById(R.id.btAtrasDatos);
        nomb = (EditText) findViewById(R.id.etNombre);
        apell = (EditText) findViewById(R.id.edApellidos);
        dir = (EditText) findViewById(R.id.edDireccion);
        tel = (EditText) findViewById(R.id.etTel);
        numLic = (EditText) findViewById(R.id.edNPermiso);
        fechaVenc = (EditText) findViewById(R.id.etFVenc);


        //Para coger lo que nos envia la otra clase (user_id )
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
            fillUserData();
        }



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserInfo();
                mostrarDialog();

            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void mostrarDialog() {

        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Guardado!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Se ha guardado correctamente");
        customDialog.show();

        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                customDialog.dismiss();
                Intent myIntent = new Intent(getApplicationContext(), act_Ventana_Principal.class);
                startActivity(myIntent);
            }
        });


    }



    //Updating user´s method. Already have email/user and pass
    public void updateUserInfo(){

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_User DBUser = new DB_User();
        DBUser.setName(nomb.getText().toString());
        DBUser.setLastname(apell.getText().toString());
        DBUser.setAddress(dir.getText().toString());
        DBUser.setDriverLicense(numLic.getText().toString());
        DBUser.setPhoneNumber(tel.getText().toString());
        DBUser.setExpiration_date(fechaVenc.getText().toString());

        fastReportDB.updateUserDB(DBUser, user_id);
    }


    //Fills all the edit texts with the user´s data
    public void fillUserData (){

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        DB_User user = new DB_User();
        user = fastReportDB.getUserDB(user_id);
        if(user != null) {
            //Fills all edit texts
            nomb.setText(user.getName());
            apell.setText(user.getLastname());
            dir.setText(user.getAddress());
            tel.setText(user.getPhoneNumber());
            numLic.setText(user.getExpiration_date());
            fechaVenc.setText(user.getExpiration_date());
        }

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "act_Datos_Personales Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lisis.charles.fastreport/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "act_Datos_Personales Page", // TODO: Define a title for the content shown.
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
