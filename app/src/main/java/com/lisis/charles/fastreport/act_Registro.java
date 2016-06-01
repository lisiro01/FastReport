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
        Toast toast;


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int validation = validateFields();
                switch (validation) {
                    case 0:
                        createUser(user.getText().toString(), pass.getText().toString());
                        popUpRegistroBien("Bienvenido :)", "Ahora conduzca con precaución.");
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "El usuario debe ser un email válido.", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "El usuario ya esta en uso, por favor, elija uno diferente.", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Por su seguridad la contraseña debe contener una mayúscula, una minúscula, un número y ser de 8 caracteres como mínimo.", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
                        break;

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

    public int validateFields() {
        //Regular expression for Password, must has Upper case, one Lower case, one number and at least 8 chars
        String passPattern = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$";
        //Regular expression for Email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!user.getText().toString().trim().matches(emailPattern))
            return 1;
        if (checkIfUserExist(user.getText().toString()))
            return 2;
        if (!pass.getText().toString().trim().matches(passPattern))
            return 3;
        if (!pass.getText().toString().equalsIgnoreCase(pass2.getText().toString()))
            return 4;

        return 0;
    }

}