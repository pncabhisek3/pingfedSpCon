package com.cisco.pmtpf.server.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class CounterReference {

	@Id
	@JsonProperty("referenceId")
	private Long referenceId;
	@JsonProperty("counterMap")
	private Map<String, Sequence> counterMap;

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Map<String, Sequence> getCounterMap() {
		return counterMap;
	}

	public void setCounterMap(Map<String, Sequence> counterMap) {
		this.counterMap = counterMap;
	}

}
