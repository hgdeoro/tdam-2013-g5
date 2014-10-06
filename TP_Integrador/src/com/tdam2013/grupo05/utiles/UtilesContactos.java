package com.tdam2013.grupo05.utiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class UtilesContactos {

	/**
	 * Encapsula la informacion de un telefono de un contacto
	 */
	public static class TelefonoDto {

		public TelefonoDto(String telefono, String type, String label) {
			this.telefono = telefono;
			this.type = type;
			this.label = label;
		}

		private String telefono;

		private String type;

		private String label;

		public String getLabelAsString() {
			return "" + this.type + "/" + this.label;
		}

		public String toString() {
			return "" + this.telefono + "(" + this.type + "/" + this.label
					+ ")";
		}

		// -----

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

	}

	/**
	 * Devuelve lista de nros. telefonicos
	 * 
	 * @param contactId
	 * @return
	 */
	public static List<TelefonoDto> getTelefonos(Context ctx, Long contactId) {

		List<TelefonoDto> telefonos = new ArrayList<TelefonoDto>();
		Cursor cursor = null;

		try {
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			String[] projection = new String[] {};
			String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
					+ " = ?";
			String[] selectionArgs = new String[] { contactId.toString() };
			String sortOrder = null;

			cursor = ctx.getContentResolver().query(uri, projection, selection,
					selectionArgs, sortOrder);
			while (cursor.moveToNext()) {

				String telefono = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				Log.i("getTelefonos()", "Telefono: " + telefono);

				String contactType = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
				Log.i("getTelefonos()", "contactType: " + contactType);

				String contactLabel = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
				Log.i("getTelefonos()", "contactLabel: " + contactLabel);

				telefonos.add(new TelefonoDto(telefono, contactType,
						contactLabel));
			}

		} finally {
			if (cursor != null)
				cursor.close();
		}

		return telefonos;
	}

	/**
	 * Devuelve lista de emails
	 * 
	 * @param contactId
	 * @return
	 */
	public static List<String> getEmails(Context ctx, Long contactId) {

		List<String> emails = new ArrayList<String>();
		Cursor cursor = null;

		try {
			cursor = ctx.getContentResolver().query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
					new String[] { contactId.toString() }, null);
			while (cursor.moveToNext()) {
				String email = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				Log.i("getEmails()", "Email: " + email);

				emails.add(email);
			}

		} finally {
			if (cursor != null)
				cursor.close();
		}

		return emails;
	}

	/**
	 * Formatea username desde display name. Esto es un workaround temporal.
	 */
	public static String formatUsername(String displayName) {
		String usernameFormatted = displayName.toString().replace(" ", "_")
				.toUpperCase(Locale.getDefault());
		if (usernameFormatted.length() > 12)
			usernameFormatted = usernameFormatted.substring(0, 12);
		return usernameFormatted;
	}

}
