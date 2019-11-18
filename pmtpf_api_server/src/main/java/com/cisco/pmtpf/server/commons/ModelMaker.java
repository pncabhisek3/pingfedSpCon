package com.cisco.pmtpf.server.commons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.cisco.pmtpf.server.model.Connection;
import com.cisco.pmtpf.server.utils.JSonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ModelMaker {

	private static String JSLocation = "";
	static {
		JSLocation = new File(".").getAbsolutePath() + "/src/main/webapp/pmtpfService/js/constants/";
	}

	public static void main(String[] args) throws IOException {
		writeIntoFile(JSLocation, "model.js", createModelsJs());
//		writeIntoFile(JSLocation, "enums.js", createEnumsJs());
	}

	private static String createEnumsJs() {
		return null;
	}

	private static void writeIntoFile(String location, String filename, String content) throws IOException {
		File f = new File(location, filename);
		if (f.exists())
			f.delete();
		f.createNewFile();
		PrintWriter out = new PrintWriter(f);
		out.write(content);
		out.close();
		System.out.println(filename + " is modified. Refresh your application to see new changes.");
	}

	private static String createModelsJs() throws JsonProcessingException {
		StringBuilder sb = new StringBuilder();
		sb.append("var connectionObject=").append(JSonUtils.object2JsonString(Connection.getEmptyObject()))
				.append(";\r");

		return sb.toString();
	}

}
