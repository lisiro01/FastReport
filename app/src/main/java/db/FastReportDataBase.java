package db;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
/**
 * Created by Lis on 27/4/16.
 */
public class FastReportDataBase extends Activity{

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);

            //Abrimos la base de datos 'UsersDB' en modo escritura
            SQLiteHelper usdbh =
                    new SQLiteHelper(this, "UsersDB", null, 1);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                //Insertamos 5 usuarios de ejemplo
                for(int i=1; i<=5; i++)
                {
                    //Generamos los datos
                    int codigo = i;
                    String nombre = "Usuario" + i;

                    //Insertamos los datos en la tabla Usuarios
                    db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                            "VALUES (" + codigo + ", '" + nombre +"')");
                }

                //Cerramos la base de datos
                db.close();
            }
        }
}
