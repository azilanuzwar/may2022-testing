package qaautomation.may2022;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.response.Response;
import qaautomation.may2022.pages.DataUtility;

public class APITestAfterRefactor2 extends BaseAPITest {

	@Test
	public void dashboardAPI() {
		Response responseDashboard = given().spec(loginJsonSpec).param("status", "completed").when()
				.get(DataUtility.getDataFromExcel("Path", "dashboard"));

		assertEquals(responseDashboard.statusCode(), 200);
	}

	@Test
	public void configAPI() {
		Response responseConfig = given().spec(commonJsonSpec).when().get(DataUtility.getDataFromExcel("Path", "config"));

		assertEquals(responseConfig.statusCode(), 200);
	}
	//yang dibawah ini coba pelajari lagi alurnya dari mana zil:
	
	@Test
	public void userAPI () {
		Response responseDashboard = given().spec(loginJsonSpec).param("status", "completed")
				.when().get("/build_cards");
		
		assertEquals(responseDashboard.statusCode(), 200);
	}
	
	@Test
	public void fakerTest() {
		Faker faker = new Faker();
		System.out.println(faker.name().firstName());
		
	}
}
