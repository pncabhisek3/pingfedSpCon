package com.cisco.pmtpf.server.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)

@Document(collection = "Connections")
public class Connection implements Serializable {

	@JsonProperty("appid")
	private Long appid;
	@Id
	@JsonProperty("pfid")
	private String pfid;
	@JsonProperty("active")
	private Boolean isActive = true;
	@JsonProperty("entityName")
	private String entityName;
	@JsonProperty("entityId")
	private String entityId;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("acsUri")
	private String acsUri;
	@JsonProperty("connectionType")
	private String connectionType;
	@JsonProperty("applicationName")
	private String appName;
	@JsonProperty("signAssertion")
	private Boolean signatureAssertion;
	@JsonProperty("signAuthReq")
	private Boolean signatureAuthRequest;
	@JsonProperty("spSLOcert")
	private String spSloCertificate;
	@JsonProperty("spSLOServiceEndpoint")
	private String spSloServiceEndpoint;

	@DBRef
	@JsonProperty("spSloCert")
	private BlobDocument spSloCert;

	@JsonProperty("connectionMode")
	private String connectionMode;

	@JsonProperty("appPortfolio")
	private ApplicationPortfolio appPortfolio;

	@JsonProperty("owners")
	private List<Attributes> owners;

	@JsonProperty("groups")
	private List<Attributes> groups;

	@JsonProperty("attributes")
	private Attributes attributes;

	@JsonProperty("subject")
	private Subject subject;
	private final static long serialVersionUID = 7427316936732131461L;

	public BlobDocument getSpSloCert() {
		return spSloCert;
	}

	public void setSpSloCert(BlobDocument spSloCert) {
		this.spSloCert = spSloCert;
	}

	public ApplicationPortfolio getAppPortfolio() {
		return appPortfolio;
	}

	public void setAppPortfolio(ApplicationPortfolio appPortfolio) {
		this.appPortfolio = appPortfolio;
	}

	public String getConnectionMode() {
		return connectionMode;
	}

	public void setConnectionMode(String connectionMode) {
		this.connectionMode = connectionMode;
	}

	public Long getAppid() {
		return appid;
	}

	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAcsUri() {
		return acsUri;
	}

	public void setAcsUri(String acsUri) {
		this.acsUri = acsUri;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Boolean getSignatureAssertion() {
		return signatureAssertion;
	}

	public void setSignatureAssertion(Boolean signatureAssertion) {
		this.signatureAssertion = signatureAssertion;
	}

	public Boolean getSignatureAuthRequest() {
		return signatureAuthRequest;
	}

	public void setSignatureAuthRequest(Boolean signatureAuthRequest) {
		this.signatureAuthRequest = signatureAuthRequest;
	}

	public String getSpSloCertificate() {
		return spSloCertificate;
	}

	public void setSpSloCertificate(String spSloCertificate) {
		this.spSloCertificate = spSloCertificate;
	}

	public String getSpSloServiceEndpoint() {
		return spSloServiceEndpoint;
	}

	public void setSpSloServiceEndpoint(String spSloServiceEndpoint) {
		this.spSloServiceEndpoint = spSloServiceEndpoint;
	}

	public List<Attributes> getOwners() {
		return owners;
	}

	public void setOwners(List<Attributes> owners) {
		this.owners = owners;
	}

	public List<Attributes> getGroups() {
		return groups;
	}

	public void setGroups(List<Attributes> groups) {
		this.groups = groups;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public static Connection getEmptyObject() {
		Connection con = new Connection();
		con.setSubject(Subject.getEmptyObject());
		con.setAttributes(Attributes.getEmptyObject());
		con.setAppPortfolio(ApplicationPortfolio.getEmptyObject());
		return con;
	}

	@Override
	public String toString() {
		return "Connection [appid=" + appid + ", pfid=" + pfid + ", isActive=" + isActive + ", entityName=" + entityName
				+ ", entityId=" + entityId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", acsUri=" + acsUri + ", connectionType=" + connectionType + ", appName=" + appName
				+ ", signatureAssertion=" + signatureAssertion + ", signatureAuthRequest=" + signatureAuthRequest
				+ ", spSloCertificate=" + spSloCertificate + ", spSloServiceEndpoint=" + spSloServiceEndpoint
				+ ", spSloCert=" + spSloCert + ", connectionMode=" + connectionMode + ", appPortfolio=" + appPortfolio
				+ ", owners=" + owners + ", groups=" + groups + ", attributes=" + attributes + ", subject=" + subject
				+ "]";
	}

}