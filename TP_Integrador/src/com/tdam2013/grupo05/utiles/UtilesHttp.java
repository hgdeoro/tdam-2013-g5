package com.tdam2013.grupo05.utiles;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tdam2013.grupo05.modelos.MensajeWeb;

import android.util.Log;

public class UtilesHttp {

	public boolean debug = true;

	private final String baseUrl;

	public static final String DEFAULT_PASSWORD = "123456";

	protected static final Random randomGenerator = new Random();

	public static final String REGISTER_USER_XML = "<action id=\"%s\" name=\"register-user\">"
			+ "<action-detail>"
			+ "<user username=\"%s\" password=\"%s\"/>"
			+ "</action-detail></action>";

	public static final String SEND_MESSAGE_XML = "<action id=\"%s\" name=\"send-message\">"
			+ "<action-detail><auth username=\"%s\" key=\"%s\"></auth>"
			+ "<message to=\"%s\"><![CDATA[%s]]></message></action-detail></action>";

	public static final String GET_MESSAGES_XML = "<action id=\"%s\" name=\"get-messages\">"
			+ "<action-detail><auth username=\"%s\" key=\"%s\"></auth>"
			+ "<filter type=\"timestamp\">%s</filter></action-detail></action>";

	public UtilesHttp() {
		baseUrl = "http://10.0.2.2:8080";
	}

	public UtilesHttp(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Post a messages (the payload) and return the response.
	 * 
	 * @param payload
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String post(String payload) throws ClientProtocolException,
			IOException {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(baseUrl + "/messages/Sender");
		post.setEntity(new StringEntity(payload));

		HttpResponse response = client.execute(post);
		if (debug)
			for (Header h : response.getAllHeaders())
				Log.d("post()", "H> " + h.getName() + ": " + h.getValue());

		Log.d("post()", "getStatusLine(): "
				+ response.getStatusLine().toString());

		HttpEntity entity = response.getEntity();
		if (entity == null) {
			Log.w("post()", ">> entity == null");
			return null;

		} else {
			StringBuffer sb = new StringBuffer();
			if (debug)
				Log.d("post()", ">> entity != null");

			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					instream));
			String line = br.readLine();
			if (line == null || "".equals(line))
				Log.i("post()", "> linea: null o empty");

			while (line != null) {
				Log.d("post()", ">" + line);
				sb.append(line);
				line = br.readLine();
			}

			instream.close();
			return sb.toString();
		}

	}

	public boolean registerUser(String username)
			throws ClientProtocolException, IOException,
			ParserConfigurationException, SAXException {

		final String random = "" + randomGenerator.nextLong();

		String xmlResponse = post(String.format(REGISTER_USER_XML, random,
				UtilesXml.escape(username), DEFAULT_PASSWORD));

		/*
		 * <?xml version="1.0" encoding="UTF-8"?><result type="success"
		 * id="-2129497889206272016"><user
		 * username="user1_8663"></user></result>
		 */

		if (debug)
			Log.d("registerUser()", "XML response: " + xmlResponse);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(new ByteArrayInputStream(xmlResponse
				.getBytes("UTF-8")));

		NodeList results = doc.getElementsByTagName("result");
		Node result = results.item(0);
		String type = result.getAttributes().getNamedItem("type")
				.getTextContent();

		if ("success".equals(type)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean sendMessage(String fromUser, String toUser, String message)
			throws ClientProtocolException, IOException,
			ParserConfigurationException, SAXException {

		final String random = "" + randomGenerator.nextLong();

		String xmlResponse = post(String.format(SEND_MESSAGE_XML, random,
				UtilesXml.escape(fromUser), DEFAULT_PASSWORD,
				UtilesXml.escape(toUser), UtilesXml.escape(message)));

		if (debug)
			Log.d("sendMessage()", "XML response: " + xmlResponse);

		/*
		 * <?xml version="1.0" encoding="UTF-8"?><result type="success"
		 * id="4880064370372774440"></result>
		 */

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(new ByteArrayInputStream(xmlResponse
				.getBytes("UTF-8")));

		NodeList results = doc.getElementsByTagName("result");
		Node result = results.item(0);
		String type = result.getAttributes().getNamedItem("type")
				.getTextContent();

		if ("success".equals(type)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Devuelve lista de mensajes.
	 * 
	 * @return null si se produce un error
	 */
	public List<MensajeWeb> getAllMessages(String forUser)
			throws ClientProtocolException, IOException,
			ParserConfigurationException, SAXException {

		return getAllMessages(forUser, "01/01/1970 00:00:00");

	}

	/**
	 * Devuelve lista de mensajes.
	 * 
	 * @return null si se produce un error
	 */
	public List<MensajeWeb> getAllMessages(String forUser, String timestamp)
			throws ClientProtocolException, IOException,
			ParserConfigurationException, SAXException {

		final String random = "" + randomGenerator.nextLong();

		String xmlResponse = post(String.format(GET_MESSAGES_XML, random,
				UtilesXml.escape(forUser), DEFAULT_PASSWORD, timestamp));

		if (debug)
			Log.d("getAllMessages()", "XML response: " + xmlResponse);

		/*
		 * <result type="success"
		 * id="870274628232307897"><messages-list><message
		 * timestamp="12/11/2013 15:41:16" from="user1_4606"><![CDATA[Hola, como
		 * va?]]></message></messages-list></result>
		 */

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(new ByteArrayInputStream(xmlResponse
				.getBytes("UTF-8")));

		NodeList results = doc.getElementsByTagName("result");
		Node result = results.item(0);
		String type = result.getAttributes().getNamedItem("type")
				.getTextContent();

		if (!"success".equals(type)) {
			Log.e("getAllMessages()",
					"La busqueda de mensajes devolvio error: " + xmlResponse);
			return null;
		}

		NodeList messages = doc.getElementsByTagName("message");
		List<MensajeWeb> list = new ArrayList<MensajeWeb>(messages.getLength());
		for (int i = 0; i < messages.getLength(); i++) {
			Node message = messages.item(i);
			NamedNodeMap attrs = message.getAttributes();
			Node fromAttr = attrs.getNamedItem("from");
			Node timestampAttr = attrs.getNamedItem("timestamp");
			list.add(new MensajeWeb(fromAttr.getNodeValue(), timestampAttr
					.getNodeValue(), message.getTextContent()));
		}

		return list;

	}

}
