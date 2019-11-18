package com.cisco.pmtpf.server.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cisco.pmtpf.server.commons.BaseCounter;
import com.cisco.pmtpf.server.dao.ConnectionDAO;
import com.cisco.pmtpf.server.model.Connection;

@Repository
public class ConnectionDAOImpl implements ConnectionDAO {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionDAOImpl.class);

	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	private BaseCounter baseCounter;

	@Override
	public Connection createConnection(Connection connection) {
		if (null == connection)
			throw new IllegalArgumentException("Invalid connection input paramter.");

		connection.setAppid(baseCounter.sequence("apps", Connection.class));
		connection = mongoOperation.save(connection);
		logger.info("\nNew connection has been created...\n");
		return connection;
	}

//	@Override
//	public Connection updateConnection(Connection connection, String pfid) {
//
////		Query query = new Query(Criteria.where("_id").is(pfid));
////		Connection conn = mongoOperation.findOne(query, Connection.class);
//
//		connection.setPfid(pfid);
//		connection = mongoOperation.save(connection);
//		return connection;
//	}
//	

	@Override
	public boolean deleteConnection(String entityID) {
		Query query = new Query(Criteria.where("entityId").is(entityID));
		mongoOperation.remove(query, Connection.class);
		return true;
	}

	@Override
	public List<Connection> getConnections(final Query query) {
		List<Connection> connections = mongoOperation.find(query, Connection.class);
		return connections;
	}

	@Override
	public Connection getConnection(String entityId) {
		Query query = new Query(Criteria.where("entityId").is(entityId));
		Connection connection = mongoOperation.findOne(query, Connection.class);
		return connection;
	}

	@Override
	public Connection updateConnection(Connection connection) {
		return mongoOperation.save(connection);
	}

	@Override
	public List<Connection> getConnections() {
		List<Connection> connections = mongoOperation.findAll(Connection.class);
		return connections;
	}

}