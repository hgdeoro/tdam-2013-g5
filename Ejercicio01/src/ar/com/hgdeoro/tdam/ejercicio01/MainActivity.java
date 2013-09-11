package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ar.com.hgdeoro.tdam.ejercicio01.db.DBHelper;

public class MainActivity extends Activity {

    /** Dialogs */
    public static final int DEFAULT_DIALOG = 0;

    /** Activity for response */
    public static final int AFR_SELECT_TEXT_TO_LOAD = 1;

    /** Keys para datos en intents */
    public static final String TEXT_ID = "TEXT_ID";
    public static final String TEXT_STRING = "TEXT_STRING";

    /** Id del texto cargado (desde BD). -1 si no hay nada cargado desde la BD. */
    private long textId = -1;

    /**
     * Metodo utilitario para setear texto en componente central. Resetea textId
     * 
     * @param newText
     */
    private void _setText(CharSequence newText) {
        ((EditText) findViewById(R.id.editText)).setText(newText);
    }

    /**
     * Metodo utilitario para setear texto en componente central. Resetea textId
     * 
     * @param newText
     */
    private void cleanTextAndId() {
        _setText("");
        this.textId = -1;
        Log.i("cleanText()", "");
    }

    /**
     * Metodo utilitario para setear texto en componente central.
     * 
     * @param newText
     */
    private void setTextFromDb(long textId, String text) {
        Log.i("setTextFromDb()", "id: " + textId + " - texto: '" + text + "'");
        cleanTextAndId();
        this.textId = textId;
        _setText(text);
    }

    /**
     * Al crear la Activity seteamos listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Instanciamos DBHelper para crear BD
        //

        new DBHelper(this);

        //
        // Creamos listener reutilizable
        //

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTextAndId();
                _setText(((Button) v).getText());
            }
        };

        //
        // Seteamos listener
        //

        ((Button) findViewById(R.id.btArriba)).setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                MainActivity.this.showDialog(DEFAULT_DIALOG);
            }

        });
        ((Button) findViewById(R.id.btAbajoIzq)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btAbajoDer)).setOnClickListener(onClickListener);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DEFAULT_DIALOG) {
            // .setIcon(R.drawable.icon)
            Dialog dialog = new AlertDialog.Builder(this).setTitle(R.string.dialogTitle)
                    .setPositiveButton(R.string.labelOk, null).setMessage(R.string.labelFunciono)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Log.i("DEFAULT_DIALOG", "Cobarde!");
                        }
                    }).create();
            return dialog;
        } else {
            return super.onCreateDialog(id);
        }
    }

    /**
     * On resume borramos el contenido del EditText
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.i("onMenuItemSelected()", "featureId: " + featureId + " - item: " + item);
        if (item.getItemId() == R.id.action_toast_1) {
            /*
             * Toast
             */
            Toast.makeText(getBaseContext(), R.string.menu_toast_1, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_toast_2) {
            /*
             * Toast
             */
            Toast.makeText(getBaseContext(), R.string.menu_toast_2, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_limpiar) {
            /*
             * Limpiar
             */
            cleanTextAndId();
            return true;
        } else if (item.getItemId() == R.id.action_load_text) {
            /*
             * Cargar texto desde BD
             */
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this,
                    "ar.com.hgdeoro.tdam.ejercicio01.ListTextsActivity"));
            this.startActivityForResult(intent, AFR_SELECT_TEXT_TO_LOAD);
            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult()", "requestCode: " + requestCode + " - resultCode: " + resultCode
                + " - data: " + data);
        if (requestCode == AFR_SELECT_TEXT_TO_LOAD) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                setTextFromDb(bundle.getLong(TEXT_ID), bundle.getString(TEXT_STRING));
            } else {
                Log.e("onActivityResult()", "resultCode != RESULT_OK");
            }
        } else {
            Log.e("onActivityResult()", "requestCode desconocido");
        }
    }

}
