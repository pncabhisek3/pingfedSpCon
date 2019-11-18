package com.cisco.pmtpf.server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

	@JsonProperty("userId")
	private Long userId;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("groupName")
	private String groupName;
	@JsonProperty("distinguishedName")
	private String distinguishedName;

	private final static long serialVersionUID = 3828340162948032635L;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

}