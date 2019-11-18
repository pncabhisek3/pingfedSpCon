package com.cisco.pmtpf.server.commons;

import org.springframework.beans.factory.annotation.Value;

public class MongoRepository {
	
	@Value("${spring.data.mongodb.port}")
	private static String port;
	@Value("${spring.data.mongodb.host}")
	private static String host;
	@Value("${spring.data.mongodb.database}")
	private static String database;

	private static MongoRepository repo;

	public static synchronized MongoRepository getInstance() {
		if (null == repo)
			repo = new MongoRepository();
		return repo;
	}
	
	public static synchronized MongoRepository getInstance(final String prot, final String host, final String database) {
		if (null == repo)
			repo = new MongoRepository();
		return repo;
	}
}
