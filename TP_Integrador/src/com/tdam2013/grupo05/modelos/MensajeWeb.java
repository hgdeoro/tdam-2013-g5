package com.tdam2013.grupo05.modelos;

/**
 * Representa un mensaje web.
 */
public class MensajeWeb {

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
