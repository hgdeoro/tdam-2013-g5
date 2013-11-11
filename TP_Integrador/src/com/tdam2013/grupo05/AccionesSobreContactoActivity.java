package com.tdam2013.grupo05;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tdam2013.grupo05.utiles.UtilesContactos;
import com.tdam2013.grupo05.utiles.UtilesIntents;

public class AccionesSobreContactoActivity extends ListActivity {

	public static final String CONTACT_ID = "CONTACT_ID";

	public static final String LLAMAR = "Llamar";
	public static final String SMS = "Enviar SMS";
	public static final String EMAIL = "Enviar email";
	public static final String MSGWEB = "Enviar mensaje web";

	public static final String[] acciones = new String[] { LLAMAR, LLAMAR, SMS,
			SMS, EMAIL, MSGWEB };

	// TODO: crear custom adaptar para cargar ambos textos: accion y dato

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acciones_sobre_contacto_activity);

		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.acciones_sobre_contacto_activity_item,
				R.id.acciones_sobre_contacto_item_accion, acciones));

		Long contactId = this.getIntent().getExtras().getLong(CONTACT_ID);

		Log.i("AccionesSobreContactoActivity", "contactId: " + contactId);

		List<String> telefonos = UtilesContactos.getTelefonos(
				this.getApplicationContext(), contactId);

	}

	/**
	 * List Item
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final String accion = acciones[position];
		if (LLAMAR.equals(accion)) {
			this.startActivity(UtilesIntents.getCallPhoneIntent("0"));

		} else if (SMS.equals(accion)) {
			this.startActivity(UtilesIntents.getSendSmsIntent("0"));

		} else if (EMAIL.equals(accion)) {
			this.startActivity(UtilesIntents
					.getSendEmailIntent("alguien@example.com"));

		} else if (MSGWEB.equals(accion)) {
			this.startActivity(UtilesIntents
					.getEnviarMensajeWebActivityIntent(this));

		} else {

			Toast.makeText(getBaseContext(), "Accion desconocida",
					Toast.LENGTH_SHORT).show();
		}
	}

}
