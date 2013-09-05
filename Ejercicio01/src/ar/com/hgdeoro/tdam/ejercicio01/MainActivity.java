package ar.com.hgdeoro.tdam.ejercicio01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

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
                MainActivity.this.showDialog(0);
            }

        });
        ((Button) findViewById(R.id.btAbajoIzq)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btAbajoDer)).setOnClickListener(onClickListener);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // .setIcon(R.drawable.icon)
        Dialog dialog = new AlertDialog.Builder(this).setTitle("Pts! Hey!")
                .setPositiveButton("OK", null).setMessage("Funcionó!").create();
        return dialog;
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

}
