package com.tdam2013.grupo05.db;

import android.content.ContentValues;
import android.content.Context;
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

	// public Cursor getSentWebMessages() {
	// String orderBy = "" + TABLE_WEB_MESSAGES.F_TIME + " DESC";
	//
	// return this.getReadableDatabase().query(
	// TABLE_WEB_MESSAGES.T_NAME,
	// new String[] { TABLE_WEB_MESSAGES.F_USERNAME,
	// TABLE_WEB_MESSAGES.F_TIME, TABLE_WEB_MESSAGES.F_TEXT },
	// null, null, null, null, orderBy);
	// }

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

	};

}
