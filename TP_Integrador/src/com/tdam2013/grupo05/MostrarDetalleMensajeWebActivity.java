package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.db.MensajeWebDto;

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
	public static final String CONTACT_USERNAME = "CONTACT_USERNAME";

	private Long msgId = null;
	private String contactUsername = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_detalle_mensaje_web_activity);

		Intent intent = getIntent();
		msgId = intent.getExtras().getLong(MESSAGE_WEB_ID);
		contactUsername = intent.getExtras().getString(CONTACT_USERNAME);

		MensajeWebDto dto = Database.getDatabase(this.getApplicationContext())
				.getMensajeById(msgId);

		((TextView) findViewById(R.id.mostrar_detalle_mensaje_web_contacto))
				.setText(dto.username);
		((TextView) findViewById(R.id.mostrar_detalle_mensaje_web_fecha_hora))
				.setText(dto.formatAsDateTime(this.getApplicationContext()));

		EditText editText = (EditText) findViewById(R.id.mostrar_detalle_mensaje_web_texto);
		editText.setText(dto.text);
		editText.setEnabled(false);

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

			Database db = Database.getDatabase(this.getApplicationContext());
			db.deleteSentMessage(msgId);

			Intent intentNewActivity = HistorialActivity
					.getHistorialDeContactoActivityIntent(this,
							contactUsername, null);

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

	public static Intent getMostrarDetalleMensajeWebActivity(Context ctx,
			long messageId, String contactUsername) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				MostrarDetalleMensajeWebActivity.class.getCanonicalName()));
		intent.putExtra(MostrarDetalleMensajeWebActivity.MESSAGE_WEB_ID,
				messageId);
		intent.putExtra(MostrarDetalleMensajeWebActivity.CONTACT_USERNAME,
				contactUsername);
		return intent;
	}

}
