package ar.com.hgdeoro.tdam.holamundo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends MyBaseActivity {

    private static final int PICK_CONTACT = 1;

    private void addStatus(String status) {
        EditText text = (EditText) findViewById(R.id.status);
        text.setText(text.getText() + status + "\n");
    }

    private void cleanStatus() {
        ((EditText) findViewById(R.id.status)).setText("");
    }

    private void setHelp() {
        addStatus("1. Abrir pagina web");
        addStatus("2. Seleccionar contacto");
        addStatus("3. Insertar contacto");
        addStatus("4. ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btHelp)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cleanStatus();
                setHelp();
            }

        });

        ((Button) findViewById(R.id.btClean)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cleanStatus();
            }

        });

        /*
         * Intent 1
         */
        ((Button) findViewById(R.id.btIntent1)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                addStatus("Abriendo 'http://lwn.net'");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://lwn.net"));
                startActivity(intent);
            }

        });

        /*
         * Intent 2
         */
        ((Button) findViewById(R.id.btIntent2)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri
                        .parse("content://contacts/people"));
                startActivityForResult(intent, PICK_CONTACT);
            }

        });

        /*
         * Intent 3
         */
        ((Button) findViewById(R.id.btIntent3)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Data.RAW_CONTACT_ID, Long.valueOf(-1));
                values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                values.put(Phone.NUMBER, "54 9 351 9991111");
                values.put(Phone.TYPE, Phone.TYPE_CUSTOM);
                values.put(Phone.LABEL, "Some Contact");
                Uri dataUri = getContentResolver()
                        .insert(ContactsContract.Data.CONTENT_URI, values);
                addStatus("dataUri: " + dataUri);
            }

        });

        /*
         * Intent 4
         */
        ((Button) findViewById(R.id.btIntent4)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case (PICK_CONTACT):
            addStatus("onActivityResult(PICK_CONTACT)");
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                addStatus("onActivityResult(PICK_CONTACT) -> contactData: " + contactData);

            } else {
                addStatus("onActivityResult(PICK_CONTACT) -> resultCode: " + resultCode);

            }
        default:
            addStatus("onActivityResult() -> requestCode desconocido: " + requestCode);
            break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
