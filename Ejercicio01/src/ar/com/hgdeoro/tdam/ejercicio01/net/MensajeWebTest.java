package ar.com.hgdeoro.tdam.ejercicio01.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class MensajeWebTest {

	@Test
	public void test() throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:8080/messages");
		HttpResponse response = client.execute(post);
		response.getStatusLine().toString();
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					instream));
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}

			instream.close();
		}

	}

}
