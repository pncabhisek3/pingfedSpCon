package com.cisco.pmtpf.server.model;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection= "blobDocumentData")
public class BlobDocumentData implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("docData")
	private byte[] docData;

	@JsonProperty("docName")
	private String docName;

	@Id
	@JsonProperty("docId")
	private long docId;

	public byte[] getDocData() {
		return docData;
	}

	public void setDocData(byte[] docData) {
		this.docData = docData;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	@Override
	public String toString() {
		return "BlobDocumentData [docData=" + Arrays.toString(docData) + ", docName=" + docName + ", docId=" + docId
				+ "]";
	}

}
