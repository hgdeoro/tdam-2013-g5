package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mostrar_detalle_mensaje_web, menu);
		return true;
	}

	/**
	 * Menu
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.i("onMenuItemSelected()", "item: " + item);

		if (item.getItemId() == R.id.action_borrar_item_historial) {

			// TODO: pedir confirmacion?

			Intent intent = getIntent();
			long msgId = intent.getExtras().getLong(MESSAGE_WEB_ID);
			Database db = new Database(this.getApplicationContext());
			db.deleteSentMessage(msgId);

			// FIXME: redireccionar ususario a activity de historial
			
			// Toast.makeText(getBaseContext(), "BORRANDO ITEM: " + msgId,
			// Toast.LENGTH_SHORT).show();
		}

		Log.i("onMenuItemSelected()", "Item no manejado "
				+ "(puede deberse a que se utilizo el menu contextual)");
		return super.onMenuItemSelected(featureId, item);

	}
}
