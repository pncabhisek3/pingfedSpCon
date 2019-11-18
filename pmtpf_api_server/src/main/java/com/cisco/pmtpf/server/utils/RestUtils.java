package com.cisco.pmtpf.server.utils;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestUtils {
	
	public static RestTemplate RestTemplate = new RestTemplate();

	public static ResponseEntity<?> make_GETApiCall(String url, HttpEntity<?> requestEntity, Class<?> responseType) {
		return RestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
	}

	public static ResponseEntity<?> make_POSTApiCall(String url, HttpEntity<?> requestEntity, Class<?> responseType) {
		return RestTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
	}

	public static ResponseEntity<?> make_POSTForObject(String url, Object request, Class<?> responseType,
			Map<String, ?> uriVariables) {
		return (ResponseEntity<?>) RestTemplate.postForObject(url, request, responseType, uriVariables);
	}

}
