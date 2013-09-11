package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import ar.com.hgdeoro.tdam.ejercicio01.db.DBHelper;

public class ListTextsActivity extends ListActivity {

    public static final String[] provincias = new String[] { "Córdoba", "Buenos Aires", "Santa Fe",
            "La Pampa", "San Juan", "San Luis", "Mendoza", "La Rioja" };

    private boolean usarDb = true;

    @SuppressWarnings({ "deprecation" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_texts_activity);

        if (usarDb) {
            Cursor cursor = new DBHelper(this).obtenerTextos();
            startManagingCursor(cursor);

            ListAdapter adapter = new SimpleCursorAdapter(
            // Context
                    this,

                    // row template
                    R.layout.list_texts_activity_list_item,

                    // Pass in the cursor to bind to.
                    cursor,

                    // Array of cursor columns to bind to.
                    new String[] { DBHelper.TEXTOS_COL_TEXTO },

                    // Parallel array of which template objects to bind to those
                    // columns.
                    new int[] { R.id.label_list_text_activity_item });

            setListAdapter(adapter);

        } else {
            setListAdapter(new ArrayAdapter<String>(this, R.layout.list_texts_activity_list_item,
                    R.id.label_list_text_activity_item, provincias));
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Toast.makeText(this, "Item " + position, Toast.LENGTH_SHORT).show();

        if (usarDb) {

            Intent intent = this.getIntent();

            /* SEGURO?!?!?!?!?!??!?! (la linea siguiente) */
            intent.putExtra(MainActivity.TEXT_ID, this.getSelectedItemId());

            /* SEGURO?!?!?!?!?!??!?! (la linea siguiente) */
            intent.putExtra(MainActivity.TEXT_STRING, (String) null);

            this.setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = this.getIntent();
            intent.putExtra(MainActivity.TEXT_ID, Long.valueOf((long) position));
            intent.putExtra(MainActivity.TEXT_STRING, provincias[position]);
            this.setResult(RESULT_OK, intent);
            finish();
        }
    }

}
