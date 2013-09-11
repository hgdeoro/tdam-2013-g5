package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListTextsActivity extends ListActivity {

    public static final String[] provincias = new String[] { "Córdoba", "Buenos Aires", "Santa Fe",
            "La Pampa", "San Juan", "San Luis", "Mendoza", "La Rioja" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_texts_activity);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_texts_activity_list_item,
                R.id.label_list_text_activity_item, provincias));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "Item " + position, Toast.LENGTH_SHORT).show();
    }

}
