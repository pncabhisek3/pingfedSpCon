package com.cisco.pmtpf.server.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class PmtpfGroup {

	@Id
	@JsonProperty("groupId")
	private Long groupId;
	@JsonProperty("groupName")
	private String groupName;
	@JsonProperty("privileges")
	@DBRef
	private Set<Privilege> privileges;
	
	public enum Fields{
		groupId, groupName, privileges;
	}
	
	public enum Groups{
		admin_pmtpf;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return "PmtpfGroup [groupId=" + groupId + ", groupName=" + groupName + ", privileges=" + privileges + "]";
	}

}
