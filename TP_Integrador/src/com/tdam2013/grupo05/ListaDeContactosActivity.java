package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        /*
         * Context Menu
         */
        registerForContextMenu(this.getListView());
    }

    /**
     * List Item
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        this.startActivity(Utiles.getAccionesSobreContactoActivityIntent(this));
    }

    /**
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_de_contactos, menu);
        return true;
    }

    /**
     * Menu
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.i("onMenuItemSelected()", "item: " + item);

        if (item.getItemId() == R.id.action_ldc_filtros) {
            Log.i("onMenuItemSelected()", "action_ldc_filtros");
            Toast.makeText(getBaseContext(), "action_ldc_filtros", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.action_ldc_orden) {
            Log.i("onMenuItemSelected()", "action_ldc_orden");
            Toast.makeText(getBaseContext(), "action_ldc_orden", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.action_ldc_renombrar_usuario_web) {
            Log.i("onMenuItemSelected()", "action_ldc_renombrar_usuario_web");
            this.startActivity(Utiles.getRegistrarUsuarioActivityIntent(this));
            return true;

        } else if (item.getItemId() == R.id.action_ldc_ver_historial) {
            Log.i("onMenuItemSelected()", "action_ldc_ver_historial");
            this.startActivity(Utiles.getHistorialActivityIntent(this));
            return true;

        }

        Log.i("onMenuItemSelected()", "Item no manejado "
                + "(puede deberse a que se utilizo el menu contextual)");
        return super.onMenuItemSelected(featureId, item);

    }

    /**
     * Context Menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.lista_de_contactos_contextual, menu);
    }

    /**
     * Context Menu
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.i("onContextItemSelected()", "item: " + item);

        if (item.getItemId() == R.id.action_ldcc_ver_historial) {
            Log.i("onContextItemSelected()", "action_ldcc_ver_historial");
            this.startActivity(Utiles.getHistorialActivityIntent(this));
            return true;
        }

        Log.i("onContextItemSelected()", "Item no manejado");
        return super.onContextItemSelected(item);

    }

}
