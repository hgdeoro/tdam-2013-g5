package com.tdam2013.grupo05;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EnviarMensajeWeb extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_mensaje_web_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enviar_mensaje_web, menu);
        return true;
    }

}
