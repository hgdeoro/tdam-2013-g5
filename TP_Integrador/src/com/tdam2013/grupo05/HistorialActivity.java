package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesIntents;

public class HistorialActivity extends ListActivity {

	// public static final String[] item_historial = new String[] { "Juan",
	// "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan",
	// "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan", "Pepe", "Juan",
	// "Pepe", "Juan", "Pepe", "Juan", "Pepe", };

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_activity);

		// setListAdapter(new ArrayAdapter<String>(this,
		// R.layout.historial_activity_item, R.id.historial_item_contacto,
		// item_historial));

		Cursor cursor = new Database(this.getApplicationContext())
				.getSentWebMessages();
		startManagingCursor(cursor);

		ListAdapter adapter = new SimpleCursorAdapter(
		// Context
				this,

				// row template
				R.layout.historial_activity_item,

				// Pass in the cursor to bind to.
				cursor,

				// Array of cursor columns to bind to.
				new String[] { Database.TABLE_WEB_MESSAGES.F_USERNAME,
						Database.TABLE_WEB_MESSAGES.F_TIME },

				// Parallel array of which template objects to bind to those
				// columns.
				new int[] { R.id.historial_item_contacto,
						R.id.historial_item_fecha_hora });

		setListAdapter(adapter);

	}

	/**
	 * List Item
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		this.startActivity(UtilesIntents
				.getMostrarDetalleMensajeWebActivity(this));
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

		Log.i("onMenuItemSelected()", " - item: " + item);

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
