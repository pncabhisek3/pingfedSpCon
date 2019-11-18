package com.cisco.pmtpf.server.model;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class BlobDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("inputFileId")
	private Long inputFileId;
	@JsonProperty("staleInputFileId")
	private Long staleInputFileId;
	@JsonProperty("inputFileName")
	private String inputFileName;
	@JsonProperty("inputFileOriginalName")
	private String inputFileOriginalName;
	@JsonProperty("inputFileType")
	private String inputFileType;
	@JsonProperty("inputFileData")
	private byte[] inputFileData;

	public enum Fields {
		inputFileId, inputFileName;
	}

	public String getInputFileOriginalName() {
		return inputFileOriginalName;
	}

	public void setInputFileOriginalName(String inputFileOriginalName) {
		this.inputFileOriginalName = inputFileOriginalName;
	}

	public Long getInputFileId() {
		return inputFileId;
	}

	public void setInputFileId(Long inputFileId) {
		this.inputFileId = inputFileId;
	}

	public Long getStaleInputFileId() {
		return staleInputFileId;
	}

	public void setStaleInputFileId(Long staleInputFileId) {
		this.staleInputFileId = staleInputFileId;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getInputFileType() {
		return inputFileType;
	}

	public void setInputFileType(String inputFileType) {
		this.inputFileType = inputFileType;
	}

	public byte[] getInputFileData() {
		return inputFileData;
	}

	public void setInputFileData(byte[] inputFileData) {
		this.inputFileData = inputFileData;
	}

	public static String getExtension(String fileName) {
		if (StringUtils.isBlank(fileName))
			return null;
		int i = fileName.lastIndexOf('.');
		if (i > 0)
			return fileName.substring(i + 1);
		return null;
	}

	@Override
	public String toString() {
		return "BlobDocument [inputFileId=" + inputFileId + ", staleInputFileId=" + staleInputFileId
				+ ", inputFileName=" + inputFileName + ", inputFileOriginalName=" + inputFileOriginalName
				+ ", inputFileType=" + inputFileType + ", inputFileData=" + Arrays.toString(inputFileData) + "]";
	}

}
