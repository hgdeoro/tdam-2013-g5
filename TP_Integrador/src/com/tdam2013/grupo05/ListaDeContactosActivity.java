package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaDeContactosActivity extends ListActivity {

    // TODO: cargar contactos desde ContentManager de contactos

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this,
                "com.tdam2013.grupo05.AccionesSobreContactoActivity"));
        this.startActivity(intent);
    }

}
