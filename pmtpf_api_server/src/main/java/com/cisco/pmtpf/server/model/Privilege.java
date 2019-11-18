package com.cisco.pmtpf.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Privilege {

	@Id
	@JsonProperty("privilegeId")
	private Long privilegeId;
	@JsonProperty("resource")
	private Resource resource;
	@JsonProperty("action")
	private Action action;

	public enum Fields {
		privilegeId, resource, action;
	}

	public Privilege() {
	}

	public Privilege(Resource resource, Action action) {
		this.resource = resource;
		this.action = action;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "Privilege [privilegeId=" + privilegeId + ", resource=" + resource + ", action=" + action + "]";
	}

}
