package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Extras.DatabaseSQLiteHelper;
import Extras.PopUpHelper;

public class act_Registro extends AppCompatActivity {


    private static final int CONSTANTE = 0; // necesario para el startActivityForResult
    private Button btnRegistrarse, btnAtras;
    private EditText user, pass, pass2;
    private long user_id = 0;

    private Dialog customDialog;
    private PopUpHelper popUpHelper;
    private Context context;
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnRegistrarse = (Button) findViewById(R.id.btRegistrarseR);
        btnAtras = (Button) findViewById(R.id.btAtrasR);
        user = (EditText) findViewById(R.id.edNombreUsuario);
        pass = (EditText) findViewById(R.id.edContraseñaR);
        pass2 = (EditText) findViewById(R.id.edRepite);

        context = this;
        popUpHelper = new PopUpHelper();


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().trim().matches(emailPattern)) {
                    if (!checkIfUserExist(user.getText().toString())) {
                        if (pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())) {

                            createUser(user.getText().toString(), pass.getText().toString());
                            popUpRegistroBien("Bienvenido :)", "Ahora solo conduzca con precaución.");
                        } else {
                            popUpHelper.popUpNoAnswer("Lo sentimos :(", "Las contraseñas no coinciden.", context);
                        }
                    } else {
                        popUpHelper.popUpNoAnswer("Lo sentimos :(", "Ese nombre de usuario está en uso.", context);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_SHORT).show();
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
    public void createUser(String user, String pass) {

        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        user_id = fastReportDB.createUserDB(user, pass);
    }

    //Checks if a user exist
    public boolean checkIfUserExist(String user) {


        DatabaseSQLiteHelper fastReportDB = new DatabaseSQLiteHelper(getApplicationContext());

        user_id = fastReportDB.findUserDB(user);

        if (user_id != -1)
            return true;
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        finish();
    }


    public void popUpRegistroBien(String title, String message) {
        customDialog = popUpHelper.popUpGeneral(title, message, context);
        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();

                Intent myIntent = new Intent(getApplicationContext(), act_Ventana_Principal.class);
                myIntent.putExtra("user_id", user_id);
                startActivityForResult(myIntent, CONSTANTE);
            }
        });
    }


}
