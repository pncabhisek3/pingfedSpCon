package com.cisco.pmtpf.server.rest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cisco.pmtpf.server.commons.ApiConstants;
import com.cisco.pmtpf.server.commons.ApiResponse;
import com.cisco.pmtpf.server.commons.ErrorCodeEnum;
import com.cisco.pmtpf.server.dao.BlobDocumentDao;
import com.cisco.pmtpf.server.model.BlobDocument;
import com.cisco.pmtpf.server.utils.JSonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class BlobDocumentController {

	private static final Logger logger = LoggerFactory.getLogger(BlobDocumentController.class);

	@Autowired
	BlobDocumentDao<BlobDocument> docDao;

	@SuppressWarnings("deprecation")
	@PostMapping(value = ApiConstants.BlobDocumentData, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> uploadBlobDocument(@RequestParam("qquuid") String qquuid,
			@RequestParam("qqfilename") String qqfilename, @RequestParam("qqtotalfilesize") String qqtotalfilesize,
			@RequestPart("qqfile") MultipartFile file2upload) {
		if (null == file2upload)
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.No_Data_Exists.toString()),
					HttpStatus.NOT_ACCEPTABLE);

		String fileUploadResp = null;
		try {
			if (file2upload.getBytes().length == 0)
				return new ResponseEntity<Object>(
						new ApiResponse<Object>(false, ErrorCodeEnum.No_Data_Exists.toString()),
						HttpStatus.NOT_ACCEPTABLE);
		} catch (IOException e) {
			logger.error("", e);
			fileUploadResp = getJsonString(generateFileUploadStatus(false, "File not uploaded."));
			return new ResponseEntity<Object>(fileUploadResp, HttpStatus.NOT_ACCEPTABLE);
		}
		
		BlobDocument doc = new BlobDocument();
		doc.setInputFileName(qqfilename);
		doc.setInputFileOriginalName(file2upload.getOriginalFilename());
		doc.setInputFileType(BlobDocument.getExtension(file2upload.getOriginalFilename()));
		try {
			doc.setInputFileData(file2upload.getBytes());
		} catch (IOException e) {
			logger.error("Unable to get bytes from multipartFile.", e);
			fileUploadResp = getJsonString(generateFileUploadStatus(false, "File not uploaded."));
			return new ResponseEntity<Object>(fileUploadResp, HttpStatus.NOT_ACCEPTABLE);
		}

		doc = docDao.saveDocument(doc);
		logger.info("New Document uploaded");
//		doc.setInputFileData(null);

		if (doc.getInputFileId() > 0) {
			Map<String, Object> map = generateFileUploadStatus(true, null);
			map.put("data", doc);
			fileUploadResp = getJsonString(map);
		}
		return new ResponseEntity<Object>(fileUploadResp, HttpStatus.OK);
	}

	@GetMapping(value = ApiConstants.BlobDocumentDataWithId, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> getBlobDocument(@PathVariable("documentId") Long documentId) {
		if (null == documentId) {
			logger.debug("Path variable on getBlobDocument() is ", documentId);
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.Invalid_Value.toString()),
					HttpStatus.NOT_ACCEPTABLE);
		}

		BlobDocument document = docDao.getDoument(documentId);
		logger.debug("Doucment found by id" + documentId + " is:", document);
		return new ResponseEntity<Object>(new ApiResponse<BlobDocument>(document), HttpStatus.OK);
	}

	@SuppressWarnings("deprecation")
	@DeleteMapping(value = ApiConstants.BlobDocumentDataWithId, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<Object> deleteDocument(@PathVariable("documentId") Long documentId) {
		if (null == documentId) {
			logger.debug("Path variable on getBlobDocument() is ", documentId);
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.Invalid_Value.toString()),
					HttpStatus.NOT_ACCEPTABLE);
		}

		if (docDao.deleteDocument(documentId)) {
			logger.debug("Document delete successful by document id:", documentId);
			return new ResponseEntity<Object>(new ApiResponse<BlobDocument>(null), HttpStatus.OK);
		} else {
			logger.debug("Failed to delete document by document id:", documentId);
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.Not_Authorized.toString()),
					HttpStatus.FORBIDDEN);
		}
	}

	private Map<String, Object> generateFileUploadStatus(boolean isSuccess, String msg) {
		Map<String, Object> uploadResp = new HashMap<String, Object>();
		uploadResp.put("success", isSuccess);
		if (!isSuccess) {
			uploadResp.put("error", StringUtils.isBlank(msg) ? "error" : msg);
			uploadResp.put("preventRetry", true);
		}
		return uploadResp;
	}

	private String getJsonString(Map<String, Object> obj) {
		String data = null;
		try {
			data = JSonUtils.object2JsonString(obj);
		} catch (JsonProcessingException e) {
			logger.error("", e);
		}
		return data;
	}
}
