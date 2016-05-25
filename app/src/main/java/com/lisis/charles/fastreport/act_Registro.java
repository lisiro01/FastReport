package com.lisis.charles.fastreport;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import db.DatabaseSQLiteHelper;
import db.PopUpHelper;

public class act_Registro extends AppCompatActivity {

    private Button btnRegistrarse, btnAtras;
    private EditText user, pass, pass2;
    private long user_id = 0;
    PopUpHelper popUpHelper;
    final Dialog dialog;

    public act_Registro(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnRegistrarse = (Button) findViewById(R.id.btRegistrarseR);
        btnAtras = (Button) findViewById(R.id.btAtrasR);
        user = (EditText) findViewById(R.id.edNombreUsuario);
        pass = (EditText) findViewById(R.id.edContraseñaR);
        pass2 = (EditText) findViewById(R.id.edRepite);
        popUpHelper = new PopUpHelper();




        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIfUserExist(user.getText().toString())) {
                    if (pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())) {
                        createUser(user.getText().toString(), pass.getText().toString());
                        popUpHelper.popUpGeneral("Éxito :)", "Bienvenido, usuario creado.", dialog, user_id);

                    } else {
                        //popUpErrorPass();
                        popUpHelper.popUpGeneral("Lo sentimos :(", "Las contraseñas no coinciden", dialog);
                    }
                } else {
                    popUpHelper.popUpGeneral("Lo sentimos :(", "Ese nombre de usuario esta en uso", dialog);
                    //popUpErrorUser();
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



    /*public void popUpGeneral(String title, String message, final Boolean goToPrincipalWindow) {

        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText(title);
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText(message);
        customDialog.show();

        (customDialog.findViewById(R.id.btnAceptarPopUp)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog.dismiss();
                if (goToPrincipalWindow) {
                    Intent myIntent = new Intent(getApplicationContext(), act_Ventana_Principal.class);
                    myIntent.putExtra("user_id", user_id);
                    startActivity(myIntent);
                }
            }
        });


    }*/
}
