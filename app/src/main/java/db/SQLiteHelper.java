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

   private  String sqlCreate;
    {
        sqlCreate = "CREATE TABLE Usuarios " +
                "               (usuario TEXT NOT NULL PRIMARY KEY,\n" +
                "                nombre TEXT ,\n" +
                "                apellidos TEXT,\n" +
                "                contrasenya TEXT ,\n" +
                "                telefono TEXT ,\n" +
                "                licenciaCond TEXT ,\n" +
                "                fechaVenc TEXT,\n" + // NO OLVIDAR CONVERTIRLO EN DATE
                "                direccion TEXT);" ;

    }


    public SQLiteHelper(Context contexto, String nombre, CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        if(db.isReadOnly()) {
            db = getWritableDatabase();
            db.execSQL(sqlCreate);
        }
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


    public void actualizaDatosUsuario (String user, String name, String lastN,
                                       String phone, String lic, String expDate, String adress){

        String cond = "usuario=" + user;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues reg = new ContentValues();
        reg.put("nombre", name);
        reg.put("apellidos", lastN);
        reg.put("telefono", phone );
        reg.put("licenciaCond", lic);
        reg.put("fechaVenc", expDate);
        reg.put("direccion", adress);
        db.update("Usuarios", reg,cond,null);
        db.close();
    }

    public void creaUsuario ( String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();


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
        db.close();
       /* db.execSQL("INSERT INTO Usuarios (usuario,nombre,apellidos,contrasenya, telefono,licenciaCond, fechaVenc, direccion)" +
                "VALUES ('lisPrueba',null,null,'123',null,null,null,null )");*/

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

   /* public String cargarDatos(){
        SQLiteDatabase db = this.getReadableDatabase();
        String r = "Hola";

            Cursor c = db.rawQuery("select record from Usuarios", null);

            if (c.moveToFirst())
                r = c.getString(0);


        return r;
    }*/

    public void obtener (){




    }


}
