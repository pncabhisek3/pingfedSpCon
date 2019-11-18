package com.cisco.pmtpf.server.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.cisco.pmtpf.server.model.CounterReference;
import com.cisco.pmtpf.server.model.Sequence;
import com.mongodb.MongoClient;

@Component
public class BaseCounter {

	private static Logger logger = LoggerFactory.getLogger(BaseCounter.class);

	@Value("${spring.data.mongodb.host}")
	private String host;
	@Value("${spring.data.mongodb.port}")
	private String port;
	@Value("${spring.data.mongodb.database}")
	private String database;

	@Autowired
	private MongoTemplate mongoTemplate;

	public BaseCounter() {
	}

	public BaseCounter(String host, String port, String database) {
		this.host = host;
		this.port = port;
		this.database = database;
	}

	private MongoClient mongoClient;
	private boolean manualOpen = false;

	public long sequence(String fieldName, Class<?> clazz) {
		mongoTemplate= getInstance();
		String fullyQualifiedName = clazz.getName().replace(".", "_");

		Query query = new Query(Criteria.where("referenceId").is(1L));
		List<CounterReference> crfs = mongoTemplate.find(query, CounterReference.class);
		CounterReference counterRef = null;
		if (null != crfs && crfs.size() == 1)
			counterRef = crfs.get(0);
		Map<String, Sequence> counterMap = null;
		if (null != counterRef)
			counterMap = counterRef.getCounterMap();

		if (null != counterMap) {
			for (@SuppressWarnings("unused")
			String className : counterMap.keySet()) {
				Sequence sequence = counterMap.get(fullyQualifiedName);
				if (null == sequence) {
					counterMap.put(fullyQualifiedName, new Sequence(fieldName, 1L));
					break;
				} else {
					sequence.setSequence(sequence.getSequence() + 1);
					break;
				}
			}
			mongoTemplate.save(counterRef);
		}

		if (null == counterMap || counterMap.isEmpty()) {
			counterMap = new HashMap<String, Sequence>();
			Sequence sequence = new Sequence(fieldName, 1L);
			counterMap.put(fullyQualifiedName, sequence);
			counterRef = new CounterReference();
			counterRef.setCounterMap(counterMap);
			counterRef.setReferenceId(1L);
			counterRef = mongoTemplate.save(counterRef);
			counterMap = counterRef.getCounterMap();
		}
		crfs.clear();
		close();
		return counterMap.get(fullyQualifiedName).getSequence();
	}

	public void close() {
		if (manualOpen) {
			mongoClient.close();
			mongoTemplate = null;
			manualOpen = false;
		}
	}
	
	private synchronized MongoTemplate getInstance() {
		if (null == mongoTemplate) {
			logger.info("Instance block executed. Manually MongoTemplate instantiated-" + this.getClass().getName());
			mongoClient = new MongoClient(host, Integer.parseInt(port));
			mongoTemplate = new MongoTemplate(mongoClient, database);
			manualOpen = true;
		}
		return mongoTemplate;
	}
	
	public void dropSequence(Class<?> clazz) {
		if(null == clazz)
			return;
		String fullyQualifiedName = clazz.getName().replace(".", "_");
		mongoTemplate= getInstance();
		
		Query query = new Query(Criteria.where("referenceId").is(1L));
		List<CounterReference> crfs = mongoTemplate.find(query, CounterReference.class);
		CounterReference counterRef = null;
		if(null == crfs || crfs.size() == 0) 
			return;
		if (null != crfs && crfs.size() == 1)
			counterRef = crfs.get(0);
		Map<String, Sequence> counterMap = null;
		
		if (null != counterRef)
			counterMap = counterRef.getCounterMap();
		counterMap.remove(fullyQualifiedName);
		mongoTemplate.save(counterRef);
		crfs.clear();
	}

}
