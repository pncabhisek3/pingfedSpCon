package com.cisco.pmtpf.server.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileuploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getFileUploadPage() {
		return "fileupload/fileupload";
	}
}
