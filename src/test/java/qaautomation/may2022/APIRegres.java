package qaautomation.may2022;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIRegres
{
	String name = ""; //kenapa ini dikosongin?

	@Test
	public void loginAPI() {

		RestAssured.baseURI = "https://reqres.in";
		String payload = "{\"name\":\"morpheus\",\"job\":\"leader\"}";

		Response responseLogin = RestAssured.given().contentType("application/json").body(payload).when()
				.post("/api/users");

		name = responseLogin.jsonPath().get("name");
		String expectedName = "morpheus";

		assertEquals(responseLogin.statusCode(), 201);
		assertEquals(name, expectedName);

	}

}
