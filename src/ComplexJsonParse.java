import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		JsonPath js = new JsonPath(payload.CoursePrice());

		
		//Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
			
		
		//Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
	
		//Print title of first course
		String firstCourseTitle = js.get("courses[0].title");
		System.out.println(firstCourseTitle);
	
	
	
		//Print all course titles and their respective prices
		for(int i=0; i<courseCount; i++)
		{
			String CourseTitles = js.get("courses["+i+"].title");
			System.out.println(CourseTitles);
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		
		
		System.out.println("print no. of copies sold by RPA course");
		
		for(int i=0; i<courseCount; i++)
		{
			String CourseTitles = js.get("courses["+i+"].title");
			if(CourseTitles.equalsIgnoreCase("RPA"))
			{
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		
		//verify if sum of all course prices matches the purchase amount
		int totalPrice = 0;
		for(int i=0; i<courseCount; i++)
		{		
		int price = js.getInt("courses["+i+"].price");
		int copies = js.getInt("courses["+i+"].copies");
		int amount = price * copies;
		 totalPrice = amount + totalPrice;
		}
		System.out.println(totalPrice);
		
			Assert.assertEquals(totalAmount, totalPrice);
		
	}

}
