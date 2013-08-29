package ar.com.hgdeoro.tdam.ejercicio01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private void setText(CharSequence newText) {
        ((EditText) findViewById(R.id.editText)).setText(newText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                setText(((Button) v).getText());
            }
        };

        ((Button) findViewById(R.id.btArriba)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btAbajoIzq)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btAbajoDer)).setOnClickListener(onClickListener);
    }

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
