package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class act_Lista_Vehiculos extends AppCompatActivity {

    private TextView tvAnadirNuevo;
    private Button btnAtras;
    private long user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);

        tvAnadirNuevo = (TextView) findViewById(R.id.tvAnyadir);
        btnAtras = (Button) findViewById(R.id.btAtrasMisVeh);

        //Para coger el id del usuario que nos envia la clase ventana principal
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getLong("user_id");
        }



        tvAnadirNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(act_Lista_Vehiculos.this, act_Vehiculo.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(act_Lista_Vehiculos.this, act_Ventana_Principal.class);

                startActivity(myIntent);
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
}
