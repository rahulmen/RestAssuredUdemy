package com.learning.restAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class RestAssuredExample1 {


	private static String addBody = "{\n" + 
			"    \"location\":{\n" + 
			"        \"lat\" : -38.383493,\n" + 
			"        \"lng\" : 33.427343\n" + 
			"    },\n" + 
			"    \"accuracy\":50,\n" + 
			"    \"name\":\"Frontline hous\",\n" + 
			"    \"phone_number\":\"(+91) 983 893 3937\",\n" + 
			"    \"address\" : \"29, side layout, cohen 09\",\n" + 
			"    \"types\": [\"shoe park\",\"shop\"],\n" + 
			"    \"website\" : \"http://google.com\",\n" + 
			"    \"language\" : \"French-IN\"\n" + 
			"}";

	
	private static String deleteBody = "{'place_id':place_id}";
	
	private static String place_id;

	/*		@Test
		public void test1() {
		//BaseURL or host
		RestAssured.baseURI="https://maps.googleapis.com/";
		//given() is used to provide parameters,cookies and headers.
		//when() is used to pass resource
		//then() is used for assertion like status code and Content Type(Header Validation) and body verification.
		given().
		param("location","-33.8670522,151.1957362").
		param("radius", "1500").
		param("type","restaurant").
		param("keyword","cruise").param("key", "AIzaSyCNekA0xaLSEb13Qf95YujnftKxxTNsy1o").
		when().get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
		//.and().body(".results[0].geometry.location.lat",equalTo(-33.8585683));
		.and().body("status",equalTo("OK")).and().body("results[0].id", equalTo("8e980ad0c819c33cdb1cea31e72d654ca61a7065")).and().header("Server","scaffolding on HTTPServer2");		
	}
	 */

	@Test
	public void addPlace() {
		RestAssured.baseURI="http://216.10.245.166";

		//This will return raw data which we need to convert into String
		Response res =
				given().queryParam("key", "qaclick123").body(addBody)
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().body("status",equalTo("OK")).
				and().header("server", "Apache").extract().response();			

		String responseOutput = res.asString();
		JsonPath js = new JsonPath(responseOutput);
		place_id=js.get("place_id");
	}
	
	
	@Test(dependsOnMethods="addPlace")
	public void deletePlace() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		
		given().queryParam("key", "qaclick123").and().header("Content-Type", "application/json").and().body(deleteBody).
		when().post("/maps/api/place/delete/json").then().assertThat().and().statusCode(200).and().assertThat().
		header("Server", "Apache");
	}
	}
