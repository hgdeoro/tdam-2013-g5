package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class EnviarMensajeWeb extends Activity {

    public static final int DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_mensaje_web_activity);

        ((Button) this.findViewById(R.id.enviar_mensaje_web_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EnviarMensajeWeb.this.showDialog(DIALOG);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enviar_mensaje_web, menu);
        return true;
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG) {
            Dialog dialog = new AlertDialog.Builder(this).setTitle("ERROR")
                    .setMessage("No se ha podido enviar el mensaje web.").create();
            return dialog;
        } else {
            return super.onCreateDialog(id);
        }
    }

}
