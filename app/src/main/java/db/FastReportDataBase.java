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
                //setContentView(R.layout.);
                //solve this error importing: import your.application.packagename.R;


                //Abrimos la base de datos 'UsersDB' en modo escritura
                SQLiteHelper usdbh =
                        new SQLiteHelper(this, "UsersDB", null, 1);

                SQLiteDatabase db = usdbh.getWritableDatabase();

                //Si hemos abierto correctamente la base de datos
                if(db == null)
                {
                    int codigo = 12;
                    String nombre = "Jorge Valdes";
                    //Insertamos los datos en la tabla Usuarios
                    db.execSQL("CREATE TABLE `Usuario` (\n" +
                            "\t`usuario`\tTEXT NOT NULL UNIQUE,\n" +
                            "\t`nombre`\tTEXT NOT NULL,\n" +
                            "\t`apellidos `\tTEXT NOT NULL,\n" +
                            "\t`password`\tTEXT NOT NULL,\n" +
                            "\t`telefono`\tTEXT NOT NULL,\n" +
                            "\t`licenciaCond`\tTEXT NOT NULL,\n" +
                            "\t`fechaVenc`\tDATE NOT NULL,\n" +
                            "\t`direccion`\tTEXT NOT NULL,\n" +
                            "\tPRIMARY KEY(usuario)\n" +
                            "); " +
                            "CREATE TABLE `Vehiculo` (\n" +
                            "\t`tipo`\tTEXT,\n" +
                            "\t`matricula`\tTEXT NOT NULL,\n" +
                            "\t`marca`\tTEXT,\n" +
                            "\t`modelo`\tTEXT,\n" +
                            "\tPRIMARY KEY(matricula)\n" +
                            ");");

                }

                    //Cerramos la base de datos
                    db.close();
                }
            }

