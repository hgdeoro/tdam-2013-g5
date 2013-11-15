package com.tdam2013.grupo05.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * DTO (Data Transfer Object) para mensaje web.
 * 
 * @author Horacio G. de Oro
 * 
 */
public class MensajeWebDto {

	// public static final SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss.SSS");

	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public long id;
	public String text;
	public String username;
	public int direction;

	// TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS").
	public String time;

	// public Date getTimeAsDate() {
	// return new Date(time);
	// }

	public String formatAsDateTime(Context ctx) {
		try {
			return DateFormat.getDateFormat(ctx).format(sdf.parse(this.time))
					+ ", "
					+ DateFormat.getTimeFormat(ctx)
							.format(sdf.parse(this.time));
		} catch (ParseException e) {
			Log.e("formatTime()", "ParseException", e);
			return this.time;
		}
	}
}
