package com.lisis.charles.fastreport;

import android.app.Dialog;
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
import db.SQLiteHelper;

public class DatosPersonales extends AppCompatActivity {


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
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // aqui hay que meter los datos en la bbdd, si se inserto bien, entonces de muestr popup

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


        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    public void cargardatos(){
        SQLiteHelper admin = new SQLiteHelper(getApplicationContext(), "admin", null, 1);

            nomb.setText(admin.cargarDatos());

    }

    //Updating user´s method. Already have email/user and pass
    public void updateUserInfo(){
        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());
        User user = new User();
        user.setName(nomb.getText().toString());
        user.setLastname(apell.getText().toString());
        user.setAddress(dir.getText().toString());
        user.setDriverLicense(numLic.getText().toString());
        user.setPhoneNumber(tel.getText().toString());
        user.setExpiration_date(fechaVenc.getText().toString());

        //TODO: coger el long user_id y pasarlo aqui en esta funcion de segundo parametro.
        fastReportDB.updateUserDB(user, user_id);
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DatosPersonales Page", // TODO: Define a title for the content shown.
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
                "DatosPersonales Page", // TODO: Define a title for the content shown.
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

