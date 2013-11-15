package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.db.MensajeWebDto;

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

		((TextView) findViewById(R.id.mostrar_detalle_mensaje_web_contacto))
				.setText(dto.username);
		((TextView) findViewById(R.id.mostrar_detalle_mensaje_web_fecha_hora))
				.setText(dto.formatAsDateTime(this.getApplicationContext()));

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
