package in.stackRoute.RestAssured;

import static io.restassured.RestAssured.given;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import in.stackRoute.food.FoodDeliveryApplication;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(classes=FoodDeliveryApplication.class)
public class RegisterRestTest {

	@DataProvider(name="positive_register_data")
	public String[] pos_reg() {
		return new String[] {

	//correctJson =
			"{\"userName\": \"user22\",\r\n\"name\": \"xyz\",\r\n\"email\": \"user22@gmail.com\",\r\n\"password\": \"pass\"}",
	//noName =
			"{\"userName\": \"user23\",\r\n\"email\": \"user23@gmail.com\",\r\n\"password\": \"pass\"}"
		};
	}

	
	
	// Post '/register' -- valid parameters passed
	//@Test(dataProvider = "positive_register_data", priority = 1)
	public void test_postRegister_positive(String body) {

		Response res = given().contentType(ContentType.JSON).body(body).when().post("http://localhost:9191/register")
				.then().assertThat().statusCode(201).contentType(ContentType.JSON).log().body().extract().response();

		String jsonString = res.toString();
		System.out.println(jsonString);

	}
	
	
	@DataProvider(name="negative_register_data")
	public String[] neg_reg() {
		return new String[] {
		//repeatedDetails =
				"{\"userName\": \"user13\",\r\n\"name\": \"xyz\",\r\n\"email\": \"user13@gmail.com\",\r\n\"password\": \"pass\"}",
		//repeatedUsername =
				"{\"userName\": \"user13\",\r\n\"name\": \"xyz\",\r\n\"email\": \"user14@gmail.com\",\r\n\"password\": \"pass\"}",
		//repeatedEmail =
				"{\"userName\": \"user14\",\r\n\"name\": \"xyz\",\r\n\"email\": \"user13@gmail.com\",\r\n\"password\": \"pass\"}",
		//noUSername =
				"{\"name\": \"xyz\",\r\n\"email\": \"user15@gmail.com\",\r\n\"password\": \"pass\"}",
		//noEmail =
				"{\"userName\": \"user15\",\r\n\"name\": \"xyz\",\r\n\"password\": \"pass\"}",
		//noPassword =
				"{\"userName\": \"user16\",\r\n\"name\": \"xyz\",\r\n\"email\": \"user16@gmail.com\"}"

		};
	}


	//Post '/register' -- invalid parameters passed
	@Test(dataProvider = "negative_register_data",priority = 2)
	public void test_postRegister_negative(String body) {

		Response res = 
				given()
				.contentType(ContentType.JSON)
				.body(body)
				.when()
					.post("http://localhost:9191/register")
				.then()
					.assertThat()
					.statusCode(409)
			         .contentType(ContentType.JSON)
					.log().body()
					.extract().response();
		
		String jsonString = res.toString();
		System.out.println(jsonString);
		
	}

}
