package com.tdam2013.grupo05.utiles;

import java.util.ArrayList;
import java.util.List;

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

		public TelefonoDto(String telefono, String type, String label,
				String labelAsString) {
			this.telefono = telefono;
			this.type = type;
			this.label = label;
			this.labelAsString = labelAsString;
		}

		private final String telefono;

		private final String type;

		private final String label;

		private final String labelAsString;

		public String toString() {
			return "" + this.telefono + "(" + this.type + "/" + this.label
					+ "/" + labelAsString + ")";
		}

		// -----

		public String getTelefono() {
			return telefono;
		}

		public String getType() {
			return type;
		}

		public String getLabel() {
			return label;
		}

		public String getLabelAsString() {
			return labelAsString;
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

				int contactTypeInt = Integer.parseInt(contactType);

				String resolvedLabel = ContactsContract.CommonDataKinds.Phone
						.getTypeLabel(ctx.getResources(), contactTypeInt,
								contactLabel).toString();

				telefonos.add(new TelefonoDto(telefono, contactType,
						contactLabel, resolvedLabel));
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

}
