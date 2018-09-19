/*******************************************************************************
 * Copyright 2018 itexto Consultoria
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package br.com.itexto.mautic.endpoints;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.itexto.mautic.MauticClient;
import br.com.itexto.mautic.MauticError;
import br.com.itexto.mautic.domain.Contact;

public class Contacts extends BasicEndpoint {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	
	
	public Contact create(Contact contact) throws MauticError {
		Contact result = null;
		if (contact != null) {
			String address = getClient().getHost() + "/api/contacts/new";
			
			try {
				URL url = new URL(address);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("Authorization", "Basic " + getClient().getBasicAuth());
				connection.setRequestProperty("Content-type", "application/json");
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(mapper.writeValueAsString(contact));
				writer.close();
				Map content = (Map) mapper.readValue(connection.getInputStream(), Map.class);
				if (content.containsKey("contact")) {
					return parseContact((Map) content.get("contact"));
				}
			} catch (IOException e) {
				throw new MauticError("Error creating contact", e);
			}
		}
		return result;
	}
	
	public List<Contact> searchByEmail(String email) throws MauticError {
		List<Contact> result = new ArrayList<Contact>();
		if (email != null) {
			String address = getClient().getHost() + "/api/contacts?search=" + email;
			URL url = null;
			try {
				url = new URL(address);
			} catch (MalformedURLException e) {
				throw new MauticError("Invalid URL: [" + address + "]", e);
			}
			
			HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				throw new MauticError("Error openning URL: [" + address + "]", e);
			}
			try {
				connection.setRequestMethod("GET");
				
			} catch (ProtocolException ex) {
				throw new MauticError("Protocol error", ex);
			}
			connection.setRequestProperty("Authorization", "Basic " + getClient().getBasicAuth());
			
			try {
				Map content = mapper.readValue(connection.getInputStream(), Map.class);
				if (content.containsKey("total") && content.containsKey("contacts")) {
					Long total = Long.parseLong(content.get("total").toString());
					if (total > 0) {
						Map contacts = (Map) content.get("contacts");
						result = parseContacts(contacts);
					}
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Contact parseContact(Map map) {
		Contact contact = new Contact();
		contact.setId(Long.parseLong(map.get("id").toString()));
		Map fields = (Map) map.get("fields");
		Map allFields = (Map) fields.get("all");
		contact.setFirstName(parseField(allFields, "firstname"));
		contact.setLastName(parseField(allFields, "lastname"));
		contact.setEmail(parseField(allFields, "email"));
		
		return contact;
	}
	
	private String parseField(Map map, String field) {
		return map != null && map.containsKey(field) ? map.get(field).toString() : null;
	}
	
	private List<Contact> parseContacts(Map map) {
		List<Contact> result = new ArrayList<Contact>();
		for (Object key : map.keySet()) {
			Map contactItem = (Map) map.get(key);
			
			Contact contact = parseContact(contactItem);
			
			result.add(contact);
		}
		return result;
	}
	
	public Contacts(MauticClient client) {
		super(client);
	}

}
