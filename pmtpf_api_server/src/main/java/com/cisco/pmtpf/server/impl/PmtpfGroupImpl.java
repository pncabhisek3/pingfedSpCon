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
import com.cisco.pmtpf.server.dao.PmtpfGroupDao;
import com.cisco.pmtpf.server.model.Attributes;
import com.cisco.pmtpf.server.model.PmtpfGroup;
import com.mongodb.client.result.DeleteResult;

@Repository
public class PmtpfGroupImpl implements PmtpfGroupDao {

	private static final Logger logger = LoggerFactory.getLogger(PmtpfGroupImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	BaseCounter counter;

	private String INSTANCE_NAME = this.getClass().getName();

	@Override
	public Attributes save(final Attributes group) {
		if (null == group || StringUtils.isBlank(group.getCn()))
			return null;

		Query query = new Query(Criteria.where(PmtpfGroup.Fields.groupName.name()).is(group.getCn()));
		PmtpfGroup pg = mongoTemplate.findOne(query, PmtpfGroup.class);
		if (null == pg) {
			logger.info("No Group found. Creating a new one in " + INSTANCE_NAME);
			pg = new PmtpfGroup();
			pg.setGroupName(group.getCn());
			pg.setGroupId(counter.sequence(PmtpfGroup.Fields.groupId.name(), PmtpfGroup.class));
			pg = mongoTemplate.save(pg);
			counter.close();
		} else {
			logger.info("Existing Group found. Updating now in " + INSTANCE_NAME);
			pg.setGroupName(group.getCn());
			pg = mongoTemplate.save(pg);
		}
		return group;
	}

	@Override
	public Attributes update(Attributes group) {
		save(group);
		return group;
	}

	@Override
	public Attributes get(String groupName) {
		if (StringUtils.isBlank(groupName))
			return null;

		Query query = new Query(Criteria.where(PmtpfGroup.Fields.groupName.name()).is(groupName));
		PmtpfGroup pg = mongoTemplate.findOne(query, PmtpfGroup.class);
		if (null == pg) {
			logger.info("No Group found by name in" + INSTANCE_NAME);
			return null;
		}
		logger.info("Group found by name in " + INSTANCE_NAME);
		Attributes attr = new Attributes();
		attr.setCn(pg.getGroupName());
		return attr;
	}

	@Override
	public List<Attributes> getAll() {
		List<PmtpfGroup> groups = mongoTemplate.findAll(PmtpfGroup.class);
		List<Attributes> attrs = getAttrs(groups);
		groups = null;
		return attrs;
	}

	@Override
	public List<Attributes> getAll(final Query query) {
		if (null == query)
			return null;
		List<PmtpfGroup> groups = mongoTemplate.find(query, PmtpfGroup.class);
		List<Attributes> attrs = getAttrs(groups);
		groups = null;
		return attrs;
	}

	@Override
	public void delete(long groupId) {
		Query query = new Query(Criteria.where(PmtpfGroup.Fields.groupId.name()).is(groupId));
		PmtpfGroup group = mongoTemplate.findOne(query, PmtpfGroup.class);
		logger.info("Group data to delete>>>" + group + " in " + INSTANCE_NAME);
		if (null == group)
			return;
		mongoTemplate.remove(group);
	}

	private List<Attributes> getAttrs(List<PmtpfGroup> groups) {
		if (null == groups || groups.size() == 0) {
			logger.info("No groups found in " + INSTANCE_NAME);
			return null;
		}
		logger.info("Groups found in " + INSTANCE_NAME);
		List<Attributes> attrs = new ArrayList<>();

		for (PmtpfGroup pmtpfGroup : groups) {
			Attributes attr = new Attributes();
			attr.setCn(pmtpfGroup.getGroupName());
			attrs.add(attr);
		}
		return attrs;
	}

	@Override
	public void delete(List<Attributes> attrs) {
		if (null == attrs || attrs.size() == 0)
			return;
		for (Attributes attr : attrs) {
			Attributes _attr = get(attr.getCn());
			deteletByAttr(_attr);
		}
	}

	private void deteletByAttr(Attributes attr) {
		Query query = new Query(Criteria.where(PmtpfGroup.Fields.groupName.name()).is(attr.getCn()));
		PmtpfGroup pg = mongoTemplate.findOne(query, PmtpfGroup.class);
		DeleteResult remove = mongoTemplate.remove(pg);
		if (null == remove)
			logger.info("Failed to delete by attrute: " + attr + " from the list in " + INSTANCE_NAME);
	}

}
