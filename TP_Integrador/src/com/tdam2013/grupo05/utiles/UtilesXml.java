package com.tdam2013.grupo05.utiles;

public class UtilesXml {

	public static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&apos;");

	}
}
