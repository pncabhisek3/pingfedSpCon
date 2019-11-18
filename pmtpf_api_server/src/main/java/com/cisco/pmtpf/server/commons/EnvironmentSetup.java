package com.cisco.pmtpf.server.commons;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.cisco.pmtpf.server.model.Action;
import com.cisco.pmtpf.server.model.PmtpfGroup;
import com.cisco.pmtpf.server.model.Privilege;
import com.cisco.pmtpf.server.model.Resource;
import com.mongodb.MongoClient;

@Component
public class EnvironmentSetup implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentSetup.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (alreadySetup)
			return;

		// Map contains the list of environment variables
		Map<String, String> newenv = new HashMap<String, String>();
		newenv.put("BLACKBOX_PMTPF_ORIGIN", "iam-status-3.cisco.com");

		setEnvVariables(newenv);

//		setInitialDataForDatabase();

		alreadySetup = true;
	}

	private void setInitialDataForDatabase() {
		MongoClient mc = new MongoClient("localhost", 27017);
		MongoTemplate m = new MongoTemplate(mc, "PMTPF");
		BaseCounter bc = new BaseCounter("localhost", "27017", "PMTPF");

		Assert.assertNotNull(m);

		// Privilege management...
		m.dropCollection(Privilege.class);
		bc.dropSequence(Privilege.class);

		Privilege adminPrivilege = new Privilege(Resource.All, Action.EXECUTE);
		adminPrivilege.setPrivilegeId(bc.sequence(Privilege.Fields.privilegeId.name(), Privilege.class));
		adminPrivilege = m.save(adminPrivilege);

		// Group management...
		m.dropCollection(PmtpfGroup.class);
		bc.dropSequence(PmtpfGroup.class);

		PmtpfGroup adminGroup = new PmtpfGroup();
		Set<Privilege> privileges = new HashSet<Privilege>();
		privileges.add(adminPrivilege);
		adminGroup.setPrivileges(privileges);
		adminGroup.setGroupId(bc.sequence(PmtpfGroup.Fields.groupId.name(), PmtpfGroup.class));
		adminGroup.setGroupName(PmtpfGroup.Groups.admin_pmtpf.name());

		m.save(adminGroup);

		mc.close();
		mc = null;
		m = null;
		bc.close();
		bc = null;
		logger.info("\n Initial data, such as groups, privileges.. etc are loaded from: " + this.getClass().getName()+"\n");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setEnvVariables(Map<String, String> newenv) {

		if (null == newenv || newenv.size() == 0)
			return;
		try {
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");

			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
			env.putAll(newenv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
					.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
			cienv.putAll(newenv);
		} catch (NoSuchFieldException e) {
			Class[] classes = Collections.class.getDeclaredClasses();
			Map<String, String> env = System.getenv();
			for (Class cl : classes) {
				if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
					Field field = null;
					try {
						field = cl.getDeclaredField("m");
					} catch (NoSuchFieldException | SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					field.setAccessible(true);
					Object obj = null;
					try {
						obj = field.get(env);
					} catch (IllegalArgumentException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Map<String, String> map = (Map<String, String>) obj;
					map.clear();
					map.putAll(newenv);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		logger.info("\n Initial environment variables set in your environment from: "+this.getClass().getName()+"\n");
	}

}
