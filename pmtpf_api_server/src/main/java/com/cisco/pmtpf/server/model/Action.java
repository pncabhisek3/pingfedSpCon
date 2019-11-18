package com.cisco.pmtpf.server.model;

public enum Action {

	READ("Only view/read is allowed"), 
	WRITE("Only edit/write is allowed"), 
	EXECUTE("Both read and write is allowed");

	private String description;

	private Action(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
