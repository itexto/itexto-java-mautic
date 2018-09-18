package br.com.itexto.mautic.endpoints;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.itexto.mautic.MauticClient;
import br.com.itexto.mautic.MauticError;
import br.com.itexto.mautic.domain.Email;
import br.com.itexto.mautic.endpoints.dto.SendEmailRequestDTO;

public class Emails extends BasicEndpoint {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Send an e-mail, to the contact identified by idContact
	 * @param idContact - id of the contact
	 * @param email - template e-mail with tokens, to be sent
	 * @return true if successfully sent
	 */
	public boolean sendToContact(long idContact, Email email) throws MauticError {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(this.getClient().getHost() + "/api/emails/" + email.getId() + "/contact/" + idContact + "/send");
			System.out.println("URL: " + url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Authorization", "Basic " + getClient().getBasicAuth());
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("POST");
			
			
			SendEmailRequestDTO request = new SendEmailRequestDTO(email);
			String content = mapper.writeValueAsString(request);
			System.out.println(content);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			
			writer.write(content);
			writer.flush();
			writer.close();
			Map result = (Map) mapper.readValue(connection.getInputStream(), Map.class);
			
			if (result.containsKey("success")) {
				return Boolean.parseBoolean(result.get("success").toString());
			}
		} catch (IOException ex) {
			byte[] buffer = new byte[65536];
			int c = -1;
			try {
				
				c = connection.getErrorStream().read(buffer);
				System.out.println("Erro: " + new String(buffer, 0, c));
			} catch (Throwable t) {
				throw new MauticError("Error getting error message", t);
			}
			throw new MauticError("Error accessing server: " + new String(buffer, 0, c), ex);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		return false;
	}
	
	public Emails(MauticClient client) {
		super(client);
	}

}
