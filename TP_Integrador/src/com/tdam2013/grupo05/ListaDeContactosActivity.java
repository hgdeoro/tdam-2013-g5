package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ListaDeContactosActivity extends ListActivity {

    public static final String[] contactos = new String[] { "Juan", "Pepe", "Juan", "Pepe", "Juan",
            "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe",
            "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_contactos_activity);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_de_contactos_activity_item,
                R.id.lista_de_contactos_item_label, contactos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_de_contactos, menu);
        return true;
    }

}
