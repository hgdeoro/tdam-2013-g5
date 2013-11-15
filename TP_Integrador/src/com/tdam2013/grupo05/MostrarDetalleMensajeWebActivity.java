package com.tdam2013.grupo05;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.db.MensajeWebDto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MostrarDetalleMensajeWebActivity extends Activity {

	public static final String MESSAGE_WEB_ID = "MESSAGE_WEB_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_detalle_mensaje_web_activity);

		Intent intent = getIntent();
		long msgId = intent.getExtras().getLong(MESSAGE_WEB_ID);
		Database db = new Database(this.getApplicationContext());
		MensajeWebDto dto = db.getMensajeById(msgId);

		EditText editText = (EditText) findViewById(R.id.mostrar_detalle_mensaje_web_texto);
		editText.setText(dto.text);
		editText.setEnabled(false);

		db.close();
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
