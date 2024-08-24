package APITestingUsingRestAssured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.ReUseableMethods;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	@Test(dataProvider="BooksData",priority = 1)
	public void addBook(String isbn, String aisle) 
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().header("Content-Type","application/json").
		body(ReUseableMethods.addBook(isbn,aisle)).
		when().post("Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=ReUseableMethods.rawToJson(response);
		String id= js.get("ID");
		System.out.println("The ID is: "+id);
		System.out.println("********************************************");	
		
		given().header("Content-Type", "application/json")
		.body(ReUseableMethods.deleteBook(isbn, aisle)).when()
		.post("/Library/DeleteBook.php")
		.then().statusCode(200).extract().response();
		System.out.println("Deleted book " + isbn+aisle + "\n");
	}
	
	/*
	  @Test(dataProvider="BooksData",priority = 2)
	public void deleteBook(String isbn, String aisle) {
	 
		given().header("Content-Type", "application/json")
		.body(ReUseableMethods.deleteBook(isbn, aisle)).when()
		.post("/Library/DeleteBook.php")
		.then().statusCode(200).extract().response();
		System.out.println("Deleted book " + isbn+aisle + "\n");
		System.out.println("********************************************");
		} 
		
		*/
	
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		// Create a multidimensional array which is a collection of arrays
		return new Object[][] {{"fps","2341"},{"isw","6745"},{"lmg","0971"}};
		
		
	}
}
