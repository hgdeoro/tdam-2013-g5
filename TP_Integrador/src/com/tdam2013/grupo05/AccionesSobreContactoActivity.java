package com.tdam2013.grupo05;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesContactos;
import com.tdam2013.grupo05.utiles.UtilesIntents;

public class AccionesSobreContactoActivity extends ListActivity {

	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String DISPLAY_NAME = "DISPLAY_NAME";

	public static final String LLAMAR = "Llamar";
	public static final String SMS = "Enviar SMS";
	public static final String EMAIL = "Enviar email";
	public static final String MSGWEB = "Enviar mensaje web";

	private List<Info> infoList = new ArrayList<Info>();

	private Long contactId = null;
	private String displayName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acciones_sobre_contacto_activity);

		contactId = this.getIntent().getExtras().getLong(CONTACT_ID);
		displayName = this.getIntent().getExtras().getString(DISPLAY_NAME);

		Log.i("AccionesSobreContactoActivity", "contactId: " + contactId);

		for (UtilesContactos.TelefonoDto telefono : UtilesContactos
				.getTelefonos(this.getApplicationContext(), contactId)) {

			infoList.add(new Info(LLAMAR, telefono.getTelefono(), telefono
					.getLabelAsString()));
			infoList.add(new Info(SMS, telefono.getTelefono(), telefono
					.getLabelAsString()));
		}

		for (String valor : UtilesContactos.getEmails(
				this.getApplicationContext(), contactId)) {
			infoList.add(new Info(EMAIL, valor));
		}

		// FIXME: esta OK usar el contact id? Sino, usar el nombre del contacto
		infoList.add(new Info(MSGWEB, displayName));

		this.setListAdapter(new CustomAdapter());
	}

	/**
	 * List Item
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Info info = infoList.get(position);

		if (LLAMAR.equals(info.accion)) {
			this.startActivity(UtilesIntents.getCallPhoneIntent(info.valor));

		} else if (SMS.equals(info.accion)) {
			this.startActivity(UtilesIntents.getSendSmsIntent(info.valor));

		} else if (EMAIL.equals(info.accion)) {
			this.startActivity(UtilesIntents.getSendEmailIntent(info.valor));

		} else if (MSGWEB.equals(info.accion)) {

			// FIXME: esta bien que hagamos la busqueda en el main-thread?
			// Es un simple select...

			String contactUsername = Database.getDatabase(this)
					.getUsernameDeContacto(contactId);

			this.startActivity(UtilesIntents.getEnviarMensajeWebActivityIntent(
					getApplicationContext(), contactUsername, contactId,
					displayName));

		} else {

			Toast.makeText(getApplicationContext(), "Accion desconocida",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Custom Adapter
	 */

	class Holder {
		/** Accion: llamar, sms, etc. */
		TextView accion;

		/** Valor a usar para la accion: telefono, email, etc. */
		TextView valor;

		/**
		 * Tipo de contacto, solo para nros telefonicos. Ej: casa, trabajo, etc.
		 */
		TextView contactType;

	}

	/**
	 * Encapsula datos a mostrar en un item de la lista
	 */
	class Info {

		/** Accion a realiar (llamar, enviar sms, etc.) */
		final String accion;

		/** Numero telefonico o email */
		final String valor;

		/**
		 * Tipo de contacto, solo para nros telefonicos. Ej: casa, trabajo, etc.
		 */
		final String contactType;

		public Info(String accion, String valor) {
			this.accion = accion;
			this.valor = valor;
			this.contactType = null;
		}

		public Info(String accion, String valor, String contactType) {
			this.accion = accion;
			this.valor = valor;
			this.contactType = contactType;
		}
	}

	class CustomAdapter extends BaseAdapter {

		private LayoutInflater inflater = LayoutInflater
				.from(AccionesSobreContactoActivity.this);

		public CustomAdapter() {
		}

		@Override
		public int getCount() {
			return infoList.size();
		}

		@Override
		public Object getItem(int position) {
			return infoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.acciones_sobre_contacto_activity_item, null);
				holder = new Holder();
				holder.accion = (TextView) convertView
						.findViewById(R.id.acciones_sobre_contacto_item_accion);
				holder.valor = (TextView) convertView
						.findViewById(R.id.acciones_sobre_contacto_item_valor);
				holder.contactType = (TextView) convertView
						.findViewById(R.id.acciones_sobre_contacto_item_label);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Info info = (Info) getItem(position);
			holder.accion.setText(info.accion);
			holder.valor.setText(info.valor);

			if (info.contactType == null) {
				convertView.findViewById(
						R.id.acciones_sobre_contacto_item_label).setVisibility(
						View.INVISIBLE);
				holder.contactType.setText("(null)");
			} else {
				convertView.findViewById(
						R.id.acciones_sobre_contacto_item_label).setVisibility(
						View.VISIBLE);
				holder.contactType.setText(info.contactType);
			}

			return convertView;
		}

	}
}
