package Extras;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lisis.charles.fastreport.R;


public class PopUpHelper {

    public Dialog popUpGeneral(String title, String message, Context context) {//Cuando se quiere hacer algo al cerrar el popUp

        final Dialog customDialog = new Dialog(context);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.pop_up_notificar);

        ((TextView) customDialog.findViewById(R.id.titulo)).setText(title);
        ((TextView) customDialog.findViewById(R.id.textoPopUp)).setText(message);
        customDialog.show();

        return customDialog;
    }

    public void popUpNoAnswer(String title, String message, Context context) {//cuando al cerrar el popUp no pasa nada

        final Dialog customDialog = new Dialog(context);
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
