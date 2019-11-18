package com.cisco.pmtpf.server.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.cisco.pmtpf.server.model.Connection;

public interface ConnectionDAO {
	Connection createConnection(Connection connection);

//	Connection updateConnection(Connection connection, String entityID);
	
	Connection updateConnection(Connection connection);

	boolean deleteConnection(String entityID);
	
	List<Connection> getConnections();

	List<Connection> getConnections(final Query query);
	
	Connection getConnection(String entityId);
}
