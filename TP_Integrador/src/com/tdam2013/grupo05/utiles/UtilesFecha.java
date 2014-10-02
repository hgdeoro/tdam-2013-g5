package com.tdam2013.grupo05.utiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

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

	public static String formatearFecha(Date fecha) {
		return sdfFormato.format(fecha);
	}
}
