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
				Log.i("AccionesSobreContactoActivity", "Telefono: " + telefono);

				telefonos.add(telefono);
				// contact.addTelephoneNumber(pCur.getString(pCur
				// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			}

		} finally {
			if (cursor != null)
				cursor.close();
		}

		return telefonos;
	}

}
