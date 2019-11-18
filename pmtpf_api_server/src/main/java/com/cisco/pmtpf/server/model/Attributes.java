
package com.cisco.pmtpf.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attributes {

	@JsonProperty("givenname")
	private String givenname;
	@JsonProperty("sn")
	private String sn;
	@JsonProperty("mail")
	private String mail;
	@JsonProperty("jobRole")
	private String jobRole;
	@JsonProperty("uid")
	private String uid;
	@JsonProperty("accessLevel")
	private String accessLevel;
	@JsonProperty("company")
	private String company;
	@JsonProperty("street")
	private String street;
	@JsonProperty("co")
	private String co;
	@JsonProperty("postalCode")
	private String postalCode;
	@JsonProperty("l")
	private String l;
	@JsonProperty("st")
	private String st;
	@JsonProperty("memberOf")
	private String memberOf;
	@JsonProperty("departmentNumber")
	private String departmentNumber;
	@JsonProperty("cn")
	private String cn;

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(String memberOf) {
		this.memberOf = memberOf;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public static Attributes getEmptyObject() {
		return new Attributes();
	}

}
