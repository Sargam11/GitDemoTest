import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Reusablemethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//validate if Add Place API is working as expected
		
		//given - all input details
		//when -  submit the API - includes resource and http method
		//Then - Validate the response
		
		
		
		//Add place--? update place with new address--? Get place to validate if new address is present in response
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payload.AddPlace())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); //for parsing Json
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		//Update place
		
		String newAddress = "80 Summer walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get Place
		
		
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = Reusablemethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
	}
	
	//we can also use payload from external file like txt.Json in the body instead of using payload.AddPlace() or the payload in body itself by
	// using body(new String(Files.readAllBytes(paths.get("url/location of file")))) this method converts the external json file's content into bytes and then
	//we use it as string 

}
