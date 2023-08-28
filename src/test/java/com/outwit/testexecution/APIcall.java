package com.outwit.testexecution;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class APIcall {
	private String APIName;

	public APIcall(String APIName) {
		this.APIName = APIName;
	}

	public static String BaseURI() {
		String BaseURL = "http://localhost:7001/smcfs/servlets/IBMApiTesterServlet";
		return BaseURL;
	}

	public  Map<String, String> getQueryParams() {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("YFSEnvironment.progId", "SterlingHttpTester");
		queryParams.put("InteropApiName", APIName);
		queryParams.put("IsFlow", "N");
		queryParams.put("ServiceName", " ");
		queryParams.put("ApiName", APIName);
		queryParams.put("YFSEnvironment.userId", "admin");
		queryParams.put("YFSEnvironment.password", "password");
		queryParams.put("YFSEnvironment.version", "");
		queryParams.put("YFSEnvironment.locale", "");
		
		return queryParams;
	}

	public String response(String filepath) throws Exception {
             
		String requestBody = FileOperations.readXMLFile(filepath);

		Response respcode = given().contentType("text/xml").header("Accept", "text/xml")
				.queryParams(getQueryParams()).queryParams("InteropApiData", requestBody)
				.when()
				.post(APIcall.BaseURI());
		
		String resp = respcode.then().assertThat().and().contentType("text/xml; charset=UTF-8").extract().asString();
		return resp;

	}
}
