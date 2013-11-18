package com.tdam2013.grupo05.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public static int DB_VERSION = 2;

	public static final String DB_NAME = "database.db";

	@SuppressWarnings("unused")
	private Context context = null;

	public Database(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(String.format("CREATE TABLE %s (   " /* T_NAME */
				+ "%s INTEGER PRIMARY KEY,            " /* F_ID */
				+ "%s TEXT NOT NULL,                  " /* F_USERNAME */
				+ "%s INTEGER NOT NULL,               " /* F_DIRECTION */
				+ "%s TIMESTAMP NOT NULL DEFAULT current_timestamp, " /* F_TIME */
				+ "%s TEXT NOT NULL                   " /* F_TEXT */
				+ ")", TABLE_WEB_MESSAGES.T_NAME, TABLE_WEB_MESSAGES.F_ID,
				TABLE_WEB_MESSAGES.F_USERNAME, TABLE_WEB_MESSAGES.F_DIRECTION,
				TABLE_WEB_MESSAGES.F_TIME, TABLE_WEB_MESSAGES.F_TEXT));

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void insertSentMessage(String toUser, String text) {
		ContentValues cv = new ContentValues();
		cv.put(TABLE_WEB_MESSAGES.F_USERNAME, toUser);
		cv.put(TABLE_WEB_MESSAGES.F_DIRECTION,
				TABLE_WEB_MESSAGES.DIRECTION_MESSAGE_SENT);
		// cv.put(TABLE_WEB_MESSAGES.F_TIME);
		cv.put(TABLE_WEB_MESSAGES.F_TEXT, text);

		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_WEB_MESSAGES.T_NAME, null, cv);
		db.close();
	}

	/**
	 * Busca mensajes enviados, especificando filtros y ordenamiento
	 * 
	 * @return
	 */
	public Cursor searchSentWebMessages(TABLE_WEB_MESSAGES.Filter filtro,
			TABLE_WEB_MESSAGES.OrderBy order, String username) {

		String[] columns = new String[] { TABLE_WEB_MESSAGES.F_ID,
				TABLE_WEB_MESSAGES.F_USERNAME, TABLE_WEB_MESSAGES.F_TIME,
				TABLE_WEB_MESSAGES.F_TEXT };

		/*
		 * Filtro
		 */

		String selection = "";
		ArrayList<String> selectionList = new ArrayList<String>();

		if (username != null) {
			selection += TABLE_WEB_MESSAGES.F_USERNAME + " = ?";
			selectionList.add(username);
		}

		if (filtro != null)
			switch (filtro) {
			case WEEK:
				if (selection.trim().length() > 0)
					selection += " AND ";
				selection += TABLE_WEB_MESSAGES.F_TIME
						+ " >= datetime('now', '-7 days')";
				break;
			case DAY:
				if (selection.trim().length() > 0)
					selection += " AND ";
				selection += TABLE_WEB_MESSAGES.F_TIME
						+ " >= datetime('now', '-1 day')";
				break;
			default:
				break;
			}

		String[] selectionArgs = new String[selectionList.size()];
		for (int i = 0; i < selectionArgs.length; i++)
			selectionArgs[i] = selectionList.get(i);

		/*
		 * 
		 */

		final String qOrderBy;
		if (username != null) {
			// Si se especifico usuario, NO tiene sentido ordenar
			// alfabeticamente, por lo tanto, ignoramos la configuracion
			qOrderBy = "" + TABLE_WEB_MESSAGES.F_TIME + " DESC";
		} else {
			if (order == null)
				order = TABLE_WEB_MESSAGES.OrderBy.CRONOLOGICO;
			switch (order) {
			case ALFABETICO:
				qOrderBy = "" + TABLE_WEB_MESSAGES.F_USERNAME + " ASC";
				break;
			default:
				qOrderBy = "" + TABLE_WEB_MESSAGES.F_TIME + " DESC";
				break;
			}
		}

		return this.getReadableDatabase().query(TABLE_WEB_MESSAGES.T_NAME,
				columns, selection, selectionArgs, null, null, qOrderBy);
	}

	public MensajeWebDto getMensajeById(long id) {

		SQLiteDatabase db = null;
		Cursor cursor = null;

		try {
			db = this.getReadableDatabase();

			String[] columns = new String[] { TABLE_WEB_MESSAGES.F_ID,
					TABLE_WEB_MESSAGES.F_USERNAME, TABLE_WEB_MESSAGES.F_TIME,
					TABLE_WEB_MESSAGES.F_TEXT, TABLE_WEB_MESSAGES.F_DIRECTION };

			String selection = TABLE_WEB_MESSAGES.F_ID + " == ?";

			String[] selectionArgs = new String[] { "" + id };

			cursor = db.query(TABLE_WEB_MESSAGES.T_NAME, columns, selection,
					selectionArgs, null, null, null);

			if (cursor.moveToNext()) {
				MensajeWebDto dto = new MensajeWebDto();
				dto.id = cursor.getLong(cursor
						.getColumnIndex(TABLE_WEB_MESSAGES.F_ID));
				dto.text = cursor.getString(cursor
						.getColumnIndex(TABLE_WEB_MESSAGES.F_TEXT));
				dto.username = cursor.getString(cursor
						.getColumnIndex(TABLE_WEB_MESSAGES.F_USERNAME));
				dto.direction = cursor.getInt(cursor
						.getColumnIndex(TABLE_WEB_MESSAGES.F_DIRECTION));
				dto.time = cursor.getString(cursor
						.getColumnIndex(TABLE_WEB_MESSAGES.F_TIME));
				return dto;

			} else {
				return null;
			}

		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
	}

	/**
	 * Table with messages
	 */
	public static final class TABLE_WEB_MESSAGES {
		/*
		 * Table
		 */
		public static final String T_NAME = "web_messages";

		/*
		 * Fields
		 */
		public static final String F_ID = "_id";
		public static final String F_USERNAME = "username";
		public static final String F_DIRECTION = "message_direction";
		public static final String F_TIME = "message_time";
		public static final String F_TEXT = "message_text";

		/*
		 * Constants
		 */

		/** Message was sent to user referenced by F_USERNAME */
		public static final int DIRECTION_MESSAGE_SENT = 1;

		/** Message was received from user referenced by F_USERNAME */
		public static final int DIRECTION_MESSAGE_RECEIVED = 2;

		/*
		 * OrderBy
		 */

		public enum OrderBy {
			ALFABETICO, CRONOLOGICO
		};

		public enum Filter {
			ALL, DAY, WEEK
		};
	};

}
