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


public class Login extends AppCompatActivity {

    private Button btnEntrar;
    private TextView tvRegistrarse;
    private EditText userName, pass;
    private long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btLogEntrar);
        tvRegistrarse = (TextView) findViewById(R.id.tvAunNo);
        userName = (EditText) findViewById(R.id.etUsuario);
        pass = (EditText) findViewById(R.id.etContraseña);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUserPass();
                if(userid != -1) {
                    Intent in = new Intent(Login.this, VentanaPrincipal.class);
                    in.putExtra("user_id", userid);
                    startActivity(in);
                } else{
                    popUpErrorUserPass();
                }

            }
        });

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, Registro.class);
                startActivity(in);
            }
        });

    }

    //Checks if the user and the pass is correct and if so modifies the userId
    public void checkUserPass(){
        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());
        userid = (int)(long)fastReportDB.checkUserPassBD(userName.getText().toString(), pass.getText().toString());

    }

    //PopUp showed when the user and password don´ match
    public void popUpErrorUserPass() {
        final Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(false);
        customDialog.setContentView(R.layout.pop_up_notificar);
        ((TextView) customDialog.findViewById(R.id.titulo)).setText("¡Error!");
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText("Usuario o contraseña incorrectos");
        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }
}
