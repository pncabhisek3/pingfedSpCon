package com.cisco.pmtpf.server.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * @author abhisekm
 */
public class PropertiesUtils {

	/***
	 * Read properties file and get the corresponding Properties object.
	 * 
	 * @param fileName
	 * @return Properties object
	 */
	public static Properties readFile(final String fileName) {
		if (StringUtils.isBlank(fileName))
			throw new IllegalArgumentException("File name is mandatory");
		InputStream is = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(fileName);
			p.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return p;
	}


	/**
	 * 
	 * Write the updated properties associated to a Properties object into the
	 * properties file.
	 * 
	 * @param fileName be the properties file name
	 * @param prop     be the Properties object to write
	 * @return Properties object which is written into the properties file i.e
	 *         fileName.
	 */
	public static Properties writeFile(final String fileName, final Properties prop) {
		if (StringUtils.isBlank(fileName) && null == prop)
			throw new IllegalArgumentException("Both fileName and prop objects are mandatory.");
		OutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
			prop.store(os, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

}
