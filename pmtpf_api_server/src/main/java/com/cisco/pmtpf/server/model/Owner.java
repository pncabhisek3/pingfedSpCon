package com.cisco.pmtpf.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cisco.pmtpf.server.model.PmtpfGroup.Fields;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cecid", "apps" })

@Document(collection = "Owners")
public class Owner {

	@Id
	@JsonProperty("ownerId")
	private Long ownerId;

	@JsonProperty("ownerName")
	private String ownerName;

	public enum Fields {
		ownerId, ownerName;
	}

	@JsonProperty("isAdmin")
	private Boolean isAdmin = false;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Owners [ownerId=" + ownerId + ", ownerName=" + ownerName + ", isAdmin=" + isAdmin + "]";
	}

}