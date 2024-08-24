package APITestingUsingRestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import Files.ReUseableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// Validate AddPlace API working as expected or not
		
		// given - all input details
		// when - Submit the API - resource and http methods are under this
		//then - Validate the response
		// For static Json payload:
		// Convert the content of file into String 
		// --> Content of file can convert into Byte then convert Byte data to String
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured.useRelaxedHTTPSValidation();
		String Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\2103125\\Downloads\\Rest Assrured Course\\body.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println("**********************************");
		System.out.println("The parse Json response in string format");
		System.out.println(Response);
		System.out.println("**********************************");
		
		// Add place and then update place with new address and get place to validate if new address
		// is present in response
		// E2E scenario
		
		//Parsing the Json
		// Got the place Id first
		JsonPath js= new JsonPath(Response);
		String placeId=js.getString("place_id");
		System.out.println("**********************************");
		System.out.println("Place id is "+placeId);
		System.out.println("**********************************");
		
		// Update Place
		
		String newAddress="Durban, South Africa";
		
		given().log().all().queryParam("key", "qaclick123").headers("Connection","keep-alive")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		// Get Place
		
		String getplaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		// Again Parsing the Json
		JsonPath js1 = ReUseableMethods.rawToJson(getplaceResponse);
		String actualAddress = js1.get("address");
		System.out.println("**********************************");
		System.out.println("The actual value of the address is "+actualAddress);
		System.out.println("**********************************");
		
		// Assertion 
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}

}
