package com.cisco.pmtpf.server.dao;

import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public interface LdapUserRepository {

	String Ldap_Owner = "OWNER";
	String Ldap_Group = "GROUP";
	String Ldap_App = "APP";
	String Ldap_UserID = "USERID";

	List<Attributes> findEntries(Hashtable<String, Object> ldapEnv, String SearchFilter, String searchBase, String... searchAttributes)
			throws NamingException;
}
