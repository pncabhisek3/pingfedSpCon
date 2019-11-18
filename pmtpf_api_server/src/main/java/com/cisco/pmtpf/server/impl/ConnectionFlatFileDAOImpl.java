package com.cisco.pmtpf.server.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.cisco.pmtpf.server.dao.ConnectionFlatFileDAO;

@Repository
public class ConnectionFlatFileDAOImpl implements ConnectionFlatFileDAO {

	/**
	 * Read json file and return json object.
	 */
	public JSONObject getdatafile(String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException {
		JSONParser parser = new JSONParser();

		File file = ResourceUtils.getFile(filePath);
		Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
		JSONObject jsonObject = new JSONObject(obj.toString());
		return jsonObject;
	}

	public int getlastappindex(String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException {

		int index = 0;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(filePath));
		JSONObject jsonobj = new JSONObject(obj.toString());
		JSONArray jsonarray = jsonobj.getJSONArray("Apps");
		for (int i = 0; i < jsonarray.length(); i++) {
			System.out.println(i);
			JSONObject jsonapp = jsonarray.getJSONObject(i);
			System.out.println(jsonapp.toString());
			if (index < Integer.parseInt(jsonapp.get("appid").toString())) {
				index = Integer.parseInt(jsonapp.get("appid").toString());
			}
		}

		return index;
	}

	public String getpfid(int appid, String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException {

		String pfid = "";
		// Object obj = new JSONParser().parse(new
		// FileReader("C:\\Users\\nkaggina\\Desktop\\pmtpf.json"));
		Object obj = new JSONParser().parse(new FileReader(filePath));
		JSONArray jsonarray = new JSONObject(obj.toString()).getJSONArray("Apps");
		for (int i = 0; i < jsonarray.length(); i++) {
			// System.out.println(i);
			JSONObject jsonapp = jsonarray.getJSONObject(i);
			if (appid == Integer.parseInt(jsonapp.get("appid").toString())) {
				pfid = jsonapp.getString(pfid);
			}

		}
		return pfid;

	}

	public void updatedatafile(JSONObject pmtpf, String filePath)
			throws FileNotFoundException, IOException, ParseException, JSONException {

		String obj = pmtpf.toString();
		// FileWriter writer = new
		// FileWriter("C:\\Users\\nkaggina\\Desktop\\pmtpf.json");
		FileWriter writer = new FileWriter(filePath);
		writer.write(obj);
		writer.flush();
		writer.close();
	}

}
