package com.cisco.pmtpf.server.rest.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.pmtpf.server.commons.ApiConstants;
import com.cisco.pmtpf.server.commons.ApiResponse;
import com.cisco.pmtpf.server.commons.ESPTestClientForGen;
import com.cisco.pmtpf.server.commons.ErrorCodeEnum;
import com.cisco.pmtpf.server.dao.BlobDocumentDao;
import com.cisco.pmtpf.server.dao.ConnectionDAO;
import com.cisco.pmtpf.server.dao.LdapUserRepository;
import com.cisco.pmtpf.server.model.BlobDocument;
import com.cisco.pmtpf.server.model.Connection;
import com.cisco.pmtpf.server.model.PmtpfGroup;
import com.cisco.pmtpf.server.utils.Base64Utils;
import com.cisco.pmtpf.service.ConnectionService;

@RestController
public class ConnectionController {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	private ConnectionDAO connectiondao;

	@Autowired
	private BlobDocumentDao<BlobDocument> docDao;

	@Autowired
	private ConnectionService connectionSevice;

	@Autowired
	private LdapUserRepository ldapUserRepository;

	@Value("${esp.api.genid}")
	private String espAppGenid;

	@Value("${esp.api.password}")
	private String espAppPassword;

	private final String APPLICATION_JSON_VALUE = "application/json";

