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
import com.tdam2013.grupo05.utiles.UtilesIntents;

/**
 * Muestra el detalle de un mensaje web.
 * 
 * Posee menu para permitir borrar el mensaje del historial.
 * 
 * @author Horacio G. de Oro
 *
 */
public class MostrarDetalleMensajeWebActivity extends Activity {

	/** "Name", para putExtra() de Intent */
	public static final String MESSAGE_WEB_ID = "MESSAGE_WEB_ID";

	/** "Name", para putExtra() de Intent */
	public static final String CONTACT_NAME = "CONTACT_NAME";

	// FIXME: esta activity queda en el stack. Luego de borrar el item del
	// historial, si se hace back, se vuelve a mostrar el mensaje, ya borrado!
	// ¿Como se saca esto del stack de activities?
	//
	// Ver, por ejemplo:
	// https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TOP

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

			String contactName = intent.getExtras().getString(CONTACT_NAME);

			Intent intentNewActivity = UtilesIntents
					.getHistorialDeContactoActivityIntent(this, contactName);
			// Necesitamos FLAG_ACTIVITY_CLEAR_TOP para que el back stack quede
			// bien seteado, y no quede en el back stack la activity que muestra
			// el detalle del mensaje recien borrado!
			intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intentNewActivity);

		}

		Log.i("onMenuItemSelected()", "Item no manejado "
				+ "(puede deberse a que se utilizo el menu contextual)");
		return super.onMenuItemSelected(featureId, item);

	}
}
