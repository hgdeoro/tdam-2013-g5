package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.commonsware.cwac.loaderex.AbstractCursorLoader;
import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesIntents;

public class HistorialActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	public static final String INTENT_EXTRA__CONTACT_NAME = "CONTACT_NAME";

	public static final String PREF_ORDEN__ALFABETICO = "ALFA";

	public static final String PREF_ORDEN__CRONOLOGICO = "CRONO";

	private SimpleCursorAdapter mCursorAdapter;

	private String contactName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_activity);

		contactName = getIntent().getExtras() == null ? null : getIntent()
				.getExtras().getString(INTENT_EXTRA__CONTACT_NAME);

		/*
		 * Cursor
		 */

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
		this.startActivity(UtilesIntents.getMostrarDetalleMensajeWebActivity(
				this, id));
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

				if (contactName != null) {
					// Filtramos x contacto
					return new Database(
							HistorialActivity.this.getApplicationContext())
							.getSentWebMessages(contactName);

				} else {
					if (PREF_ORDEN__ALFABETICO.equals(HistorialActivity.this
							.getPreferenceOrden())) {
						return new Database(
								HistorialActivity.this.getApplicationContext())
								.getSentWebMessagesOrderedByUsername();
					} else {
						return new Database(
								HistorialActivity.this.getApplicationContext())
								.getSentWebMessages();
					}
				}

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

	/*
	 * Preferences
	 */

	private String getPreferenceOrden() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String value = sp.getString("pref_hist_orden", PREF_ORDEN__CRONOLOGICO);

		Log.d("getPreferenceOrden()", "pref_hist_orden: '" + value + "'");

		if (PREF_ORDEN__ALFABETICO.equals(value)) {
			return PREF_ORDEN__ALFABETICO;
		} else {
			return PREF_ORDEN__CRONOLOGICO;
		}
	}

}
