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
package br.com.itexto.mautic;

import java.util.Base64;

import br.com.itexto.mautic.endpoints.Contacts;
import br.com.itexto.mautic.endpoints.Emails;

public class MauticClient {
	
	private final String host;
	
	private final String username;
	
	private final String password;
	
	private final String basicAuth;
	
	
	
	
	/**
	 * Create a new MauticClient
	 * @param host - The Mautic Host
	 * @param username - The Mautic API username
	 * @param password - The Mautic API password
	 */
	public MauticClient(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
		basicAuth = Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes());
	}
	
	public Contacts contacts() {
		return new Contacts(this);
	}
	
	public Emails emails() {
		return new Emails(this);
	}
	
	
	public String getHost() {
		return host;
	}
	
	public String getBasicAuth() {
		return this.basicAuth;
	}



	public String getUsername() {
		return username;
	}



	public String getPassword() {
		return password;
	}




}
