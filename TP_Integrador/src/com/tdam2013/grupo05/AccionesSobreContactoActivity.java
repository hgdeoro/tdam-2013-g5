package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class AccionesSobreContactoActivity extends ListActivity {

    public static final String[] acciones = new String[] { "Llamar", "Llamar", "Enviar sms",
            "Enviar email", "Enviar mensaje web", };

    // TODO: crear custom adaptar para cargar ambos textos: accion y dato
    // TODO: implementar onListItemClick() para respoder a clics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acciones_sobre_contacto_activity);

        setListAdapter(new ArrayAdapter<String>(this,
                R.layout.acciones_sobre_contacto_activity_item,
                R.id.acciones_sobre_contacto_item_accion, acciones));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acciones_sobre_contacto, menu);
        return true;
    }

}
