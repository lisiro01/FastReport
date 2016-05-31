package com.lisis.charles.fastreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Extras.DatabaseSQLiteHelper;
import Extras.PopUpHelper;


public class act_Login extends AppCompatActivity {

    private Button btnEntrar;
    private TextView tvRegistrarse;
    private EditText userName, pass;
    private long userid;

    private PopUpHelper popUpHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btLogEntrar);
        tvRegistrarse = (TextView) findViewById(R.id.tvAunNo);
        userName = (EditText) findViewById(R.id.etUsuario);
        pass = (EditText) findViewById(R.id.etContraseña);

        context = this;
        popUpHelper = new PopUpHelper();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUserPass();
                if(userid != -1) {
                    Intent in = new Intent(act_Login.this, act_Ventana_Principal.class);
                    in.putExtra("user_id", userid);
                    startActivity(in);
                } else{
                    popUpHelper.popUpNoAnswer("¡Error!", "Usuario o contraseña incorrectos", context);
                }

            }
        });

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent in = new Intent(act_Login.this, act_Registro.class);
                Intent in = new Intent(act_Login.this, act_Registro.class);
                startActivity(in);
            }
        });

    }

    //Checks if the user and the pass is correct and if so modifies the userId
    public void checkUserPass(){
        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());
        userid = (int)(long)fastReportDB.checkUserPassBD(userName.getText().toString(), pass.getText().toString());
    }

}
