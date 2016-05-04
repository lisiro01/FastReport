package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Lis on 27/4/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate;

    {
        sqlCreate = "CREATE TABLE Usuario (\n" +
                "usuario TEXT NOT NULL UNIQUE,\n" +
                "nombre TEXT NOT NULL,\n" +
                "apellidos TEXT NOT NULL,\n" +
                "password TEXT NOT NULL,\n" +
                "telefono TEXT NOT NULL,\n" +
                "licenciaCond TEXT NOT NULL,\n" +
                "fechaVenc DATE NOT NULL,\n" +
                "direccion TEXT NOT NULL,\n" +
                "PRIMARY KEY(usuario)\n" +
                "); " +
                "CREATE TABLE Vehiculo (\n" +
                "tipo TEXT,\n" +
                "matricula TEXT NOT NULL,\n" +
                "marca TEXT,\n" +
                "modelo TEXT,\n" +
                "NombreAseg TEXT NOT NULL,\n" +
                "numPoliza TEXT NOT NULL,\n" +
                "PRIMARY KEY(matricula)\n" +
                "); " +
                "CREATE TABLE Accidente" +
                "(usuario TEXT " +
                "matricula TEXT ,\n" +
                "fechaAcc DATE ,\n" +
                "hora TEXT ,\n" +
                "localizacion TEXT ,\n" +
                "PRIMARY KEY(usuario,matricula, NombreAseg)\n" +
                "FOREIGN KEY(usuario) REFERENCES Usuario," +
                " FOREIGN KEY (matricula) REFERENCES Vehiculo," +
                "FOREIGN KEY (NombreAseg)  REFERENCES Aseguradora ;";

    }


    public SQLiteHelper(Context contexto, String nombre,
                        CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //TODO: Arreglar esto para mantener los datos.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

    public void insertarVehiculo (SQLiteDatabase db, String type, String matr, String marc,
                                  String model, String nombAseg, String numPol){

        ContentValues reg = new ContentValues();
        reg.put("tipo", type);
        reg.put("matricula", matr);
        reg.put("marca", marc);
        reg.put("modelo", model );
        reg.put("NombreAseg", nombAseg );
        reg.put("numPoliza", numPol);

        db.insert("Vehiculo",null, reg);

    }


    public void insertarUsuario (SQLiteDatabase db, String user, String name, String lastN,
                                 String pass, String phone, String lic, Date expDate, String adress){

        ContentValues reg = new ContentValues();
        reg.put("usuario", user);
        reg.put("nombre", name);
        reg.put("apellidos", lastN);
        reg.put("password", pass );
        reg.put("telefono", phone );
        reg.put("licenciaCond", lic);
        reg.put("fechaVenc", expDate.toString());
        reg.put("direccion", adress);

        db.insert("Usuario",null, reg);
    }




    public void insertarAccidente(SQLiteDatabase db, String user, String matr, String date,
                                  String time, String local){

        ContentValues reg = new ContentValues();
        reg.put("usuario", user);
        reg.put("matricula", matr);
        reg.put("fechaAcc", date);
        reg.put("hora", time );
        reg.put("localizacion", local );

        db.insert("Accidente",null, reg);
    }


}
