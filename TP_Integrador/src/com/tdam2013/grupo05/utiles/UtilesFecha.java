package com.tdam2013.grupo05.utiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;

public class UtilesFecha {

	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdfParseo = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdfFormato = new SimpleDateFormat(
			"dd/MM/yyyy 'a las' HH:mm:ss");

	public static Date parsearFecha(String fecha) throws ParseException {
		return sdfParseo.parse(fecha);
	}

	public static String formatearFechaHora(Date fecha) {
		return sdfFormato.format(fecha);
	}

	public static String formatearFechaHoraConMediumDateFormat(Date fecha,
			Context ctx) {
		return DateFormat.getMediumDateFormat(ctx).format(fecha) + " a las "
				+ DateFormat.getTimeFormat(ctx).format(fecha);
	}

	public static String formatearFechaHoraConMediumDateFormat(String fecha,
			Context ctx) throws ParseException {
		Date fechaAsDate = UtilesFecha.parsearFecha(fecha);
		return UtilesFecha.formatearFechaHoraConMediumDateFormat(fechaAsDate,
				ctx);
	}

}
