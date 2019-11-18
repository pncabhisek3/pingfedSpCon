package com.cisco.pmtpf.server.commons;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdapAuth {
	private static final Logger logger = LoggerFactory.getLogger(LdapAuth.class);
	private final static String ldapURI =  "ldap://dsxstage.cisco.com:389";//"ldap://dsx.cisco.com:389";
	private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

	private static DirContext ldapContext() throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		return ldapContext(env);
	}

	private static DirContext ldapContext(Hashtable<String, String> env) throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, ldapURI);
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}

	static String getUid(String user) throws Exception {
		DirContext ctx = ldapContext();

		String filter = "(uid=" + user + ")";
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer = ctx.search("", filter, ctrl);

		String dn;
		if (answer.hasMore()) {
			SearchResult result = (SearchResult) answer.next();
			dn = result.getNameInNamespace();
		} else {
			dn = null;
		}
		answer.close();
		return dn;
	}

	private static boolean testBind(String dn, String password) throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			ldapContext(env);
		} catch (javax.naming.AuthenticationException e) {
			return false;
		}
		return true;
	}

	public static boolean getauth(String user, String password) throws Exception {

		/* Found user - test password */
		if (testBind(user, password)) {
			System.out.println("user '" + user + "' authentication succeeded");
			logger.error("user '" + user + "' authentication succeeded");
			return true;
		} else {
			System.out.println("user '" + user + "' authentication failed");
			logger.error("user '" + user + "' authentication failed");
			return false;
		}
	}
}