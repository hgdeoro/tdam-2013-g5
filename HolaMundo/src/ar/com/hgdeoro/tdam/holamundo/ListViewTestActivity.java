package ar.com.hgdeoro.tdam.holamundo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewTestActivity extends ListActivity {

    public static final String[] provincias = new String[] { "Córdoba", "Buenos Aires", "Santa Fe",
            "La Pampa", "San Juan", "San Luis", "Mendoza", "La Rioja" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_activity_item,
                R.id.labelListActivityItem, provincias));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

}
