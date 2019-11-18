//package com.cisco.ping.api.server;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.cisco.pmtpf.server.commons.BaseCounter;
//import com.cisco.pmtpf.server.model.Connection;
//import com.mongodb.MongoClient;
//import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = PmtpfApiServerApplicationTests.class)
//public class PmtpfApiServerApplicationTests {
//
//	MongoClient mc = null;
//	MongoTemplate m = null;
//	BaseCounter bc = null;
//
//	@Before
//	public void startUp() {
//		mc = new MongoClient("localhost", 27017);
//		m = new MongoTemplate(mc, "PMTPF");
//		bc = new BaseCounter("localhost", "27017", "PMTPF");
//	}
//
//	@After
//	public void teadDown() {
//		mc.close();
//		mc = null;
//		m = null;
//		bc.close();
//		bc = null;
//	}
//
////	@Test
////	public void loadPrivilege() {
////		Assert.assertNotNull(m);
////
////		// Privilege management...
////		m.dropCollection(Privilege.class);
////		bc.dropSequence(Privilege.class);
////
////		Privilege adminPrivilege = new Privilege(Resource.All, Action.EXECUTE);
////		adminPrivilege.setPrivilegeId(bc.sequence(Privilege.Fields.privilegeId.name(), Privilege.class));
////		adminPrivilege = m.save(adminPrivilege);
////
////		// Group management...
////		m.dropCollection(PmtpfGroup.class);
////		bc.dropSequence(PmtpfGroup.class);
////
////		PmtpfGroup adminGroup = new PmtpfGroup();
////		Set<Privilege> privileges = new HashSet<Privilege>();
////		privileges.add(adminPrivilege);
////		adminGroup.setPrivileges(privileges);
////		adminGroup.setGroupId(bc.sequence(PmtpfGroup.Fields.groupId.name(), PmtpfGroup.class));
////		adminGroup.setGroupName(PmtpfGroup.Groups.admin_pmtpf.name());
////
////		m.save(adminGroup);
////	}
//
////	@Test
////	public void test() throws NamingException {
////		Hashtable<String, Object> env = new Hashtable<String, Object>(11);
////
////		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
////		env.put(Context.PROVIDER_URL, "ldap://dsxdev.cisco.com:389");
////		env.put(Context.SECURITY_AUTHENTICATION, "simple");
////		env.put(Context.SECURITY_PRINCIPAL, "uid=pingfedauto.gen,OU=Generics,O=cco.cisco.com");
////		env.put(Context.SECURITY_CREDENTIALS, Base64Utils.decode("TXlzd2VldGZhbWlseUAxMjM="));
////		
////		DirContext dex = new InitialDirContext(env);
////		System.out.println("Dir ctx:"+dex);
////		
////		SearchControls searchCtls = new SearchControls();
////		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
////		String[] attrIdsToSearch = new String[] { "member" };
////		searchCtls.setReturningAttributes(attrIdsToSearch);
////		
////		
////		String filter= "(CN=admin_pmtpf)";
////		NamingEnumeration<SearchResult> result = dex.search("OU=Cisco Groups,O=cco.cisco.com", filter, searchCtls);
////		List<Attributes> srclist = new ArrayList<Attributes>();
////		while (result.hasMoreElements()) {
////			SearchResult searchResult = (SearchResult) result.nextElement();
////			System.out.println("Result"+searchResult);
////			srclist.add(searchResult.getAttributes());
////		}
////		
////		for (Attributes attributes : srclist) {
////			System.out.println("Attr:"+attributes.getIDs());
////			Attribute attribute = attributes.get("member");
////			System.out.println(attribute);
////		}
////	}
//	
////	@Test
////	public void test() {
////		String groups[]= {"admin_pmtpff","PMTPF_MEMBEROF","group-allcwk-man"};
////		Criteria criteria = new Criteria();
////		List<Criteria> orQ= new ArrayList<Criteria>();
////		
////		for (String grp : groups) {
//////			Criteria expression = new Criteria();
//////			Criteria criteria3 = expression.and("cn").is(grp);
////			Criteria criteria2 = Criteria.where("groups").elemMatch(Criteria.where("cn").is(grp));
////			orQ.add(criteria2);
//////			orQ.add(criteria3);
////		}
////		
////		Criteria elemMatch = Criteria.where("owners").elemMatch(Criteria.where("uid").is("lmuthiah"));
////		orQ.add(elemMatch);
////		Query query = Query.query(criteria.orOperator(orQ.toArray(new Criteria[orQ.size()])));
////		List<Connection> conns = m.find(query, Connection.class);
////		System.out.println(conns);
////	}
//
//}
