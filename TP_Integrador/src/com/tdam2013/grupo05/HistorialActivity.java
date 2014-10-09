package com.tdam2013.grupo05;

import java.text.ParseException;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.commonsware.cwac.loaderex.AbstractCursorLoader;
import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.db.Database.TABLE_WEB_MESSAGES;
import com.tdam2013.grupo05.utiles.UtilesFecha;
import com.tdam2013.grupo05.utiles.UtilesIntents;

/**
 * Muestra el historial de mensajes web.
 * 
 * Puede ser utilizada a nivel general, donde muestra mensajes de todos los
 * contactos, o puede ser usada para mostrar los datos de un contacto en
 * particular.
 * 
 * @author Horacio G. de Oro
 *
 */
public class HistorialActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	public static final String INTENT_EXTRA__CONTACT_USERNAME = "CONTACT_USERNAME";
	public static final String INTENT_EXTRA__CONTACT_ID = "CONTACT_ID";

	public static final String PREF_ORDEN__ALFABETICO = "ALFA";
	public static final String PREF_ORDEN__CRONOLOGICO = "CRONO";

	public static final String PREF_FILTRO__ALL = "ALL";
	public static final String PREF_FILTRO__DAY = "DAY";
	public static final String PREF_FILTRO__WEEK = "WEEK";

	private SimpleCursorAdapter mCursorAdapter;

	// Si contactUsername es == null, mostramos historial de todos los contacto.
	private String contactUsername = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_activity);

		contactUsername = getIntent().getExtras().getString(
				INTENT_EXTRA__CONTACT_USERNAME);

		if (contactUsername == null) {
			Long contactId = getIntent().getExtras().getLong(
					INTENT_EXTRA__CONTACT_ID);
			if (contactId != null)
				contactUsername = Database.getDatabase(this)
						.getUsernameDeContacto(contactId);
		}

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

		mCursorAdapter.setViewBinder(new ViewBinderItemHistorialActivity(this
				.getApplicationContext()));

		setListAdapter(mCursorAdapter);

	}

	static public class ViewBinderItemHistorialActivity implements
			SimpleCursorAdapter.ViewBinder {

		private final Context ctx;

		public ViewBinderItemHistorialActivity(Context ctx) {
			this.ctx = ctx;
		}

		private String getNombreOwnerTelefono() {
			return "Usted";
		}

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			final int COLUMNA_DIRECCION = 4;

			if (view.getId() == R.id.historial_item_contacto) {
				int direccionMensaje = cursor.getInt(COLUMNA_DIRECCION);
				// Si mensaje fue enviado, mostramos owner del celular
				if (direccionMensaje == TABLE_WEB_MESSAGES.DIRECTION_MESSAGE_SENT) {
					((TextView) view).setText(getNombreOwnerTelefono());
					return true;
				}
				return false;
			}

			if (view.getId() == R.id.historial_item_fecha_hora) {
				String fechaFromDb = cursor.getString(columnIndex);
				try {

					((TextView) view).setText(UtilesFecha
							.formatearFechaHoraConMediumDateFormat(fechaFromDb,
									this.ctx));
					return true;

				} catch (ParseException e) {
					e.printStackTrace();
					return false;
				}

			}

			return false;
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
		this.startActivity(MostrarDetalleMensajeWebActivity
				.getMostrarDetalleMensajeWebActivity(this, id, contactUsername));
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

				Database db = Database.getDatabase(HistorialActivity.this);
				return db.searchSentWebMessages(getPreferenceFiltro(),
						HistorialActivity.this.getPreferenceOrden(),
						contactUsername);

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

	private Database.TABLE_WEB_MESSAGES.OrderBy getPreferenceOrden() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String value = sp.getString("pref_hist_orden", PREF_ORDEN__CRONOLOGICO);

		Log.d("getPreferenceOrden()", "pref_hist_orden: '" + value + "'");

		if (PREF_ORDEN__ALFABETICO.equals(value)) {
			return Database.TABLE_WEB_MESSAGES.OrderBy.ALFABETICO;
		} else {
			return Database.TABLE_WEB_MESSAGES.OrderBy.CRONOLOGICO;
		}
	}

	private Database.TABLE_WEB_MESSAGES.Filter getPreferenceFiltro() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String value = sp.getString("pref_hist_filtro", PREF_FILTRO__ALL);

		Log.d("getPreferenceFiltro()", "pref_hist_filtro: '" + value + "'");

		if (PREF_FILTRO__WEEK.equals(value)) {
			return Database.TABLE_WEB_MESSAGES.Filter.WEEK;
		} else if (PREF_FILTRO__DAY.equals(value)) {
			return Database.TABLE_WEB_MESSAGES.Filter.DAY;
		} else {
			return Database.TABLE_WEB_MESSAGES.Filter.ALL;
		}
	}

	/**
	 * Devuelve Intent para lanzar activity. contactName/contactId puede ser
	 * null (para mostrar historial de todos los contactos).
	 * 
	 * Si no se especifica contactName, se puede utilizar contactId.
	 */
	public static Intent getHistorialDeContactoActivityIntent(Context ctx,
			String contactName, Long contactId) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx, HistorialActivity.class
				.getCanonicalName()));
		intent.putExtra(HistorialActivity.INTENT_EXTRA__CONTACT_USERNAME,
				contactName);
		intent.putExtra(HistorialActivity.INTENT_EXTRA__CONTACT_ID, contactId);
		return intent;
	}

}
