package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MisVehiculos extends AppCompatActivity {

    private TextView tvAnadirNuevo;
    private Button btnAtras;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vehiculos);

        tvAnadirNuevo = (TextView) findViewById(R.id.tvAnyadir);
        btnAtras = (Button) findViewById(R.id.btAtrasMisVeh);

        tvAnadirNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog();
            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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