package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.historial, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Log.i("onMenuItemSelected()", "featureId: " + featureId + " - item: " + item);

        if (item.getItemId() == R.id.action_hist_filtros) {
            /*
             * Toast
             */
            Toast.makeText(getBaseContext(), "action_hist_filtros", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.action_hist_orden) {
            /*
             * Toast
             */
            Toast.makeText(getBaseContext(), "action_hist_orden", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            Toast.makeText(getBaseContext(), "DESCONOCIDO", Toast.LENGTH_SHORT).show();
            return super.onMenuItemSelected(featureId, item);
        }

    }

}
