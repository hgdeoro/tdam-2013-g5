package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.commonsware.cwac.loaderex.AbstractCursorLoader;
import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesIntents;

public class HistorialActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mCursorAdapter;

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_activity);

		/*
		 * Cursor
		 */

		if (true) {

			// Gets a CursorAdapter
			mCursorAdapter = new SimpleCursorAdapter(this,
					R.layout.historial_activity_item, null, new String[] {
							Database.TABLE_WEB_MESSAGES.F_USERNAME,
							Database.TABLE_WEB_MESSAGES.F_TIME,
							Database.TABLE_WEB_MESSAGES.F_TEXT }, new int[] {
							R.id.historial_item_contacto,
							R.id.historial_item_fecha_hora,
							R.id.historial_item_dato_mensaje }, 0);

			// Sets the adapter for the ListView
			setListAdapter(mCursorAdapter);

		} else {
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
							Database.TABLE_WEB_MESSAGES.F_TIME,
							Database.TABLE_WEB_MESSAGES.F_TEXT },

					// Parallel array of which template objects to bind to those
					// columns.
					new int[] { R.id.historial_item_contacto,
							R.id.historial_item_fecha_hora,
							R.id.historial_item_dato_mensaje });

			setListAdapter(adapter);

		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		getLoaderManager().restartLoader(0, null, this);
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

	/*
	 * --------------------------------------------------
	 * LoaderCallbacks<Cursor>
	 * --------------------------------------------------
	 */

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return new AbstractCursorLoader(this.getApplicationContext()) {

			@Override
			protected Cursor buildCursor() {
				return new Database(
						HistorialActivity.this.getApplicationContext())
						.getSentWebMessages();
			}

		};

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mCursorAdapter.swapCursor(null);
	}
}
