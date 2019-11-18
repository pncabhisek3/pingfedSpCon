package com.cisco.pmtpf.server.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

public interface ConnectionFlatFileDAO {

	public JSONObject getdatafile(String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException;

	public int getlastappindex(String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException;

	public String getpfid(int appid, String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException;

}