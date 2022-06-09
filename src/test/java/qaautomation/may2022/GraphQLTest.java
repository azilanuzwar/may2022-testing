package qaautomation.may2022;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import qaautomation.may2022.GraphQLRequestBuilder;
import qaautomation.may2022.pages.DataUtility;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class GraphQLTest {

	@Test
	public void checkCEO() {
		GraphQLRequestBuilder gql = new GraphQLRequestBuilder();
		//Mendefinisikan query nya: 
		String query = "{\r\n" + "  company {\r\n" + "    ceo\r\n" + "    cto\r\n" + "  }\r\n" + "}\r\n" + "";
		gql.setQuery(query);
		String jsonStringQuery = gql.getJSONString();

		Response responseCeo = given().contentType(ContentType.JSON).body(jsonStringQuery).when()
				.post("https://api.spacex.land/graphql/");

		String expectedCeo = "Elon Musk";
		String expectedCto = "Elon Musk";
		String ceoName = responseCeo.jsonPath().get("data.company.ceo");
		String ctoName = responseCeo.jsonPath().get("data.company.cto");

		assertEquals(expectedCeo, ceoName);
		assertEquals(expectedCto, ctoName);
		
		responseCeo.then().assertThat().body(matchesJsonSchema(DataUtility.getDataFromExcel("Schemas", "gqlceo")));
	}

	@Test
	public void getLaunchesWithVariable() {
		GraphQLRequestBuilder gql = new GraphQLRequestBuilder();
		JSONObject variables = new JSONObject();
		variables.put("limit", 6);

		String query = "query getLaunches($limit: Int!) {\r\n" + "  launchesPast(limit: $limit) {\r\n"
				+ "    mission_id\r\n" + "    mission_name\r\n" + "  }\r\n" + "}\r\n" + "";

		gql.setQuery(query);
		gql.setVariables(variables.toString());
		String jsonStringQuery = gql.getJSONString();
		Response responseLaunches = given().contentType(ContentType.JSON).body(jsonStringQuery).when()
				.post("https://api.spacex.land/graphql/");
		List launches = responseLaunches.jsonPath().getList("data.launchesPast");
		assertEquals(6, launches.size());
		
		responseLaunches.then().assertThat().body(matchesJsonSchema(DataUtility.getDataFromExcel("Schemas", "launches")));
	}

}
