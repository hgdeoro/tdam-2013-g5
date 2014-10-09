package com.tdam2013.grupo05.modelos;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representa un mensaje web.
 */
@SuppressLint("SimpleDateFormat")
public class MensajeWeb {

	/**
	 * DateFormat para parsear los timestamp que nos llegan desde el servicio
	 * http.
	 * 
	 * Timestamp => 30/09/2014 11:01:15
	 */
	public final static DateFormat TIMESTAMP_FROM_WEBSERVICE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	public MensajeWeb() {
	}

	public MensajeWeb(String user, String timestamp, String mensaje) {
		this.user = user;
		this.timestamp = timestamp;
		this.mensaje = mensaje;
	}

	/**
	 * Usuario originario o destinatario del mensaje
	 */
	private String user;

	private String mensaje;

	/**
	 * Timestamp del mensaje. Puede ser null.
	 */
	private String timestamp;

	public Date getTimestampAsDate() throws ParseException {
		return TIMESTAMP_FROM_WEBSERVICE_FORMAT.parse(timestamp);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
