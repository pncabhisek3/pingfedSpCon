package com.cisco.pmtpf.server.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

public class Base64Utils {

	public static String encode(String cred) {
		if (StringUtils.isBlank(cred))
			return null;
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(cred.getBytes(StandardCharsets.UTF_8));
		return encodedString;
	}

	public static String decode(String encodedStr) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(encodedStr);
		return new String(decodedByteArray);
	}
	
	public static void main(String[] args) {
		System.out.println(Base64Utils.decode("TXlzd2VldGZhbWlseUAxMjM="));
	}

}
