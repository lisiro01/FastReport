package db;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lisis.charles.fastreport.R;
import com.lisis.charles.fastreport.act_Ventana_Principal;

/**
 * Created by Lis on 25/5/16.
 */
public class PopUpHelper extends AppCompatActivity {

    public void popUpGeneral(String title, String message, final Dialog customDialog, final long user_id) {

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
                Intent myIntent = new Intent(getApplicationContext(), act_Ventana_Principal.class);
                myIntent.putExtra("user_id", user_id);
                startActivity(myIntent);
            }
        });


    }

    public void popUpGeneral(String title, String message, final Dialog customDialog) {

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

            }
        });


    }
}
