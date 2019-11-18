package com.cisco.pmtpf.server.model;

public enum Resource {

	SP_Connection("Service Provider Connection")
	
	,All("*");

	private String description;

	private Resource(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
