package com.cisco.pmtpf.server.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.cisco.pmtpf.server.model.Attributes;

public interface OwnerDao {

public Attributes save(Attributes group);
	
	public Attributes update(Attributes group);

	public Attributes get(String groupName);

	public List<Attributes> getAll();

	public List<Attributes> getAll(Query query);

	public void delete(long groupId);
	
	public void delete(List<Attributes> attr);
}
