package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lis on 27/4/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate;

    {
        sqlCreate = "CREATE TABLE Usuario (\n" +
                "usuario TEXT NOT NULL UNIQUE,\n" +
                "nombre TEXT ,\n" +
                "apellidos TEXT \n" +
                "password TEXT ,\n" +
                "telefono TEXT ,\n" +
                "licenciaCond TEXT ,\n" +
                "fechaVenc DATE ,\n" +
                "direccion TEXT ,\n" +
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


    public SQLiteHelper(Context contexto, String nombre, CursorFactory factory, int version) {
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


    public void actualizaDatosUsuario (SQLiteDatabase db,String user, String name, String lastN,
                                  String phone, String lic, String expDate, String adress){

        ContentValues reg = new ContentValues();
        reg.put("nombre", name);
        reg.put("apellidos", lastN);
        reg.put("telefono", phone );
        reg.put("licenciaCond", lic);
        reg.put("fechaVenc", expDate);
        reg.put("direccion", adress);
        db.update("Usuarios", reg, user = "usuario", null);

    }

    public void creaUsuario (SQLiteDatabase db, String user, String pass){
        ContentValues reg = new ContentValues();
        reg.put("usuario", user);
        reg.put("password", pass );
        reg.put("nombre", (String)null);
        reg.put("apellidos", (String)null);
        reg.put("telefono", (String)null );
        reg.put("licenciaCond", (String)null);
        reg.put("fechaVenc", (String)null);
        reg.put("direccion", (String)null);

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
