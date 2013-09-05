package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final int DEFAULT_DIALOG = 0;

    /**
     * Metodo utilitario para setear texto en componente central.
     * 
     * @param newText
     */
    private void setText(CharSequence newText) {
        ((EditText) findViewById(R.id.editText)).setText(newText);
    }

    /**
     * Al crear la Activity seteamos listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Creamos listener reutilizable
        //

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                setText(((Button) v).getText());
            }
        };

        //
        // Seteamos listener
        //

        ((Button) findViewById(R.id.btArriba)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity.this.showDialog(DEFAULT_DIALOG);
            }

        });
        ((Button) findViewById(R.id.btAbajoIzq)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btAbajoDer)).setOnClickListener(onClickListener);
    }

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
        setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.action_toast_1) {
            Toast.makeText(getBaseContext(), R.string.menu_toast_1, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_toast_2) {
            Toast.makeText(getBaseContext(), R.string.menu_toast_2, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }
}
