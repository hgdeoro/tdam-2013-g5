package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    /*------------------------------------------------------------------------------------------
     * Metodos utilitarios para UI y BD
     *------------------------------------------------------------------------------------------*/

    /**
     * Metodo utilitario para setear texto en componente central. Resetea textId
     * 
     * @param newText
     */
    private void cleanTextAndId() {
        ((EditText) findViewById(R.id.editText)).setText("");
        this.textId = -1;
        Log.i("cleanText()", "");
    }

    /**
     * Metodo utilitario para setear texto en componente central.
     * 
     * @param newText
     */
    private void setTextoYId(long textId, String text) {
        Log.i("setTextFromDb()", "id: " + textId + " - texto: '" + text + "'");
        cleanTextAndId(); // mmm... redundante, no?
        this.textId = textId;
        ((EditText) findViewById(R.id.editText)).setText(text);
    }

    /**
     * Carga texto/id desde BD.
     * 
     * @param id
     */
    private void cargarTextoById(long id) {
        if (id <= 0)
            throw new RuntimeException("El 'id' recibido no es valido: " + id);

        String text = new DBHelper(this).obtenerTextoById(id);
        if (text == null)
            Log.w("cargarTextoById()", "DBHelper.obtenerTextoById(" + id + ") ha devuelto null");

        setTextoYId(id, text);
    }

    /*------------------------------------------------------------------------------------------
     * Activity
     *------------------------------------------------------------------------------------------*/

    /**
     * Al crear la Activity seteamos listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Instanciamos DBHelper para crear o actualizar BD
        //

        new DBHelper(this);

        //
        // Seteamos listeners
        //

        // Se pidio que el boton de arriba abriera un Dialog
        ((Button) findViewById(R.id.btArriba)).setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                MainActivity.this.showDialog(DEFAULT_DIALOG);
            }

        });

        // Guardamos (update/insert) texto en BD
        ((Button) findViewById(R.id.btGuardar)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (texto.trim().length() == 0) {
                    Toast.makeText(getBaseContext(), R.string.no_hay_mensaje_que_guardar,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textId == -1) {
                    long newId = new DBHelper(MainActivity.this).guardarTexto(textId, texto);
                    MainActivity.this.cargarTextoById(newId);
                    Toast.makeText(getBaseContext(), R.string.text_inserted_ok, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    new DBHelper(MainActivity.this).guardarTexto(textId, texto);
                    MainActivity.this.cargarTextoById(textId);
                    Toast.makeText(getBaseContext(), R.string.text_updated_ok, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        // Limpiamos texto
        ((Button) findViewById(R.id.btLimpiar)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTextAndId();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult()", "requestCode: " + requestCode + " - resultCode: " + resultCode
                + " - data: " + data);
        if (requestCode == AFR_SELECT_TEXT_TO_LOAD) {
            if (resultCode == Activity.RESULT_OK) {
                final long id = data.getExtras().getLong(TEXT_ID);
                Log.i("onActivityResult()", "id recibido: " + id);
                this.cargarTextoById(id);
            } else {
                Log.e("onActivityResult()", "resultCode != RESULT_OK");
            }
        } else {
            Log.e("onActivityResult()", "requestCode desconocido");
        }
    }

    /*------------------------------------------------------------------------------------------
     * Dialog
     *------------------------------------------------------------------------------------------*/

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

    /*------------------------------------------------------------------------------------------
     * Menu
     *------------------------------------------------------------------------------------------*/

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

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this, MyPrefActivity.class.getCanonicalName()));
            startActivity(intent);

            Toast.makeText(getBaseContext(), R.string.menu_toast_1, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_toast_2) {
            /*
             * Toast
             */

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            boolean loginAuto = sp.getBoolean("login_automatico", true);
            Toast.makeText(getBaseContext(), "El valor es: " + loginAuto, Toast.LENGTH_SHORT)
                    .show();

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

}
