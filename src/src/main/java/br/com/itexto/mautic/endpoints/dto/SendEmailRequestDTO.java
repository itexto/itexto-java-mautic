package br.com.itexto.mautic.endpoints.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.itexto.mautic.domain.Email;

public class SendEmailRequestDTO {
	
	public SendEmailRequestDTO(Email email) {
		this.tokens = new HashMap<String, String>();
		this.tokens.putAll(email.getParameters());
	}
	
	@JsonProperty("tokens")
	private Map<String, String> tokens;

	public Map<String, String> getTokens() {
		return tokens;
	}

	public void setTokens(Map<String, String> tokens) {
		this.tokens = tokens;
	}
	
	
	
}
