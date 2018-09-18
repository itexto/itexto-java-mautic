package br.com.itexto.mautic.domain;

import java.util.HashMap;
import java.util.Map;

public class Email {
	
	private long id;
	
	private Map<String, String> parameters;
	
	public void addParameter(String code, String value) {
		this.getParameters().put(code, value);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<String, String> getParameters() {
		if (this.parameters == null) {
			this.parameters = new HashMap<String, String>();
		}
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	

}
