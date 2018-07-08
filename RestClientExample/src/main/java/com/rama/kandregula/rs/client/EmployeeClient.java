package com.rama.kandregula.rs.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rama.kandregula.rs.model.Employee;

public class EmployeeClient {

	private CloseableHttpClient client;

	
	public CloseableHttpClient build() {
		// Build Connection Manager
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(1000);
		HttpRoute route = new HttpRoute(new HttpHost("localhost", 9090, "http"));
		connManager.setMaxPerRoute(route, 3000);
		connManager.setValidateAfterInactivity(3000); 
		
		// Build Request Config
		RequestConfig reqConfig = RequestConfig.custom()
											   .setConnectTimeout(1000)
											   .setConnectionRequestTimeout(3000)
											   .setSocketTimeout(3000)
											   .build();
		// Build Client
		CloseableHttpClient client = HttpClients.custom()
								 .setConnectionManager(connManager)
								 .setDefaultRequestConfig(reqConfig)
								 .build();
		return client;
	}


	public EmployeeClient() {
		super();
		this.client = build();
	}
	
	public Employee findEmployee(String id) {
		Employee resp =null;
		String path = "employee/get";
		try {
		NameValuePair param = new BasicNameValuePair("id", id);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(param);
		InputStream json = sendRequest(path, params);
		resp = (Employee) convertJsonToObject(json, Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	private InputStream sendRequest(String path, List<NameValuePair> params) throws Exception {		
		URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(9090)
					.setPath(path)
					.setParameters(params)
					.build();
		
		HttpUriRequest req = new HttpGet(uri);
		CloseableHttpResponse response = client.execute(req);
		return response.getEntity().getContent();
	}





	public Employee addEmployee(Employee emp) {
		Employee resp =null;
		String path = "employee/add";
		String empJson = this.convertObjectToJson(emp);
		try {
		StringEntity params =new StringEntity(empJson);	    
		InputStream json = sendPOSTRequest(path, params);
		resp = (Employee) convertJsonToObject(json, Employee.class);
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resp;
	}
	
	private InputStream sendPOSTRequest(String path, StringEntity entity) throws Exception {		
		URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(9090)
					.setPath(path)
					.build();
		
		HttpPost request = new HttpPost(uri);
		request.addHeader("content-type", "application/json");
		request.setEntity(entity);
		CloseableHttpResponse response = client.execute(request);
		return response.getEntity().getContent();
	}
	
	private Object convertJsonToObject(InputStream json, Class<? extends Object> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try {
			obj = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	private String convertObjectToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String objJson = null;
		try {
			objJson = mapper.writeValueAsString(obj);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objJson;
	}
	
	
}
