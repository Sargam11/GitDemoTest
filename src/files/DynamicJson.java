package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	
	@Test(dataProvider = "Booksdata")
	public void AddBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		
		String response =  given().log().all().header("Content-Type", "application/json").body(payload.Addbook(isbn, aisle))
		.when().post("/Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = Reusablemethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name = "Booksdata")
	public Object[][] getData()
	{
		return new Object[][] {{"lmnop","5675"},{"bcdef", "5678"}, {"ghijk", "9087"}};
	}

}
