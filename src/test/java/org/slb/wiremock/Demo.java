package org.slb.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Demo {

	WireMockServer mock;
	WireMockConfiguration conf;

	@Before
	public void setup() {
		System.out.println("\n******** start wiremock server to serve requests ********");
		conf = options().port(8080).usingFilesUnderClasspath("src/test/resources");
		mock = new WireMockServer(conf);
		mock.start();
	}

	@Test
	public void TestHeaderMatching() {
		System.out.println("******** TestExactUrlMatching ********\n");
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "text/plain");
			Response resp = RestAssured.given().baseUri("http://localhost:8080").headers(headers).when()
					.get("/demo-get-endpoint");

			System.out.println(resp.body().asString());
			System.out.println("\n" + resp.getHeaders());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void TestHeaderMatching_2() {
		System.out.println("******** TestUrlPaternMatching ********\n");
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			Response resp = RestAssured.given().baseUri("http://localhost:8080").headers(headers).when()
					.get("/demo-get-endpoint");

			System.out.println(resp.body().asString());
			System.out.println("\n" + resp.getHeaders());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void TestUrlRegExMatching() {
		System.out.println("******** TestExactUrlMatching ********\n");
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "text/plain");
			Response resp = RestAssured.given().baseUri("http://localhost:8080").when()
					.get("/demo-urlMatching/anything");

			System.out.println(resp.body().asString());
			System.out.println("\n" + resp.getHeaders());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestExactUrlMatching() {
		System.out.println("******** TestExactUrlMatching ********\n");
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "text/plain");
			Response resp = RestAssured.given().baseUri("http://localhost:8080").when()
					.get("/demo-urlMatching");

			System.out.println(resp.body().asString());
			System.out.println("\n" + resp.getHeaders());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@After
	public void tearDown() {
		System.out.println("\n******** stop wiremock server and free up the port ********");
		mock.stop();
	}

}
