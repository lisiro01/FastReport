package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import db.DatabaseSQLiteHelper;

public class act_Lista_Vehiculos2 extends AppCompatActivity {


    private TextView tvAnadirNuevo;
    private Button btnAtras;
    private ListView listaVehiculos;
    //usaremos este adaptador para trabajar con el listView
    private ArrayAdapter<String> adaptador;
    private SimpleCursorAdapter adapter;
    private ArrayList<String> vehicles;

    private long user_id;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);

        tvAnadirNuevo = (TextView) findViewById(R.id.tvAnyadir);
        btnAtras = (Button) findViewById(R.id.btAtrasMisVeh);
        listaVehiculos = (ListView) findViewById(R.id.listViewVehiculos);


        //Para coger el id del usuario que nos envia la clase ventana principal
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
        }


        tvAnadirNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(act_Lista_Vehiculos2.this, act_Vehiculo.class);
                myIntent.putExtra("user_id", user_id);
                myIntent.putExtra("saveOrMod", 2);
                startActivity(myIntent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(act_Lista_Vehiculos2.this, act_Ventana_Principal.class);

                startActivity(myIntent);
            }
        });

        listaVehiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String num_vehiculo = listaVehiculos.getItemAtPosition(position).toString();

                Intent myIntent = new Intent(act_Lista_Vehiculos2.this, act_Vehiculo.class);
                myIntent.putExtra("user_id", user_id);
                myIntent.putExtra("saveOrMod", 1);
                myIntent.putExtra("mat_vehiculo", num_vehiculo);
                startActivity(myIntent);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void fillArrayListOfVehicles() {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        vehicles = fastReportDB.getAllVehiclesByUserIdString(user_id);
    }


    public void mostrarDialog() {
        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Añadir!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Aqui se añadirá el nuevo vehículo a la BD");


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
                "act_Lista_Vehiculos Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lisis.charles.fastreport/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        fillArrayListOfVehicles();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicles);
        listaVehiculos.setAdapter(adaptador);
    }


}