	@PostMapping(value = ApiConstants.PingFed_Connection, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createConnection(@RequestBody Connection connection)
			throws IOException, JSONException, ParseException {

		// Get standardJsonTemplate form pingFed connection.
		JSONObject standardPingFedJsonTemplate = connectionSevice.getTemplate(connection, null);
		logger.debug(standardPingFedJsonTemplate.toString());
		ResponseEntity<Object> response = null;

		// 1. Send req to pingfed api to create connection..
		ResponseEntity<String> result = connectionSevice.createConnection(standardPingFedJsonTemplate);
		logger.debug(result.toString());

		JSONObject pingFedConnection = null;
		if (StringUtils.isNotBlank(result.getBody()))
			pingFedConnection = new JSONObject(result.getBody());

		ApiResponse<Object> apiResp = null;

		// 2. PingFed create conn success result handling.
		if (result.getStatusCodeValue() == 201) {

			// save connections...
			connection.setPfid(pingFedConnection.getString("id"));
			connection = connectiondao.createConnection(connection);

			apiResp = new ApiResponse<Object>(connection);
			response = new ResponseEntity<Object>(apiResp, HttpStatus.OK);
			return response;

		} else if (result.getStatusCodeValue() == 422 || result.getStatusCodeValue() == 400) {
			logger.info(pingFedConnection.getJSONArray("validationErrors").getJSONObject(0).getString("message"));

			apiResp = new ApiResponse<Object>(false, result.getStatusCode().toString(), result.getBody());
			response = new ResponseEntity<Object>(apiResp, HttpStatus.BAD_REQUEST);
			return response;
		}

		else {
			apiResp = new ApiResponse<Object>(false, ErrorCodeEnum.Invalid_Request.toString());
			response = new ResponseEntity<Object>(apiResp, HttpStatus.BAD_REQUEST);
			logger.info(response.toString());
			return response;
		}

	}

	@GetMapping(value = ApiConstants.PingFed_Connection, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getApps(HttpServletRequest request,
			@RequestParam(name = "pagable", required = false) Boolean pagable,
			@RequestParam(name = "pageNum", required = false, defaultValue="1") Long pageNum,
			@RequestParam(name = "itemPerPage", required = false, defaultValue = "10") Long itemPerPage)
			throws FileNotFoundException, IOException, ParseException, JSONException {

		List<Connection> connections = null;

		// 1. If owner is not admin...
		String rawGroupHeader = request.getHeader("PMTPF_MEMBEROF");
		logger.info("header values::" + rawGroupHeader);
		rawGroupHeader = StringUtils.isBlank(rawGroupHeader) ? "admin_pmtpf,PMTPF_AUTHGROUP": rawGroupHeader;

		String rawOwnerHeader = validateOwner(request, "PMTPF_AUTHUSER");
		rawOwnerHeader = StringUtils.isBlank(rawOwnerHeader) ? "abhisekm":rawOwnerHeader;

		List<String> headerTokens = null;
		if (StringUtils.isNotBlank(rawOwnerHeader))
			headerTokens = Arrays.asList(rawGroupHeader.split(","));

		// 2. Validate group...
		if (null != headerTokens && headerTokens.size() > 0) {

			boolean isOwner = false;
			for (String _header : headerTokens) {
				logger.info("header value to compare: " + _header);

				// If user is admin...
				if (PmtpfGroup.Groups.admin_pmtpf.name().equalsIgnoreCase(_header)) {
					logger.info("header value matched for admin grup: " + _header);
					isOwner = true;
					break;
				}
			}

			long totalConnections = -1l;
			if (!isOwner) {
				logger.info("Yor are not an admin. Limited access'll be there.");

				List<Criteria> orFilter = new ArrayList<Criteria>();
				Criteria finalCriteria = new Criteria();

				// 2.1 Add filter for owner...
				if (StringUtils.isNotBlank(rawOwnerHeader)) {
					logger.info("Setting filter for owner: " + rawOwnerHeader);
					Criteria ownerCriteria = Criteria.where("owners")
							.elemMatch(Criteria.where("uid").is(rawOwnerHeader));
					orFilter.add(ownerCriteria);
				}

				// 2.2 Add filter for groups...
				if (null != headerTokens && headerTokens.size() > 0) {
					for (String grp : headerTokens) {
						logger.info("Setting filter for group:" + grp);
						Criteria groupCriteria = Criteria.where("groups").elemMatch(Criteria.where("cn").is(grp));
						orFilter.add(groupCriteria);
					}
				}

				if (orFilter != null && orFilter.size() > 0) {
					Query finalQuery = Query
							.query(finalCriteria.orOperator(orFilter.toArray(new Criteria[orFilter.size()])));

					// 2.2.1. On "pagable = false", only tot-count is acquired.
					if (false == pagable) {
						totalConnections = mongoOperation.count(finalQuery, Connection.class);
						logger.info("Pagination is false. Hence total count=" + totalConnections);
						Map<String, Object> pagableCount = new HashMap<String, Object>(1);

						// [NOTE]: Get the count by 'PAGINATION_COUNT' in the front end app.
						pagableCount.put("PAGINATION_COUNT", totalConnections);
						return new ResponseEntity<Object>(new ApiResponse<Map<String, Object>>(pagableCount),
								HttpStatus.OK);
					}

					// 2.2.2. On 'true == pagable', get conn by offset value.
					if (true == pagable)
						connections = paginateConnection(pageNum, itemPerPage, finalQuery);

					// 2.2.3. On 'null == pagable' get conn as usual.
					if (null == pagable)
						connections = connectiondao.getConnections(finalQuery);

					finalQuery = null;
					finalCriteria = null;
					orFilter.clear();
				}

				// 2.3 If No connection found...
				if (null == connections || connections.size() == 0) {
					return new ResponseEntity<Object>(
							new ApiResponse<Connection>(false, "Either Unauthorized or No connection yet created."),
							HttpStatus.UNAUTHORIZED);
				}

			} else {
				// If owner is admin...

				// 2.2.1 On "pagable = false", only tot-count is acquired.
				logger.info("You are an admin user. You can see all the connections.");
				if (false == pagable) {
					totalConnections = mongoOperation.count(new Query(), Connection.class);
					logger.info("Pagination is false. Hence total count=" + totalConnections);

					Map<String, Object> pagableCount = new HashMap<String, Object>(1);
					pagableCount.put("PAGINATION_COUNT", totalConnections);

					return new ResponseEntity<Object>(new ApiResponse<Map<String, Object>>(pagableCount),
							HttpStatus.OK);
				}

				// 2.2.2. On 'true == pagable', get conn by offset value.
				if (true == pagable)
					connections = paginateConnection(pageNum, itemPerPage, null);

				// 2.2.3. On 'null == pagable' get conn as usual.
				if (null == pagable)
					connections = connectiondao.getConnections();
			}
		}

		return new ResponseEntity<Object>(new ApiResponse<List<Connection>>(connections), HttpStatus.OK);

	}

	private List<Connection> paginateConnection(long pageNum, Long itemPerPage, Query pageQuery) {
		long offset = (pageNum - 1) * itemPerPage;

		pageQuery = (null == pageQuery) ? new Query() : pageQuery;
		Sort sort = Sort.by(Sort.Direction.ASC, "appid");
		pageQuery.with(sort);
		pageQuery.skip(offset);

		if (null == itemPerPage || itemPerPage <= 0) {
			logger.info("Invalid Item per page input:" + itemPerPage + ".Setting default value 10");
			itemPerPage = 10L;
		}
		pageQuery.limit(itemPerPage.intValue());

		List<Connection> connections = connectiondao.getConnections(pageQuery);
		return connections;
	}

	@GetMapping(value = ApiConstants.PingFed_ConnectionWithId, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getApps(HttpServletRequest request, @PathVariable("entityID") String entityID)
			throws FileNotFoundException, IOException, ParseException, JSONException {

		Connection connection = connectiondao.getConnection(entityID);
		return new ResponseEntity<Object>(new ApiResponse<Connection>(connection), HttpStatus.OK);
	}

	@PutMapping(value = ApiConstants.PingFed_ConnectionWithId, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> inactivateapp(@PathVariable("entityID") String entityID,
			@RequestBody Connection conn2Update)
			throws JSONException, FileNotFoundException, IOException, ParseException {

		// Pingfed ID is mandatory...
		String pfId = conn2Update.getPfid();
		if (null == conn2Update || StringUtils.isBlank(pfId))
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.Invalid_Request.toString()),
					HttpStatus.NOT_ACCEPTABLE);

		// Get PF-JSON template of PF Connection.
		JSONObject pingFedConnection = connectionSevice.getTemplate(conn2Update, HttpMethod.PUT);

		// Throw error if no PF-JSON template is found.
		if (StringUtils.isBlank((String) pingFedConnection.get("id"))
				|| !(pfId.equalsIgnoreCase(pingFedConnection.getString("id"))))
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, "Connection id (i.e pfid) is mandatory."),
					HttpStatus.NOT_ACCEPTABLE);

		// UPDATE Connection in pingFederate application.
		ResponseEntity<String> pfUpdatedConn = connectionSevice.updateConnection(pingFedConnection, pfId);

		// IF UPDATE request is successful...
		if (pfUpdatedConn.getStatusCodeValue() < 300) {

			// Update certificate locally if available
			BlobDocument sloCert = conn2Update.getSpSloCert();
			if (null != sloCert && null != sloCert.getStaleInputFileId()) {
				docDao.deleteDocument(sloCert.getStaleInputFileId());
				logger.info("Old cert of Connection" + conn2Update.getEntityId() + " has been deleted.");
			}

			// Update connection locally.
			conn2Update = connectiondao.updateConnection(conn2Update);

			// returning updatedConn from db.
			return new ResponseEntity<Object>(new ApiResponse<Connection>(conn2Update), HttpStatus.OK);
		} else
			// If UPDATE request is un-successful...
			return new ResponseEntity<Object>(
					new ApiResponse<Object>(false, pfUpdatedConn.getStatusCode().toString(), pfUpdatedConn.getBody()),
					HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping(value = ApiConstants.PingFed_ConnectionWithId, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteApps(@PathVariable("entityID") String entityID)
			throws JSONException, FileNotFoundException, IOException, ParseException {

		// DELETE PF-Connection from pingFederate application end.
		Connection conn = mongoOperation.findOne(new Query(Criteria.where("entityId").is(entityID)), Connection.class);
		ResponseEntity<String> result = connectionSevice.deleteConnection(conn.getPfid());

		logger.info("Pingfed Connection DELETE status code:" + result.getStatusCodeValue());

		// If connection is deleted successfully.
		if (result.getStatusCodeValue() <= 204) {

			// Delete Connection cert if available.
			BlobDocument doc = conn.getSpSloCert();
			if (null != doc) {
				logger.info("The connection deleted from PF contains a document. Attempting to remove.");
				Long certId = doc.getInputFileId();
				if (null != certId) {
					boolean deleteDocument = docDao.deleteDocument(certId);
					if (!deleteDocument) {
						return new ResponseEntity<Object>(
								new ApiResponse<Connection>(false, "Failed to delete cert:" + doc.getInputFileName()),
								HttpStatus.NOT_ACCEPTABLE);
					}
				}
			}

			// Delete connection from locally..
			connectiondao.deleteConnection(entityID);
			logger.info("Delete connection from PingFed and locally is successful..");
			return new ResponseEntity<Object>(new ApiResponse<Connection>(null), HttpStatus.OK);

			// If no connection found with such entityID in pingFed app end...
		} else if (result.getStatusCodeValue() == 404) {
			BlobDocument doc = conn.getSpSloCert();
			if (null != doc) {
				logger.info("The connection deleted from PF contains a document. Attempting to remove.");
				Long certId = doc.getInputFileId();
				if (null != certId) {
					boolean deleteDocument = docDao.deleteDocument(certId);
					if (!deleteDocument) {
						return new ResponseEntity<Object>(
								new ApiResponse<Connection>(false, "Failed to delete cert:" + doc.getInputFileName()),
								HttpStatus.NOT_ACCEPTABLE);
					}
				}
			}

			// DELETE Connection from locally..
			connectiondao.deleteConnection(entityID);
			logger.info("Delete connection locally with id:" + entityID + " is successful.");
			return new ResponseEntity<Object>(new ApiResponse<Connection>(null), HttpStatus.OK);
		} else {
			logger.error("Delete Unsuccessful. Internal Error::" + result.getBody());
			return new ResponseEntity<Object>(
					new ApiResponse<Object>(false, result.getStatusCode().toString(), result.getBody()),
					HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(value = ApiConstants.ValidateResource, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateResource(@PathVariable("resourceId") String resourceId,
			@PathVariable("resourceType") String resourceType,
			@RequestParam(name = "retriveBy", required = false) String retriveBy,
			@RequestParam(name = "retriveProps", required = false) String retriveProps) {

		// '@' will be replaced by 'uid', 'cn' (I.E 'retriveBy')
		String _searchFilter = "@=";
		List<Attributes> results = null;

		String[] _retriveProps = StringUtils.isNotBlank(retriveProps) ? retriveProps.split(",") : null;
		List<Map<String, Object>> ldapUsers = null;

		// 1. Fetch request for resource-type as owner/userid.
		if (LdapUserRepository.Ldap_Owner.equalsIgnoreCase(resourceType.trim())
				|| LdapUserRepository.Ldap_UserID.equalsIgnoreCase(resourceType.trim())) {

			// Searching owner(s) in LDAP..
			try {
				results = ldapUserRepository.findEntries(null, new StringBuilder()
						.append(_searchFilter.replaceAll("@", retriveBy)).append(resourceId).append("*").toString(),
						"O=cco.cisco.com", _retriveProps);
			} catch (NamingException e) {
				logger.debug("", e);
				return new ResponseEntity<Object>(
						new ApiResponse<Object>(false, HttpStatus.FORBIDDEN.getReasonPhrase()), HttpStatus.FORBIDDEN);
			}

			// 1.2. Return error response if attribute not found.
			if (null == results || results.size() == 0) {
				logger.debug("No such 'Owner(s)' found with resouerceId:", resourceId);
				return new ResponseEntity<Object>(new ApiResponse<Object>(false, "No such Owner(s) found."),
						HttpStatus.BAD_REQUEST);
			}

			// 1.3. Set attributes into a Map
			ldapUsers = retriveAttributeValues(results,
					ArrayUtils.isNotEmpty(_retriveProps) ? _retriveProps : new String[] { "uid" });
		}

		// 1. Fetch request for resource-type group.
		if (LdapUserRepository.Ldap_Group.equalsIgnoreCase(resourceType.trim())) {

			// 1.1. Searching group(s) in LDAP
			try {
				results = ldapUserRepository.findEntries(null,
						new StringBuilder().append(_searchFilter.replaceAll("@", retriveBy)).append("*")
								.append(resourceId).append("*").toString(),
						"O=cco.cisco.com", _retriveProps);
			} catch (NamingException e) {
				logger.debug("", e);
				return new ResponseEntity<Object>(
						new ApiResponse<Object>(false, HttpStatus.FORBIDDEN.getReasonPhrase()), HttpStatus.FORBIDDEN);
			}

			// 1.2. Return error response if attribute not found.
			if (null == results || results.size() == 0) {
				logger.debug("No such 'Owner(s)' found with resouerceId:", resourceId);
				return new ResponseEntity<Object>(new ApiResponse<Object>(false, "No such Group(s) found."),
						HttpStatus.BAD_REQUEST);
			}

			// 1.3. Set attributes into a Map.
			ldapUsers = retriveAttributeValues(results,
					ArrayUtils.isNotEmpty(_retriveProps) ? _retriveProps : new String[] { "cn" });
		}

		// 1. Fetch request for resource-type app.
		if (LdapUserRepository.Ldap_App.equalsIgnoreCase(resourceType.trim())) {

			// [NOTE]: AppPortfolio is fetch from ESP(Enterprise Svc Platform).
			// We need accessToken to get get app-portfolio json obj. Let's do it.

			// 1.1. Get 'accessToken' for ESP access first.
			String espAccessToken = null;
			try {
				espAccessToken = ESPTestClientForGen.getAccessToken(espAppGenid, Base64Utils.decode(espAppPassword));
			} catch (Exception e) {
				logger.error("ESP api accessToken not found.", e);
				return new ResponseEntity<Object>(ErrorCodeEnum.Auth_Token_Missing_Invalid.toString(),
						HttpStatus.FORBIDDEN);
			}

			String resourceEndoder = null;
			try {
				resourceEndoder = URLEncoder.encode(resourceId, "UTF-8");
			} catch (UnsupportedEncodingException ignored) {
				// Can be safely ignored because UTF-8 is always supported
				ignored.printStackTrace();
			}

			// 1.2. Attempting to fetch app-portfolio using traditional approach.
			// NOTE: RestTemplate.exchange("GET") is not working.
			String businessAppURL = new StringBuilder().append(
					"https://cisco.service-now.com/api/now/table/cmdb_ci_business_app?sysparm_query=sys_class_name=cmdb_ci_business_app^nameLIKE")
					.append(resourceEndoder).toString();

			HttpURLConnection conn = null;
			try {
				URL url = new URL(businessAppURL);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod(HttpMethod.GET.toString());
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Authorization", "Bearer " + espAccessToken);
				conn.connect();
			} catch (IOException e) {
				logger.error("", e);
			}
			BufferedReader br = null;
			StringBuilder sb = null;
			try {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				sb = new StringBuilder(2048);
				for (String line; (line = br.readLine()) != null;)
					sb.append(line);
			} catch (Exception e) {
				logger.error("", e);
			} finally {
				if (null != conn)
					conn.disconnect();
			}

			// Return complete jsonString response if _retriveProps is missing
			JSONArray fromAppJsonArray = new JSONObject(sb.toString()).getJSONArray("result");
			String appRespString = null;
			if (null == _retriveProps || _retriveProps.length == 0) {
				appRespString = fromAppJsonArray.toString();
			} else {

				// Else return the partial jsonString based on _retriveProps
				JSONArray toAppJsonArray = new JSONArray();
				Iterator<Object> iterator = fromAppJsonArray.iterator();
				while (iterator.hasNext()) {
					JSONObject fromAppJsonObj = (JSONObject) iterator.next();

					JSONObject toAppJsonObj = new JSONObject();
					for (String propKey : _retriveProps)
						toAppJsonObj.put(propKey, fromAppJsonObj.get(propKey));
					toAppJsonArray.put(toAppJsonObj);
				}
				if (null == toAppJsonArray || toAppJsonArray.length() == 0)
					return new ResponseEntity<Object>(
							new ApiResponse<Object>(false, ErrorCodeEnum.No_Data_Exists.toString()),
							HttpStatus.NOT_ACCEPTABLE);

				appRespString = toAppJsonArray.toString();

				toAppJsonArray = null;
				fromAppJsonArray = null;
				iterator = null;
				fromAppJsonArray = null;
			}
			return new ResponseEntity<Object>(new ApiResponse<Object>(appRespString), HttpStatus.OK);
		}

		if (null == results)
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.No_Data_Exists.toString()),
					HttpStatus.NOT_ACCEPTABLE);
		results.clear();

		// Handling response if no search result is found.
		if (null == ldapUsers || ldapUsers.size() == 0)
			return new ResponseEntity<Object>(new ApiResponse<Object>(false, ErrorCodeEnum.No_Data_Exists.toString()),
					HttpStatus.NO_CONTENT);

		return new ResponseEntity<Object>(new ApiResponse<List<Map<String, Object>>>(ldapUsers), HttpStatus.OK);

	}

	private List<Map<String, Object>> retriveAttributeValues(List<Attributes> attributes, String[] retriveProps) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Attributes attribute : attributes) {

			Map<String, Object> datamap = new HashMap<String, Object>();
			for (String prop : retriveProps) {
				Attribute attrKey = attribute.get(prop);
				if (null != attrKey)
					try {
						datamap.put(attrKey.getID(), attrKey.get().toString());
					} catch (NamingException e) {
						e.printStackTrace();
					}
			}
			result.add(datamap);
		}
		return result;
	}

	private String validateOwner(HttpServletRequest request, String header2search) {
		String header = request.getHeader(header2search);
		if (StringUtils.isBlank(header)) {
			logger.debug("User not found in request header associated to " + header2search);
			return null;
		}
		logger.info("Header Value found on validatting 'owner' is:" + header);
		return header.trim();
	}

}