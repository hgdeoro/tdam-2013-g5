package com.tdam2013.grupo05;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MostrarDetalleMensajeWebActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_detalle_mensaje_web_activity);
		EditText editText = (EditText) findViewById(R.id.mostrar_detalle_mensaje_web_texto);
		editText.setText("Este es el texto del mensaje  el texto del mensaje "
				+ " el texto del mensaje  el texto del mensaje  el texto del "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "mensaje  el texto del mensaje  el texto del mensaje  el "
				+ "texto del mensaje  el texto del mensaje  el texto del mensaje ");
		editText.setEnabled(false);
	}

	/*
	 * PARA FINAL: servirá para eliminar mensaje!
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.mostrar_detalle_mensaje_web, menu);
	 * return true; }
	 */
}
