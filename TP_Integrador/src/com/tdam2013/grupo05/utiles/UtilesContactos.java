package com.tdam2013.grupo05.utiles;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class UtilesContactos {

	/**
	 * Devuelve lista de nros. telefonicos
	 * 
	 * @param contactId
	 * @return
	 */
	public static List<String> getTelefonos(Context ctx, Long contactId) {

		List<String> telefonos = new ArrayList<String>();
		Cursor cursor = null;

		try {
			cursor = ctx.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[] { contactId.toString() }, null);
			while (cursor.moveToNext()) {
				String telefono = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				Log.i("getTelefonos()", "Telefono: " + telefono);

				telefonos.add(telefono);
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
