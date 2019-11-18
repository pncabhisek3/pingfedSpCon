package com.cisco.pmtpf.server.commons;

import java.text.MessageFormat;

public enum ErrorCodeEnum {

	Auth_Token_Missing_Invalid(1, "Auth-Token is missing or invalid!"),
	Auth_Token_Expired(5, "Auth-Token is expired. Please login again."),
	Invalid_Auth_Credentials(10, "Invalid User-Id and/or Password "),
	Not_Authorized(20, "You are not authorized for: {0}")

	, Invalid_Request(100, "Invalid request: {0}") // whole input is invalid (wrong format/un-parseable etc)
	, Invalid_Value(105, "Invalid value: {0}") // only some input value is invalid (wrong format/un-parseable etc)

	, Required_Parameter_Invalid(200, "Required parameter Invalid/missing: {0}")

	, No_Data_Exists(300, "No data exist with value: {0}.")

	, Internal_Server_Error(500, "Internal error occurred. Please try again later.")

	, Custom_Code(100000, "{0}");

	private final int errCode;
	private final String description;

	private ErrorCodeEnum(int errCode, String description) {
		this.errCode = errCode;
		this.description = description;
	}

	public int getErrCode() {
		return errCode;
	}

	public String getDescription() {
		return description.replaceAll("(\\{\\d+\\})", "");
	}

	public String getDescription(Object... placeHolders) {
		return MessageFormat.format(description, placeHolders);
	}

}
