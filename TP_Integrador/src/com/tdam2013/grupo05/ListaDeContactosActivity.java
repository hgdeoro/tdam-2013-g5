package com.tdam2013.grupo05;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tdam2013.grupo05.utiles.UtilesIntents;

public class ListaDeContactosActivity extends ListActivity implements LoaderCallbacks<Cursor> {

    // https://developer.android.com/training/contacts-provider/retrieve-names.html
    private final static String[] FROM_COLUMNS = { Contacts.DISPLAY_NAME_PRIMARY };

    private final static int[] TO_IDS = { R.id.lista_de_contactos_item_label };

    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;

    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;

    // The contact's LOOKUP_KEY
    String mContactKey;

    // A content URI for the selected contact
    Uri mContactUri;

    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;

    private static final String[] PROJECTION = { Contacts._ID, Contacts.LOOKUP_KEY,
            Contacts.DISPLAY_NAME_PRIMARY };

    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;

    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;

    // Defines the text expression
    private static final String SELECTION = Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";

    // Defines a variable for the search string
    private String mSearchString = "";

    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_contactos_activity);

        /*
         * Cursor
         */

        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(this, R.layout.lista_de_contactos_activity_item,
                null, FROM_COLUMNS, TO_IDS, 0);

        // Sets the adapter for the ListView
        // mContactsList.setAdapter(mCursorAdapter);
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

        getLoaderManager().initLoader(0, null, this);

    }

    /**
     * List Item
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Get the Cursor
        // Cursor cursor = getAdapter().getCursor();
        Cursor cursor = ((SimpleCursorAdapter) getListAdapter()).getCursor();

        // Move to the selected contact
        cursor.moveToPosition(position);

        // Get the _ID value
        // mContactId = getLong(CONTACT_ID_INDEX);
        mContactId = cursor.getLong(CONTACT_ID_INDEX);

        // Get the selected LOOKUP KEY
        // mContactKey = getString(CONTACT_KEY_INDEX);
        mContactKey = cursor.getString(LOOKUP_KEY_INDEX);

        // Create the contact's content Uri
        mContactUri = Contacts.getLookupUri(mContactId, mContactKey);

        /*
         * You can use mContactUri as the content URI for retrieving the details
         * for a contact.
         */

        this.startActivity(UtilesIntents.getAccionesSobreContactoActivityIntent(this));

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

        // if (item.getItemId() == R.id.action_ldc_filtros) {
        // Log.i("onMenuItemSelected()", "action_ldc_filtros");
        // Toast.makeText(getBaseContext(), "action_ldc_filtros",
        // Toast.LENGTH_SHORT).show();
        // return true;
        //
        // } else if (item.getItemId() == R.id.action_ldc_orden) {
        // Log.i("onMenuItemSelected()", "action_ldc_orden");
        // Toast.makeText(getBaseContext(), "action_ldc_orden",
        // Toast.LENGTH_SHORT).show();
        // return true;
        //
        // } else if (item.getItemId() == R.id.action_ldc_renombrar_usuario_web)
        // {
        // Log.i("onMenuItemSelected()", "action_ldc_renombrar_usuario_web");
        // this.startActivity(Utiles.getRegistrarUsuarioActivityIntent(this));
        // return true;
        //
        // } else
        //

        if (item.getItemId() == R.id.action_ldc_ver_historial) {
            Log.i("onMenuItemSelected()", "action_ldc_ver_historial");
            this.startActivity(UtilesIntents.getHistorialActivityIntent(this));
            return true;

        } else if (item.getItemId() == R.id.action_ldc_settings) {
            Log.i("onMenuItemSelected()", "action_ldc_settings");
            this.startActivity(UtilesIntents.getListaDeContactosSettingsActivity(this));
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
            this.startActivity(UtilesIntents.getHistorialActivityIntent(this));
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        mSelectionArgs[0] = "%" + mSearchString + "%";

        // Starts the query
        return new CursorLoader(this, Contacts.CONTENT_URI, PROJECTION, SELECTION, mSelectionArgs,
                null);

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
