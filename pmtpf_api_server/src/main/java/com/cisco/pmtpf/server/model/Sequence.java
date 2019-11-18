package com.cisco.pmtpf.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sequence {

	@JsonProperty("_id")
	public String _id;
	@JsonProperty("sequence")
	private Long sequence;
	
	public enum Field{
		_id, sequence;
	}

	public Sequence() {
	}

	public Sequence(String _id, Long sequence) {
		super();
		this._id = _id;
		this.sequence = sequence;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Sequence [_id=" + _id + ", sequence=" + sequence + "]";
	}

}
