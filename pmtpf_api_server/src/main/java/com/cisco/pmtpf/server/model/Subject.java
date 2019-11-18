
package com.cisco.pmtpf.server.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "SAML_SUBJECT", "nameID_Format" })
public class Subject {

	@JsonProperty("SAML_SUBJECT")
	private String sAMLSUBJECT;
	@JsonProperty("nameID_Format")
	private String nameIDFormat;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Subject() {
	}

	public Subject(String sAMLSUBJECT, String nameIDFormat) {
		super();
		this.sAMLSUBJECT = sAMLSUBJECT;
		this.nameIDFormat = nameIDFormat;
	}

	@JsonProperty("SAML_SUBJECT")
	public String getSAMLSUBJECT() {
		return sAMLSUBJECT;
	}

	@JsonProperty("SAML_SUBJECT")
	public void setSAMLSUBJECT(String sAMLSUBJECT) {
		this.sAMLSUBJECT = sAMLSUBJECT;
	}

	@JsonProperty("nameID_Format")
	public String getNameIDFormat() {
		return nameIDFormat;
	}

	@JsonProperty("nameID_Format")
	public void setNameIDFormat(String nameIDFormat) {
		this.nameIDFormat = nameIDFormat;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public static Subject getEmptyObject() {
		return new Subject();
	}

}
