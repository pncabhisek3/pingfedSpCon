package com.cisco.pmtpf.server.commons;

public class ApiConstants {

	public static final String ApplicationWithEntityId = "/applications.json/{entityID}";
	public static final String GetApplications = "/applications.json";
	public static final String PingFed_Connection = "/connections.json";
	public static final String PingFed_ConnectionWithId = "/connections.json/{entityID}";
	public static final String spSloEndpoint = "/sp/logout.json";

	public static final String ValidateResource = "/validateResource.json/{resourceId}/{resourceType}";
	public static final String ESPValidateAppPortfolio = "/validatePortfolio.json/{portfolioName}";

	public static final String BlobDocumentData = "/document/document.json";
	public static final String BlobDocumentDataWithId = "/document/document.json/{documentId}";
}
