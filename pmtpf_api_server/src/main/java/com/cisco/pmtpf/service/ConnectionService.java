package com.cisco.pmtpf.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cisco.pmtpf.server.dao.BlobDocumentDao;
import com.cisco.pmtpf.server.dao.ConnectionFlatFileDAO;
import com.cisco.pmtpf.server.model.Attributes;
import com.cisco.pmtpf.server.model.BlobDocument;
import com.cisco.pmtpf.server.model.Connection;
import com.cisco.pmtpf.server.model.Subject;
import com.cisco.pmtpf.server.utils.X509CertificateUtils;

@Service
public class ConnectionService {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);

	@Autowired
	private ConnectionFlatFileDAO dao;

	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity;

	@Value("${Ping.Dev.Cert.Id}")
	private String CertId;

	@Value("${Ping.nameFormat.basic}")
	private String nameFormatbasic;

	@Value("${Ping.nameFormat.undefined}")
	private String nameFormatundefined;

	@Value("${Ping.LDAP.dsxdev.Id}")
	private String dsxdevid;

	@Value("${Ping.Dev.authnpolicycontract.standard.id}")
	private String authnpolicystandardcontractid;

	@Value("${pmtpfStandard.JSON.filepath}")
	private String pmtpfStandard;

	@Value("${pmtpfSLO.JSON.filepath}")
	private String pmtpfSLO;

	@Autowired
	BlobDocumentDao<BlobDocument> docDao;

	public ResponseEntity<String> getConnection(String pfid) {

		headers.add("X-Xsrf-Header", "PingFederate");
		headers.add("Authorization", "Basic cGluZ2ZlZGF1dG8uZ2VuOlBpbmdmZWRhdXRvQDEyMw==");
		headers.add("Content-Type", "application/json");

		entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(
					"https://pingfed-idev3-01.cisco.com:9999/pf-admin-api/v1/idp/spConnections/" + pfid, HttpMethod.GET,
					entity, String.class);
			logger.info(response.toString());
			return response;
		} catch (HttpStatusCodeException ex) {
			System.out.println(ex.getResponseBodyAsString());
			return new ResponseEntity<String>(ex.getResponseBodyAsString(), null, HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.NOT_FOUND);

		}

	}

	public ResponseEntity<String> getConnections() {
		headers.add("X-Xsrf-Header", "PingFederate");
		headers.add("Authorization", "Basic cGluZ2ZlZGF1dG8uZ2VuOk15c3dlZXRmYW1pbHlAMTIz");
		headers.add("Content-Type", "application/json");
		entity = new HttpEntity<String>(headers);

		try {
			// This to call pingfed api to create connection.
			ResponseEntity<String> response = restTemplate.exchange(
					"https://pingfed-idev3-01.cisco.com:9999/pf-admin-api/v1/idp/spConnections", HttpMethod.GET, entity,
					String.class);
			return response;
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<String>(ex.getResponseBodyAsString(), null, HttpStatus.BAD_REQUEST);
		} catch (RestClientException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * This to call pingfed api to create connection.
	 */
	public ResponseEntity<String> createConnection(JSONObject jsonobj) {

		headers.add("X-Xsrf-Header", "PingFederate");
		headers.add("Authorization", "Basic cGluZ2ZlZGF1dG8uZ2VuOk15c3dlZXRmYW1pbHlAMTIz");
		headers.add("Content-Type", "application/json");
		entity = new HttpEntity<String>(jsonobj.toString(), headers);
		try {
			// This to call pingfed api to create connection.
			ResponseEntity<String> response = restTemplate.exchange(
					"https://pingfed-idev3-01.cisco.com:9999/pf-admin-api/v1/idp/spConnections", HttpMethod.POST,
					entity, String.class);
			System.out.println("Response :" + response.toString());
			return response;
		} catch (HttpStatusCodeException ex) {
			System.out.println(ex.getResponseBodyAsString());
			return new ResponseEntity<String>(ex.getResponseBodyAsString(), null, HttpStatus.BAD_REQUEST);
		} catch (RestClientException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.BAD_REQUEST);

		}
	}

	public ResponseEntity<String> updateConnection(JSONObject jsonobj, String pfid) {

		headers.add("X-Xsrf-Header", "PingFederate");
		headers.add("Authorization", "Basic cGluZ2ZlZGF1dG8uZ2VuOk15c3dlZXRmYW1pbHlAMTIz");
		headers.add("Content-Type", "application/json");
		entity = new HttpEntity<String>(jsonobj.toString(), headers);
		System.out.println(pfid);
		System.out.println(jsonobj.toString());
		try {
			ResponseEntity<String> response = restTemplate.exchange(
					"https://pingfed-idev3-01.cisco.com:9999/pf-admin-api/v1/idp/spConnections/" + pfid, HttpMethod.PUT,
					entity, String.class);
			System.out.println("Response :" + response.toString());
			return response;
		} catch (HttpStatusCodeException ex) {
			System.out.println(ex.getResponseBodyAsString());
			return new ResponseEntity<String>(ex.getResponseBodyAsString(), null, HttpStatus.BAD_REQUEST);
		} catch (RestClientException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.BAD_REQUEST);

		}
	}

	public ResponseEntity<String> deleteConnection(String pfid) {

		headers.add("X-Xsrf-Header", "PingFederate");
		headers.add("Authorization", "Basic cGluZ2ZlZGF1dG8uZ2VuOk15c3dlZXRmYW1pbHlAMTIz");
		headers.add("Content-Type", "application/json");

		entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(
					"https://pingfed-idev3-01.cisco.com:9999/pf-admin-api/v1/idp/spConnections/" + pfid,
					HttpMethod.DELETE, entity, String.class);
			System.out.println(response.toString());
			return response;
		} catch (HttpStatusCodeException ex) {
			System.out.println(ex.getResponseBodyAsString());
			return new ResponseEntity<String>(ex.getResponseBodyAsString(), null, HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.NOT_FOUND);

		}

	}

	/**
	 * 
	 * Prepare final json to be submitted to pingfed api.
	 */
	public JSONObject getTemplate(Connection connection, HttpMethod methodType)
			throws JSONException, FileNotFoundException, IOException, ParseException {

		JSONObject SampleBasicJson = dao.getdatafile(pmtpfStandard);
		System.out.println(SampleBasicJson);
		if (HttpMethod.PUT.equals(methodType))
			SampleBasicJson.put("id", connection.getPfid());
		SampleBasicJson.put("name", connection.getEntityName());
		SampleBasicJson.put("entityId", connection.getEntityId());
		SampleBasicJson.put("applicationName", connection.getAppPortfolio().getAppPortfolioId());
		SampleBasicJson.getJSONObject("contactInfo").put("firstName", connection.getFirstName());
		SampleBasicJson.getJSONObject("contactInfo").put("lastName", connection.getLastName());
		SampleBasicJson.getJSONObject("contactInfo").put("email", connection.getEmail());
		SampleBasicJson.getJSONObject("credentials").getJSONObject("signingSettings").getJSONObject("signingKeyPairRef")
				.put("id", CertId);
		// Start of My changes...
		SampleBasicJson.getJSONObject("credentials").getJSONObject("signingSettings").getJSONObject("signingKeyPairRef")
				.put("location", "https://pingfed-idev3-01:9999/pf-admin-api/v1/keyPairs/signing/" + CertId);
		// End of My changes...
		SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("ssoServiceEndpoints").getJSONObject(0).put("url",
				connection.getAcsUri());

		int i = 0;
		Subject SUBJECT = connection.getSubject();
		if (null != SUBJECT && connection.getSubject().getSAMLSUBJECT() != null) {
			SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
					.getJSONArray("coreAttributes").getJSONObject(0).put("name", "SAML_SUBJECT");
			SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
					.getJSONArray("coreAttributes").getJSONObject(0)
					.put("nameFormat", connection.getSubject().getNameIDFormat());
			JSONObject tempJson = new JSONObject();
			JSONObject source = new JSONObject();
			source.put("type", "TEXT");
			tempJson.put("source", source);
			tempJson.put("value", (connection.getSubject().getSAMLSUBJECT().equalsIgnoreCase("userid")) ? "${subject}"
					: "${subject}@cisco.com");
			SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("authenticationPolicyContractAssertionMappings")
					.getJSONObject(0).getJSONArray("attributeSources").getJSONObject(0)
					.getJSONObject("attributeContractFulfillment").put("SAML_SUBJECT", tempJson);
		}

		// 'temp' json is to construct final json obj. with parts of json

		Attributes attributes = connection.getAttributes();
		if (null != attributes) {
			if (attributes.getGivenname() != null) {

				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getGivenname());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "givenName");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getGivenname(), tempJson);
				i++;
			}
			if (attributes.getSn() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getSn());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "sn");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getSn(), tempJson);
				i++;
			}

			if (attributes.getMail() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getMail());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "mail");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getMail(), tempJson);
				i++;
			}
			if (attributes.getJobRole() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getJobRole());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "jobRole");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getJobRole(), tempJson);
				i++;
			}
			if (attributes.getUid() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getUid());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "uid");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getUid(), tempJson);
				i++;
			}
			if (attributes.getAccessLevel() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getAccessLevel());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "accessLevel");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getAccessLevel(), tempJson);
				i++;
			}

			if (attributes.getCompany() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getCompany());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "company");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getCompany(), tempJson);
				i++;
			}
			if (attributes.getStreet() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getStreet());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "street");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getStreet(), tempJson);
				i++;
			}
			if (attributes.getCo() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getCo());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "co");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getCo(), tempJson);
				i++;
			}
			if (attributes.getPostalCode() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getPostalCode());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "postalCode");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getPostalCode(), tempJson);
				i++;
			}
			if (attributes.getL() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getL());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "l");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getL(), tempJson);
				i++;
			}
			if (attributes.getSt() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getSt());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "st");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getSt(), tempJson);
				i++;
			}
			if (attributes.getMemberOf() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getMemberOf());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "memberOf");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getMemberOf(), tempJson);
				i++;
			}
			if (attributes.getDepartmentNumber() != null) {
				JSONObject temp = new JSONObject();
				temp.put("name", attributes.getDepartmentNumber());
				temp.put("nameFormat", nameFormatbasic);
				SampleBasicJson.getJSONObject("spBrowserSso").getJSONObject("attributeContract")
						.getJSONArray("extendedAttributes").put(i, temp);
				JSONObject tempJson = new JSONObject();
				JSONObject source = new JSONObject();
				source.put("type", "LDAP_DATA_STORE");
				tempJson.put("source", source);
				tempJson.put("value", "departmentNumber");
				SampleBasicJson.getJSONObject("spBrowserSso")
						.getJSONArray("authenticationPolicyContractAssertionMappings").getJSONObject(0)
						.getJSONArray("attributeSources").getJSONObject(0).getJSONObject("attributeContractFulfillment")
						.put(attributes.getDepartmentNumber(), tempJson);
				i++;
			}
		}

		SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("authenticationPolicyContractAssertionMappings")
				.getJSONObject(0).getJSONArray("attributeSources").getJSONObject(0).getJSONObject("dataStoreRef")
				.put("id", dsxdevid);
		// Start of My changes...
		SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("authenticationPolicyContractAssertionMappings")
				.getJSONObject(0).getJSONArray("attributeSources").getJSONObject(0).getJSONObject("dataStoreRef")
				.put("location", "https://pingfed-idev3-01:9999/pf-admin-api/v1/dataStores/" + dsxdevid);
		// End of My changes...

		SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("authenticationPolicyContractAssertionMappings")
				.getJSONObject(0).getJSONObject("authenticationPolicyContractRef")
				.put("id", authnpolicystandardcontractid);
		// Start of My changes...
		SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("authenticationPolicyContractAssertionMappings")
				.getJSONObject(0).getJSONObject("authenticationPolicyContractRef")
				.put("location", "https://pingfed-idev3-01:9999/pf-admin-api/v1/authenticationPolicyContracts/"
						+ authnpolicystandardcontractid);

		// End of My changes...

		// SPSLO Connection specific changes
		String connectionType = connection.getConnectionType();
		if (StringUtils.isNotBlank(connectionType) && connectionType.equalsIgnoreCase("spslo")) {
			JSONObject temp = new JSONObject();
			temp.put("primaryVerificationCert", true);
			temp.put("secondaryVerificationCert", false);
			JSONObject temp1 = new JSONObject();
			System.out.println(connection.getSpSloCertificate());

			// This cert is to get certificate from the 'SP Certificate X509 text format:'
			// of 'digitally signed' section.
			// TODO: Enable browse button.. instead of text area.
//			String cert = "-----BEGIN CERTIFICATE-----\n"
//					+ connection.getSpSloCertificate().replaceAll("(.{64})", "$1\n") + "\n-----END CERTIFICATE-----";

			// Covert byte[] to pem data of X509Certificate
			BlobDocument doc = connection.getSpSloCert();
			// If oldCert is present, That means file is already existing.
			if (null == doc || null == doc.getInputFileId())
				return null;
			X509Certificate _cert = null;
			String spSloCert = null;
			doc = docDao.getDoument(doc.getInputFileId());
			if (null == doc || null == doc.getInputFileId())
				return null;
			try {
				_cert = X509CertificateUtils
						.convertToX509Certificate(new ByteArrayInputStream(doc.getInputFileData()));
			} catch (CertificateException e) {
				logger.error("", e);
			}
			if (null != _cert) {
				try {
					spSloCert = X509CertificateUtils.convertToPem(_cert);
				} catch (CertificateEncodingException e) {
					logger.error("", e);
				}
			}
			temp1.put("fileData", spSloCert);

			System.out.println(temp1);
			temp.put("x509File", temp1);
			temp.put("encryptionCert", false);
			SampleBasicJson.getJSONObject("credentials").getJSONArray("certs").put(0, temp);
			SampleBasicJson.getJSONObject("credentials").getJSONObject("signingSettings")
					.getJSONObject("signingKeyPairRef").put("id", CertId);
			SampleBasicJson.getJSONObject("spBrowserSso").getJSONArray("enabledProfiles").put("SP_INITIATED_SLO");
			JSONArray temp2 = new JSONArray();
			JSONObject temp3 = new JSONObject();
			temp3.put("binding", "POST");
			temp3.put("url", connection.getSpSloServiceEndpoint());
			temp2.put(temp3);
			SampleBasicJson.getJSONObject("spBrowserSso").put("sloServiceEndpoints", temp2);
		}

		return SampleBasicJson;

	}

}
