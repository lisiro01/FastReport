package db;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.R;
/**
 * Created by Lis on 27/4/16.
 */
public class FastReportDataBase extends Activity{

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.);
            //solve this error importing: import your.application.packagename.R;


            //Abrimos la base de datos 'UsersDB' en modo escritura
            SQLiteHelper usdbh =
                    new SQLiteHelper(this, "UsersDB", null, 1);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                int codigo = 12;
                string nombre = "Jorge Valdes";
                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                            "VALUES (" + codigo + ", '" + nombre +"')");

            }

                //Cerramos la base de datos
                db.close();
            }
        }
}
