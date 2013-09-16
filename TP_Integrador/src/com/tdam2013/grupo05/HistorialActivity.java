package com.tdam2013.grupo05;

import com.tdam2013.grupo05.utiles.UtilesIntents;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class HistorialActivity extends ListActivity {

    public static final String[] item_historial = new String[] { "Juan", "Pepe", "Juan", "Pepe",
            "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan",
            "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_activity);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.historial_activity_item,
                R.id.historial_item_contacto, item_historial));

    }

    /**
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.historial, menu);
        return true;
    }

    /**
     * Menu
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Log.i("onMenuItemSelected()", "featureId: " + featureId + " - item: " + item);

        // if (item.getItemId() == R.id.action_hist_filtros) {
        // /*
        // * Toast
        // */
        // Toast.makeText(getBaseContext(), "action_hist_filtros",
        // Toast.LENGTH_SHORT).show();
        // return true;
        //
        // } else if (item.getItemId() == R.id.action_hist_orden) {
        // /*
        // * Toast
        // */
        // Toast.makeText(getBaseContext(), "action_hist_orden",
        // Toast.LENGTH_SHORT).show();
        // return true;

        if (item.getItemId() == R.id.action_settings) {
            Log.i("onMenuItemSelected()", "action_settings");
            this.startActivity(UtilesIntents.getHistorialSettingsActivity(this));
            return true;
        }

        Log.i("onMenuItemSelected()", "Item no manejado");
        return super.onMenuItemSelected(featureId, item);

    }

}
