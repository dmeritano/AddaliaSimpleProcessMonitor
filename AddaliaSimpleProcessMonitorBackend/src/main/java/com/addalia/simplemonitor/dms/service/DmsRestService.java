package com.addalia.simplemonitor.dms.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


import com.addalia.simplemonitor.dms.dto.DmsDocument;
import com.addalia.simplemonitor.dms.dto.DmsDocumentType;
import com.addalia.simplemonitor.dms.dto.DmsDocumentTypeAttribute;
import com.addalia.simplemonitor.dms.dto.DmsDocumentTypeGetAllResponse;
import com.addalia.simplemonitor.dms.dto.DmsDocumentTypeMeta;
import com.addalia.simplemonitor.dms.dto.DmsInfo;
import com.addalia.simplemonitor.dms.dto.DmsVolumes;
import com.addalia.simplemonitor.dms.dto.SearchParameters;
import com.addalia.simplemonitor.dms.dto.SearchResponse;
import com.addalia.simplemonitor.dms.dto.UserAuth;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DmsRestService {

	private static Logger logger = LoggerFactory.getLogger(DmsRestService.class);

	private String baseUrl;
	private ObjectMapper mapper = new ObjectMapper();
	private String dmsCookieValue = "";
	private RestTemplate apiClient;
	private boolean error;
	private String authenticatedUser;

	// Name of the task or process using this service (For logging purposes)
	private String taskName = "";

	public DmsRestService(String baseUrl, boolean ignoreSelfSignedSSLCertificates, String taskName) throws Exception {
		if (!baseUrl.endsWith("/")) {
			this.baseUrl = baseUrl + "/";
		} else {
			this.baseUrl = baseUrl;
		}

		this.apiClient = createRestTemplate(ignoreSelfSignedSSLCertificates);//Utils.createRestTemplate(ignoreSelfSignedSSLCertificates);

		this.taskName = taskName;

	}

	public boolean isError() {
		return error;
	}

	public boolean connect(String user, String pass) {

		final String API_ENDPOINT = this.baseUrl + "dms/authenticate";

		String msg;

		try {
			String body = mapper.writeValueAsString(new UserAuth(user, pass));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(body, headers);

			ResponseEntity<String> response = apiClient.postForEntity(API_ENDPOINT, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {

				headers = response.getHeaders();
				String cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
				this.dmsCookieValue = cookie.split(";")[0].split("=")[1];
				this.authenticatedUser = user;

				msg = "Success DMS login for user " + user + " Task: " + this.taskName;
				logger.info(msg);

				return true;
			}
		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error authenticating user %s - Task: %s - Endpoint: %s - Error: %s",
					user, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error authenticating user %s - Task: %s - Endpoint: %s - Error: %s",
					user, this.taskName, API_ENDPOINT, ex.getMessage());		
			logger.error(msg);
		}
		return false;
	}

	public void disconnect() {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/authenticate";

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			apiClient.exchange(API_ENDPOINT, HttpMethod.DELETE, new HttpEntity<Void>(requestHeaders), String.class);
			msg = "Success DMS logoff for user " + this.authenticatedUser + " Task: " + this.taskName;
			logger.info(msg);

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error desconnecting user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());	
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error disconnecting user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());	
			logger.error(msg);
		}
	}

	public boolean isConnected() {

		final String API_ENDPOINT = this.baseUrl + "dms/authenticate";

		String msg;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			apiClient.exchange(API_ENDPOINT, HttpMethod.GET, new HttpEntity<Void>(requestHeaders), String.class);

			msg = "Success checking connection for user " + this.authenticatedUser + " Task: " + this.taskName;
			logger.info(msg);

			return true;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error checking if is connected user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);			
		} catch (Exception ex) {
			msg = String.format("DMS general error checking if is connected user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());														
			logger.error(msg);			
		}

		return false;
	}

	public DmsInfo getDmsInfo() {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/info";
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			ResponseEntity<DmsInfo> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), DmsInfo.class);

			DmsInfo dmsInfo = (DmsInfo) response.getBody();

			msg = "Success getting DmsInfo for user " + this.authenticatedUser + " Task: " + this.taskName;
			logger.info(msg);

			return dmsInfo;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error getting DmsInfo with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());				
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS error general getting DmsInfo with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());											
			logger.error(msg);
		}

		this.error = true;
		return new DmsInfo();
	}

	public DmsVolumes getDmsVolumes(boolean mounted, boolean usable) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/volumes";
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			requestHeaders.add("usable", String.valueOf(usable));
			requestHeaders.add("mounted", String.valueOf(mounted));
			ResponseEntity<DmsVolumes> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), DmsVolumes.class);

			DmsVolumes volumes = (DmsVolumes) response.getBody();

			msg = "Success getting dms volumes with user " + this.authenticatedUser + " Task: " + this.taskName;
			logger.info(msg);

			return volumes;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error getting repository volumes with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error getting repository volumes with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());													
			logger.error(msg);
		}

		this.error = true;
		return new DmsVolumes();
	}

	public SearchResponse searchDocuments(SearchParameters searchParameters) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents";
		this.error = false;

		try {

			msg = String.format("Searching documents. Page:%d, PageSize:%d, Query:%s", 
					searchParameters.getPage(), searchParameters.getPageSize(), searchParameters.getQuery());
			logger.info(msg);

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			requestHeaders.add("query", searchParameters.getQuery());
			requestHeaders.add("deleted", "false");
			requestHeaders.add("page", String.valueOf(searchParameters.getPage()));			
			requestHeaders.add("pageSize", String.valueOf(searchParameters.getPageSize()));
			requestHeaders.add("onlyMeta", String.valueOf(searchParameters.isOnlyMeta()));

			ResponseEntity<SearchResponse> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), SearchResponse.class);

			SearchResponse searchResponse = (SearchResponse) response.getBody();

			msg = String.format("Found %s document/s with user: %s - Task: %s - Query: %s", 
					searchResponse.getMeta().getTotal(), this.authenticatedUser, this.taskName, searchParameters.getQuery());
			logger.info(msg);

			return searchResponse;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error searching documents with user %s - Task: %s - Query: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, searchParameters.getQuery(), API_ENDPOINT, httpClientOrServerExc.getMessage());						
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error searching documents with user %s - Task: %s - Query: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, searchParameters.getQuery(), API_ENDPOINT, ex.getMessage());						
			logger.error(msg);			
		}

		this.error = true;
		return new SearchResponse();
	}	
	
	/*
	public SearchResponse searchDocuments(String query, int page, int pageSize, boolean onlyMeta) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents";
		this.error = false;

		try {

			msg = String.format("Searching documents. Page:%d, PageSize:%d, Query:%s", page, pageSize, query);
			logger.info(msg);

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();
			requestHeaders.add("query", query);
			requestHeaders.add("deleted", "false");
			requestHeaders.add("page", String.valueOf(page));
			requestHeaders.add("onlyMeta", String.valueOf(onlyMeta));
			requestHeaders.add("pageSize", String.valueOf(pageSize));

			ResponseEntity<SearchResponse> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), SearchResponse.class);

			SearchResponse searchResponse = (SearchResponse) response.getBody();

			msg = String.format("Found %s document/s with user: %s - Task: %s - Query: %s", 
					searchResponse.getMeta().getTotal(), this.authenticatedUser, this.taskName, query);
			logger.info(msg);

			return searchResponse;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error searching documents with user %s - Task: %s - Query: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, query, API_ENDPOINT, httpClientOrServerExc.getMessage());						
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error searching documents with user %s - Task: %s - Query: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, query, API_ENDPOINT, ex.getMessage());						
			logger.error(msg);			
		}

		this.error = true;
		return new SearchResponse();
	}
	*/
	public DmsDocument getDocument(String id) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents/" + id;
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();

			ResponseEntity<DmsDocument> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), DmsDocument.class);

			DmsDocument dmsDocument = (DmsDocument) response.getBody();

			msg = "Success getting document " + id + " with user " + this.authenticatedUser + " Task: " + this.taskName;
			logger.info(msg);

			return dmsDocument;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {						
			msg = String.format("DMS error getting document with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);
		} catch (Exception ex) {
			msg = String.format("DMS general error getting document with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);
		}

		this.error = true;
		return new DmsDocument();

	}

	public SearchResponse getDocumentChildrens(String id) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents/" + id + "/children";
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();

			ResponseEntity<SearchResponse> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), SearchResponse.class);

			SearchResponse searchResponse = (SearchResponse) response.getBody();

			msg = "Success getting childrens for document " + id + " with user " + this.authenticatedUser + " Task: "
					+ this.taskName;
			logger.info(msg);

			return searchResponse;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error getting childrens for a document with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);						
		} catch (Exception ex) {
			msg = String.format("DMS error general getting childrens for a document with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);						
		}

		this.error = true;
		return new SearchResponse();

	}

	public DmsDocumentType getDocumentDefinition(String documentName) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/types/" + documentName;
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();

			ResponseEntity<String> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), String.class);

			String jsData = response.getBody();

			DmsDocumentType dmsDocumentType = new DmsDocumentType();

			// Json - Mapping
			JsonNode root = mapper.readTree(jsData);

			// Meta
			JsonNode meta = root.get("meta");
			dmsDocumentType.setMeta(mapper.readValue(meta.toString(), DmsDocumentTypeMeta.class));

			// Attributes
			Iterator<Entry<String, JsonNode>> nodes = root.get("attributes").fields();
			while (nodes.hasNext()) {
				Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
				DmsDocumentTypeAttribute dma = mapper.readValue(entry.getValue().toString(),
						DmsDocumentTypeAttribute.class);
				dma.setAttributeName(entry.getKey());
				dmsDocumentType.getAttributes().add(dma);
			}
			msg = "Success getting document definition for " + documentName + " with user " + this.authenticatedUser
					+ " Task: " + this.taskName;
			logger.info(msg);

			return dmsDocumentType;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {			
			msg = String.format("DMS error getting document type definition with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);												
		} catch (Exception ex) {
			msg = String.format("DMS error general getting document type definition with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);												
		}

		this.error = true;
		return new DmsDocumentType();
	}

	public ArrayList<DmsDocumentTypeGetAllResponse> getAllDocumentsType() {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/types";
		this.error = false;

		try {

			HttpHeaders requestHeaders = this.getRequestBaseHeaders();

			ResponseEntity<String> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), String.class);

			String jsData = response.getBody();

			ArrayList<DmsDocumentTypeGetAllResponse> documentsType = new ArrayList<>();

			// Json - Mapping
			JsonNode root = mapper.readTree(jsData);

			// Documents
			Iterator<Entry<String, JsonNode>> nodes = root.fields();
			while (nodes.hasNext()) {
				Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();

				DmsDocumentTypeGetAllResponse doc = mapper.readValue(entry.getValue().toString(),
						DmsDocumentTypeGetAllResponse.class);
				doc.setName(entry.getKey());

				documentsType.add(doc);

			}

			msg = "Success getting all documents types definitions with user " + this.authenticatedUser + " - Task: "
					+ this.taskName + " Documents obtained: " + documentsType.size();
			logger.info(msg);

			return documentsType;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error getting all documents definitions with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);																		
		} catch (Exception ex) {
			msg = String.format("DMS general error getting all documents definitions with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);																		
		}

		this.error = true;
		return new ArrayList<DmsDocumentTypeGetAllResponse>();

	}

	public byte[] getItemBytes(String id, String mimeConversion) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents/" + id + "/item";
		this.error = false;

		try {
			HttpHeaders requestHeaders = this.getRequestBaseHeaders();

			if (mimeConversion != null) {
				requestHeaders.add("mime-type", mimeConversion);
			}

			ResponseEntity<byte[]> response = apiClient.exchange(API_ENDPOINT, HttpMethod.GET,
					new HttpEntity<Void>(requestHeaders), byte[].class);

			byte[] media = response.getBody();

			msg = "Success getting item media for " + id + " with user " + this.authenticatedUser + " Task: "
					+ this.taskName;
			logger.info(msg);

			return media;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error getting media item with user with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);																		
		} catch (Exception ex) {
			msg = String.format("DMS general error getting media item with user with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);																		
		}

		this.error = true;
		return new byte[] {};
	}

	public boolean updateDocument(String id, Map<String, String> attributes) {

		String msg;
		final String API_ENDPOINT = this.baseUrl + "dms/documents/" + id;

		try {

			ObjectNode root = mapper.createObjectNode();
			ObjectNode nodeAttributesContainer = mapper.createObjectNode();
			ObjectNode attributesList = mapper.createObjectNode();
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				attributesList.put(entry.getKey(), entry.getValue());
			}
			nodeAttributesContainer.set("attributes", attributesList);
			root.setAll(nodeAttributesContainer);
			String jsonAttributes = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

			HttpHeaders requestHeaders = getRequestBaseHeaders();
			HttpEntity<String> request = new HttpEntity<>(jsonAttributes, requestHeaders);

			apiClient.exchange(API_ENDPOINT, HttpMethod.PUT, request, DmsDocument.class);

			msg = "Success updating document attributes for " + id + " with user " + this.authenticatedUser + " Task: "
					+ this.taskName;
			logger.info(msg);

			return true;

		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			msg = String.format("DMS error updating document attributes with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, httpClientOrServerExc.getMessage());							
			logger.error(msg);																					
		} catch (Exception ex) {
			msg = String.format("DMS general error updating document attributes with user %s - Task: %s - Endpoint: %s - Error: %s",
					this.authenticatedUser, this.taskName, API_ENDPOINT, ex.getMessage());							
			logger.error(msg);																					
		}

		return false;
	}

	private HttpHeaders getRequestBaseHeaders() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.add("Cookie", "JSESSIONID=" + this.dmsCookieValue);
		return requestHeaders;
	}

	private RestTemplate createRestTemplate(boolean ignoreInvalidSSLCertificates)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

		if (!ignoreInvalidSSLCertificates) {
			return new RestTemplate();
		}
		
		// Ignore invalid SSL certificates
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.setProtocol("TLSv1.2")
				.build();
				
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;

	}	
	
}
