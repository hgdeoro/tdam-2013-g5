package com.tdam2013.grupo05.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.tdam2013.grupo05.utiles.UtilesFecha;

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

	public String formatAsDateTime(Context ctx) {
		try {
			return UtilesFecha.formatearFechaHoraConMediumDateFormat(this.time,
					ctx);
		} catch (ParseException e) {
			Log.e("MensajeWebDto", "ParseException", e);
			return this.time;
		}
	}
}
