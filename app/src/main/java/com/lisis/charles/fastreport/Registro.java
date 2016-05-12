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

public class Registro extends AppCompatActivity {

    private Button btnRegistrarse, btnAtras;
    private EditText user, pass, pass2;
    private long user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnRegistrarse = (Button) findViewById(R.id.btRegistrarseR);
        btnAtras = (Button) findViewById(R.id.btAtrasR);
        user = (EditText) findViewById(R.id.edNombreUsuario);
        pass = (EditText) findViewById(R.id.edContraseñaR);
        pass2 = (EditText) findViewById(R.id.edRepite);


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())) {
                    createUser(user.getText().toString(), pass.getText().toString());
                    //This allows us to send the userId to the other activities.
                    Intent myIntent = new Intent(getApplicationContext(), VentanaPrincipal.class);
                    myIntent.putExtra("user_id", user_id);
                    startActivity(myIntent);
                } else {
                    popUpErrorPass();
                }


            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Creating a user for the very first time
    public void createUser(String user, String pass){
        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        long user_Id = fastReportDB.createUserDB(user, pass);
    }

    //PopUp showed when the passwords don´ match
    public void popUpErrorPass() {
        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Error!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Las contraseñas no coinciden");

        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });

        customDialog.show();
    }
}
