package com.tdam2013.grupo05;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
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
import com.tdam2013.grupo05.utiles.UtilesContactos.TelefonoDto;

public class AccionesSobreContactoFragment extends ListFragment {

	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String DISPLAY_NAME = "DISPLAY_NAME";

	public static final String LLAMAR = "Llamar";
	public static final String SMS = "Enviar SMS";
	public static final String EMAIL = "Enviar email";
	public static final String MSGWEB = "Enviar mensaje web";

	// private int mIndex = 0; @241
	private Long contactId = null;
	private String displayName = null;

	private List<Info> infoList = new ArrayList<Info>();

	// @241
	public static AccionesSobreContactoFragment newInstance(Long contactId,
			String displayName) {

		AccionesSobreContactoFragment fragment = new AccionesSobreContactoFragment();
		Bundle args = new Bundle();
		args.putLong(CONTACT_ID, contactId);
		args.putString(DISPLAY_NAME, displayName);

		fragment.setArguments(args);

		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactId = getArguments().getLong(CONTACT_ID);
		displayName = getArguments().getString(DISPLAY_NAME);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// ------------------------------------------------------------
		// https://stackoverflow.com/questions/21337717/listfragment-setlistadapter
		// ------------------------------------------------------------
		// if (listAdapter == null) {
		// listAdapter = new MyAdapter(getActivity());
		// }
		//
		// setListAdapter(listAdapter);
		// pbListLoading.setVisibility(View.VISIBLE);
		// ------------------------------------------------------------

		cargarLista();
		setListAdapter(new CustomAdapter());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// @242
		// Don't tie this fragment to anything through the inflater.
		// Android takes care of attaching for us.
		View v = inflater.inflate(R.layout.acciones_sobre_contacto_activity,
				container, false);
		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Info info = infoList.get(position);

		if (LLAMAR.equals(info.accion)) {
			this.startActivity(UtilesIntents.getCallPhoneIntent(info.valor));

		} else if (SMS.equals(info.accion)) {
			this.startActivity(UtilesIntents.getSendSmsIntent(info.valor));

		} else if (EMAIL.equals(info.accion)) {
			this.startActivity(UtilesIntents.getSendEmailIntent(info.valor));

		} else if (MSGWEB.equals(info.accion)) {

			// Es una busqueda MUY simple, la hacemos desde la UI...

			String contactUsername = Database.getDatabase(getActivity())
					.getUsernameDeContacto(contactId);

			this.startActivity(EnviarMensajeWebActivity
					.getEnviarMensajeWebActivityIntent(getActivity()
							.getApplicationContext(), contactUsername,
							contactId, displayName));

		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"Accion desconocida", Toast.LENGTH_SHORT).show();
		}

	}

	// ----------------------------------------------------------------------
	// FIN manejo de API de Android
	// ----------------------------------------------------------------------

	private void cargarLista() {

		// infoList.add(new Info(LLAMAR, "0123456789", "HOME"));
		// infoList.add(new Info(LLAMAR, "0123456789", "HOME"));
		// infoList.add(new Info(LLAMAR, "0123456789", "HOME"));

		List<TelefonoDto> telefonos = UtilesContactos.getTelefonos(this
				.getActivity().getApplicationContext(), contactId);

		for (UtilesContactos.TelefonoDto telefono : telefonos) {

			infoList.add(new Info(LLAMAR, telefono.getTelefono(), telefono
					.getLabelAsString()));
			infoList.add(new Info(SMS, telefono.getTelefono(), telefono
					.getLabelAsString()));
		}

		for (String valor : UtilesContactos.getEmails(this.getActivity()
				.getApplicationContext(), contactId)) {

			infoList.add(new Info(EMAIL, valor));

		}

		infoList.add(new Info(MSGWEB, displayName));

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

	class CustomAdapter extends BaseAdapter {

		private LayoutInflater inflater = LayoutInflater
				.from(AccionesSobreContactoFragment.this.getActivity());

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

		@SuppressLint("InflateParams")
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
