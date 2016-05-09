package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        sqlCreate = "CREATE TABLE Usuarios (usuario varchar(40) NOT NULL PRIMARY KEY,\n" +
                "                nombre varchar(40) ,\n" +
                "                apellidos varchar(40),\n" +
                "                contrasenya varchar(40) ,\n" +
                "                telefono varchar(9) ,\n" +
                "                licenciaCond varchar(40) ,\n" +
                "                fechaVenc varchar(40) ,\n" + // NO OLVIDAR CONVERTIRLO EN DATE
                "                direccion varchar(40));\n" +
                "                \n" +
                "                CREATE TABLE Vehiculo (\n" +
                "                tipo varchar(40),\n" +
                "                matricula varchar(40) NOT NULL PRIMARY KEY,\n" +
                "                marca varchar(40),\n" +
                "                modelo varchar(40),\n" +
                "                NombreAseg varchar(40) NOT NULL,\n" +
                "                numPoliza varchar(40) NOT NULL\n" +
                "                );\n" +
                "                CREATE TABLE Accidente \n" +
                "                (usuario varchar(40) ,\n" +
                "                matricula varchar(40) ,\n" +
                "                fechaAcc DATE ,\n" +
                "                hora varchar(40) ,\n" +
                "                localizacion varchar(40) ,\n" +
                "                PRIMARY KEY(usuario,matricula),\n" +
                "                FOREIGN KEY(usuario) REFERENCES Usuario,\n" +
                "                FOREIGN KEY (matricula) REFERENCES Vehiculo);";

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

        db.insert("Vehiculo", null, reg);

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
        db.update("Usuarios", reg, user + " = usuario", null);

    }

    public void creaUsuario (SQLiteDatabase db, String user, String pass){
        ContentValues reg = new ContentValues();
        reg.put("usuario", user);
        reg.put("contrasenya", pass );
        reg.put("nombre", (String)null);
        reg.put("apellidos", (String)null);
        reg.put("telefono", (String)null );
        reg.put("licenciaCond", (String)null);
        reg.put("fechaVenc", (String)null);
        reg.put("direccion", (String)null);

        db.insert("Usuarios", null, reg);

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

    public String cargarDatos(SQLiteDatabase db){

        String r = "hola";
        try {
            Cursor c = db.rawQuery("select record from Usuarios", null);

            if (c.moveToFirst()) {
                r = c.getString(0);
            }
        }
        catch (Exception e){

        }

        return r;
    }


}
