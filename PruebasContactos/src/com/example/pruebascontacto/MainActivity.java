package com.example.pruebascontacto;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final void dumpAggregatedContacts(Activity activity) {
        final String TAG = "dumpAggregatedContacts()";
        Log.i(TAG, "Inicio");

        // Get *ALL* the contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        // Sorted
        String sort = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor c = activity.managedQuery(uri, null, null, null, sort);

        if (!c.moveToFirst()) {
            Log.i(TAG, "no hay datos...");
            return;
        }

        while (c.moveToNext()) {
            Log.i(TAG, c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        }

        Log.i(TAG, "FIN!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        dumpAggregatedContacts(this);
    }
}
