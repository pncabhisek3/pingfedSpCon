package com.cisco.pmtpf.server.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.cisco.pmtpf.server.dao.LdapUserRepository;
import com.cisco.pmtpf.server.utils.Base64Utils;

@Repository
public class LdapUserRepositoryImpl implements LdapUserRepository {

//	private final String GSSAPI_Authentication = "GSSAPI";
//	private final String SIMPLE_Authentication = "simple";

	@Value("${ping.ldap.url}")
	private String ldapUrl;
	@Value("${ping.ldap.baseDn}")
	private String ldapBaseDn;
	@Value("${ping.ldap.userDn}")
	private String ldapUserDn;
	@Value("${ping.ldap.password}")
	private String ldapPassword;
	@Value("${ping.ldap.authenticationType}")
	private String ldapAuthenticationType;

	private Hashtable<String, Object> ldapEnv = null;

	@Override
	public List<Attributes> findEntries(Hashtable<String, Object> ldapEnv, String searchFilter, String searchBase, String... searchAttributes)
			throws NamingException {
		ldapEnv = getDefaultLdapSetup(ldapEnv);

		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		// Specify the attributes to return
		if (null != searchAttributes && searchAttributes.length == 0)
			searchAttributes = null;
		searchCtls.setReturningAttributes(searchAttributes);
		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// specify the LDAP search filter
		NamingEnumeration<SearchResult> answer = null;
		List<Attributes> srclist = null;
		DirContext dex = null;
		try {
			dex = new InitialDirContext(ldapEnv);
			answer = dex.search(searchBase, searchFilter, searchCtls);
			srclist = new ArrayList<Attributes>();

			int count = 0;
			while (answer.hasMoreElements()) {
				count++;
				SearchResult sr = (SearchResult) answer.next();
				srclist.add(sr.getAttributes());
			}
			System.out.println("Total Record>>>" + count);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			if (null != dex) {
				try {
					dex.close();
				} catch (NamingException e) {
					e.printStackTrace();

					throw e;
				}
			}
		}

		return srclist;
	}

	public Hashtable<String, Object> getDefaultLdapSetup(Hashtable<String, Object> env) {
		if(null == env || env.size() == 0) {
			env = new Hashtable<String, Object>(11);
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, this.ldapUrl);
			env.put(Context.SECURITY_AUTHENTICATION, this.ldapAuthenticationType);
			env.put(Context.SECURITY_PRINCIPAL, this.ldapUserDn);
			env.put(Context.SECURITY_CREDENTIALS, Base64Utils.decode(this.ldapPassword));
		}

		return env;
	}
	
}
