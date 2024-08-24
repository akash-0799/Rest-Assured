package APITestingUsingRestAssured;

import Files.ReUseableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(ReUseableMethods.CoursePrice());
		// Print the count of number of courses 
		int count = js.getInt("courses.size()"); // applied on array
		System.out.println("The number of courses are:");
		System.out.println(count);
		System.out.println("********************************************");
		
		// Print the total amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("The value of the total purchased amount is:");
		System.out.println(totalAmount);
		System.out.println("********************************************");
		
		// Title of the course
		System.out.println("The titles of the courses are as follows:");
		for(int i=0;i<count;i++) {
			String price = js.getString("courses["+i+"].price");
			String title = js.get("courses["+i+"].title");
			int k=i+1;
			System.out.println(k+". "+title);
			System.out.println("Price: "+price+"$");
			
		}
		System.out.println("**********************************************");
		
		
		// Print number of copies in the RPA course
		for(int i=0;i<count;i++) {
			String title = js.get("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println("The number of copies in the RPA course are: "+js.get("courses["+i+"].copies"));
				break;
			}	
		}
		System.out.println("**********************************************");
	}
	

}
