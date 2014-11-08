package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tdam2013.grupo05.preferences.ListaDeContactosSettingsActivity;
import com.tdam2013.grupo05.services.MensajeWebPollService;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;

public class ListaDeContactosActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	/**
     * 
     */
	public static final int ACTIVITY_REQUEST_CODE__REGISTER_USERNAME = 1;

	/**
	 * Return value for getPreferenceFiltroDeContactos()
	 */
	public static final String PREF_FILTRO_CONTACTO__TODOS = "TODOS";

	/**
	 * Return value for getPreferenceFiltroDeContactos()
	 */
	public static final String PREF_FILTRO_CONTACTO__CON_TELEFONO = "CON_TELEFONO";

	/*
	 * ----------------------------------------------------------------------
	 * Manejo de Cursor, etc.
	 * ----------------------------------------------------------------------
	 */

	// https://developer.android.com/training/contacts-provider/retrieve-names.html
	private final static String[] FROM_COLUMNS = {
			Contacts.DISPLAY_NAME_PRIMARY, Contacts.HAS_PHONE_NUMBER,
			Contacts.HAS_PHONE_NUMBER };

	private final static int[] TO_IDS = { R.id.lista_de_contactos_item_label,
			R.id.lista_de_contactos_item_icono_telefono,
			R.id.lista_de_contactos_item_icono_sms };

	// // Define global mutable variables
	// // Define a ListView object
	// ListView mContactsList;

	// // Define variables for the contact the user selects
	// // The contact's _ID value
	// long mContactId;

	// // The contact's LOOKUP_KEY
	// String mContactKey;

	// // A content URI for the selected contact
	// Uri mContactUri;

	// An adapter that binds the result Cursor to the ListView
	private SimpleCursorAdapter mCursorAdapter;

	/**
	 * Devuelve True si hay que ordenar los contactos en orden ascendente.
	 * 
	 * @return
	 */
	private boolean getPreferenceOrdenAscendente() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String value = sp.getString("pref_ldc_orden", "");
		Log.d("getPreferenceOrdenAscendente()", "pref_ldc_orden: '" + value
				+ "'");

		if ("Z-A".equals(value)) {
			return false;
		}
		return true;
	}

	/**
	 * Devuelve filtrado de contactos configurado en preferencias.
	 * 
	 * @return
	 */
	private String getPreferenceFiltroDeContactos() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String value = sp.getString("pref_ldc_filtro",
				PREF_FILTRO_CONTACTO__TODOS);
		Log.d("getPreferenceFiltroDeContactos()", "pref_ldc_filtro: '" + value
				+ "'");

		if (PREF_FILTRO_CONTACTO__CON_TELEFONO.equals(value)) {
			return PREF_FILTRO_CONTACTO__CON_TELEFONO;
		} else {
			return PREF_FILTRO_CONTACTO__TODOS;
		}
	}

	/*
	 * ----------------------------------------------------------------------
	 * Activity
	 * ----------------------------------------------------------------------
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_de_contactos_activity);

		/*
		 * Cursor
		 */

		// Gets a CursorAdapter
		mCursorAdapter = new SimpleCursorAdapter(this,
				R.layout.lista_de_contactos_activity_item, null, FROM_COLUMNS,
				TO_IDS, 0);
		mCursorAdapter.setViewBinder(new ListaDeContactosViewBinder());

		// Sets the adapter for the ListView
		setListAdapter(mCursorAdapter);

		/*
		 * Context Menu
		 */
		registerForContextMenu(this.getListView());

		/*
		 * En tutorial, recomiendan hacerlo desde:
		 * 
		 * onActivityCreated(Bundle savedInstanceState)
		 * 
		 * pero como no uso fragments, lo hacemos acá
		 */

		// getLoaderManager().initLoader(0, null, this);

	}

	/**
	 * Checks if the username exists in preferences. If doesn't exists, a new
	 * activity is launched to let the user enter his/her username.
	 */
	protected void checkUsername() {

		if (!UtilesMensajesWeb.usernameIsValid(UtilesMensajesWeb
				.getUsername(this))) {

			startActivityForResult(
					RegistrarUsuarioActivity
							.getRegistrarUsuarioActivityIntent(this),
					ACTIVITY_REQUEST_CODE__REGISTER_USERNAME);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ACTIVITY_REQUEST_CODE__REGISTER_USERNAME) {

			if (resultCode != RESULT_OK) {

				// Return de 'RegistrarUsuarioActivity' fallo. Puede ser:
				// 1. registracion inicial del usuario (1ra vez q' se ejecuta la
				// app)
				// 2. modificacion de usuarios

				if (UtilesMensajesWeb.getUsername(this) == null) {
					// No esta seteado el nombre de usuario, por lo tanto se
					// trata de la situacion '1' => SALIMOS
					finish();
				}
				// SI esta seteado el nombre de usuario, por lo tanto se
				// trata de la situacion '2' => NO SALIMOS

			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		/*
		 * este chequeo lo hacemos en onResume() para que funciona al cargar la
		 * activity, y tambien luego de ejecutar onActivityResult().
		 */

		checkUsername();

		// TODO: recargar SOLO si se ha cambiado el orden o filtro
		getLoaderManager().restartLoader(0, null, this);
		// getLoaderManager().initLoader(0, null, this);

		this.startService(MensajeWebPollService
				.getMensajeWebPollServiceForStartPolling(this));

	}

	/*
	 * ----------------------------------------------------------------------
	 * List Item
	 * ----------------------------------------------------------------------
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Get the Cursor
		Cursor cursor = ((SimpleCursorAdapter) getListAdapter()).getCursor();

		// Move to the selected contact
		cursor.moveToPosition(position);

		this.startActivity(AccionesSobreContactoFragment
				.getAccionesSobreContactoActivityIntent(this,
						cursor.getLong(COLUMN_INDEX_FOR_CONTACT_ID),
						cursor.getString(COLUMN_INDEX_FOR_DISPLAY_NAME_PRIMARY)));

		//
		// You now have the key pieces of an app that matches a search string to
		// contact names and returns the result in a ListView. The user can
		// click a contact name to select it. This triggers a listener, in which
		// you can work further with the contact's data. For example, you can
		// retrieve the contact's details. To learn how to do this, continue
		// with the next lesson, Retrieving Details for a Contact.
		//
		// * Retrieving Details for a Contact:
		// https://developer.android.com/training/contacts-provider/retrieve-names.html#retrieve-details.html
		//
		// To learn more about search user interfaces, read the API guide
		// Creating a Search Interface.
		//
		// The remaining sections in this lesson demonstrate other ways of
		// finding contacts in the Contacts Provider.
		//

	}

	/*
	 * ----------------------------------------------------------------------
	 * Menu & Context menu
	 * ----------------------------------------------------------------------
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

		if (item.getItemId() == R.id.action_ldc_ver_historial) {
			Log.i("onMenuItemSelected()", "action_ldc_ver_historial");
			this.startActivity(HistorialActivity
					.getHistorialDeContactoActivityIntent(this, null, null));
			return true;

		} else if (item.getItemId() == R.id.action_ldc_settings) {
			Log.i("onMenuItemSelected()", "action_ldc_settings");
			this.startActivity(ListaDeContactosSettingsActivity
					.getListaDeContactosSettingsActivity(this));
			return true;

		} else if (item.getItemId() == R.id.action_ldc_usuario_web) {
			Log.i("onMenuItemSelected()", "action_ldc_usuario_web");
			startActivityForResult(
					RegistrarUsuarioActivity
							.getRegistrarUsuarioActivityIntent(this),
					ACTIVITY_REQUEST_CODE__REGISTER_USERNAME);
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
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

			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();

			Cursor cursor = ((SimpleCursorAdapter) getListAdapter())
					.getCursor();

			cursor.moveToPosition(info.position);

			this.startActivity(HistorialActivity
					.getHistorialDeContactoActivityIntent(this, null,
							cursor.getLong(COLUMN_INDEX_FOR_CONTACT_ID)));

			return true;

		} else if (item.getItemId() == R.id.action_ldcc_editar_contacto) {
			Log.i("onContextItemSelected()", "action_ldcc_editar_contacto");

			// ////////////////////////////////////////////////////////

			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			// long id = getListAdapter().getItemId(info.position);

			// ////////////////////////////////////////////////////////

			// Get the Cursor
			Cursor cursor = ((SimpleCursorAdapter) getListAdapter())
					.getCursor();

			// Move to the selected contact
			// cursor.moveToPosition(this.getListView().getSelectedItemPosition());

			cursor.moveToPosition(info.position);

			// Get the _ID value
			long mContactId = cursor.getLong(COLUMN_INDEX_FOR_CONTACT_ID);

			// // Get the selected LOOKUP KEY
			// mContactKey = cursor.getString(COLUMN_INDEX_FOR_LOOKUP_KEY);

			Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
					mContactId);

			this.startActivity(new Intent(Intent.ACTION_EDIT, uri));

			return true;

		}

		Log.i("onContextItemSelected()", "Item no manejado");
		return super.onContextItemSelected(item);

	}

	/*
	 * --------------------------------------------------
	 * LoaderCallbacks<Cursor>
	 * --------------------------------------------------
	 */

	private static final String[] PROJECTION = { Contacts._ID,
			Contacts.LOOKUP_KEY, Contacts.DISPLAY_NAME_PRIMARY,
			Contacts.HAS_PHONE_NUMBER };

	// The column index for the _ID column
	private static final int COLUMN_INDEX_FOR_CONTACT_ID = 0;

	// The column index for the LOOKUP_KEY column
	@SuppressWarnings("unused")
	private static final int COLUMN_INDEX_FOR_LOOKUP_KEY = 1;

	// The column index for the LOOKUP_KEY column
	private static final int COLUMN_INDEX_FOR_DISPLAY_NAME_PRIMARY = 2;

	// The column index for the HAS_PHONE_NUMBER column
	@SuppressWarnings("unused")
	private static final int COLUMN_INDEX_FOR_HAS_PHONE_NUMBER = 3;

	// Defines the text expression
	// private static final String SELECTION = Contacts.DISPLAY_NAME_PRIMARY +
	// " LIKE ?";

	// Defines a variable for the search string
	// private String mSearchString = "";

	// Defines the array to hold values that replace the ?
	// private String[] mSelectionArgs = { mSearchString };

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		// mSelectionArgs[0] = "%" + mSearchString + "%";

		final Uri uri = Contacts.CONTENT_URI;

		final String orderBy = Contacts.DISPLAY_NAME_PRIMARY
				+ " COLLATE LOCALIZED "
				+ (getPreferenceOrdenAscendente() ? "ASC" : "DESC");

		final String filtro = getPreferenceFiltroDeContactos();
		final String selection;
		if (PREF_FILTRO_CONTACTO__CON_TELEFONO.equals(filtro)) {
			selection = "" + Contacts.HAS_PHONE_NUMBER + " == 1";
		} else {
			selection = "";
		}

		//
		// --> ContactsContract.Contacts.CONTENT_URI
		//
		return new CursorLoader(this, uri, PROJECTION, selection, null, orderBy);

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mCursorAdapter.swapCursor(null);
	}

	class ListaDeContactosViewBinder implements SimpleCursorAdapter.ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() == R.id.lista_de_contactos_item_icono_telefono
					|| view.getId() == R.id.lista_de_contactos_item_icono_sms) {
				int hasPhoneNumber = cursor.getInt(columnIndex);
				if (hasPhoneNumber == 0) {
					view.setVisibility(View.INVISIBLE);
				} else {
					view.setVisibility(View.VISIBLE);
				}
				return true;
			}
			return false;
		}

	}

}
