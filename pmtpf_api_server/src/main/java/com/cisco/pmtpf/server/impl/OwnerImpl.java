package com.cisco.pmtpf.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cisco.pmtpf.server.commons.BaseCounter;
import com.cisco.pmtpf.server.dao.OwnerDao;
import com.cisco.pmtpf.server.model.Attributes;
import com.cisco.pmtpf.server.model.Owner;
import com.cisco.pmtpf.server.model.PmtpfGroup;

@Repository
public class OwnerImpl implements OwnerDao {

	private static Logger logger = LoggerFactory.getLogger(OwnerImpl.class);
	private String INSTANCE_NAME = this.getClass().getName();
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private BaseCounter counter;

	@Override
	public Attributes save(Attributes owner) {
		if (null == owner || StringUtils.isBlank(owner.getCn()))
			return null;

		Query query = new Query(Criteria.where(Owner.Fields.ownerName.name()).is(owner.getUid()));
		Owner pg = mongoTemplate.findOne(query, Owner.class);
		if (null == pg) {
			logger.info("No Group found. Creating a new one in " + INSTANCE_NAME);
			pg = new Owner();
			pg.setOwnerName(owner.getCn());
			pg.setOwnerId(counter.sequence(Owner.Fields.ownerId.name(), Owner.class));
			pg = mongoTemplate.save(pg);
			counter.close();
		} else {
			logger.info("Existing Group found. Updating now in " + INSTANCE_NAME);
			pg.setOwnerName(owner.getUid());
			pg = mongoTemplate.save(pg);
		}

		return null;
	}

	@Override
	public Attributes update(Attributes group) {
		save(group);
		return group;
	}

	@Override
	public Attributes get(String ownerName) {
		if (StringUtils.isBlank(ownerName))
			return null;

		Query query = new Query(Criteria.where(Owner.Fields.ownerName.name()).is(ownerName));
		Owner pg = mongoTemplate.findOne(query, Owner.class);
		if (null == pg) {
			logger.info("No Owner found by name in" + INSTANCE_NAME);
			return null;
		}
		logger.info("Owner found by name in " + INSTANCE_NAME);
		Attributes attr = new Attributes();
		attr.setUid(pg.getOwnerName());
		return attr;
	}

	@Override
	public List<Attributes> getAll() {
		List<Owner> owners = mongoTemplate.findAll(Owner.class);
		List<Attributes> attrs = getAttrs(owners);
		owners = null;
		return attrs;
	}

	@Override
	public List<Attributes> getAll(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(List<Attributes> attr) {
		// TODO Auto-generated method stub

	}

	private List<Attributes> getAttrs(List<Owner> owners) {
		if (null == owners || owners.size() == 0) {
			logger.info("No Owner found in " + INSTANCE_NAME);
			return null;
		}
		logger.info("Owner found in " + INSTANCE_NAME);
		List<Attributes> attrs = new ArrayList<>();

		for (Owner owner : owners) {
			Attributes attr = new Attributes();
			attr.setUid(owner.getOwnerName());
			attrs.add(attr);
		}
		return attrs;
	}

}
