package com.tdam2013.grupo05.utiles;

public class UtilesMensajesWeb {

	public static boolean usernameIsValid(String username) {
		// FIXME: mejorare esta logica
		if (username != null && username.trim().length() > 0)
			return true;
		return false;
	}
}
