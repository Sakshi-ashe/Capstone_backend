package in.stackRoute.RestAssured;


import org.testng.annotations.Test;

import in.stackRoute.food.FoodDeliveryApplication;

import org.apache.http.HttpStatus;
import org.assertj.core.error.ShouldBeGreaterOrEqual;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FoodDeliveryApplication.class)

public class RestAssuredTest {
	
	String token ="";

	
	//Post '/authentication' -- already registered user
	@DataProvider(name="positive_auth_data")
	public String[] pos_auth() {
		return new String[] {
					"{\r\n  \"userName\" : \"user22\",\r\n  \"password\" : \"pass\"\r\n}"
				
		};
	}

	@Test(priority = 3, dataProvider = "positive_auth_data")
	public void test_postAuth_positive(String body) throws JSONException {

		Response res = given().contentType("application/json").body(body).when()
				.post("http://localhost:9191/authentication").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON)
				.log().body()
				.extract().response();
				//.extract().body().asString();
		
		
		System.out.println("Response  :" + res);

		token = res.jsonPath().getString("response");
		System.out.println("token:  " + token);

	}
	
	
	
	@DataProvider(name="negtive_auth_data")
	public String[] neg_auth() {
		return new String[] {
					"{\r\n  \"userName\" : \"\",\r\n  \"password\" : \"pass\"\r\n}",
					"{\r\n  \"userName\" : \"user6\",\r\n  \"password\" : \"\"\r\n}",
					"{\r\n  \"userName\" : \"\",\r\n  \"password\" : \"\"\r\n}",
					"{\r\n  \"userName\" : \"user6\",\r\n  \"password\" : \"WRONG_PASSWORD\"\r\n}",
					"{\r\n  \"userName\" : \"user1000\",\r\n  \"password\" : \"pass\"\r\n}"

		};
	}

	//Post 'authentication' -- unrecognized user
	@Test(priority=4,dataProvider = "negtive_auth_data")
	public void test_postAuth_negative(String body) {

				given()
					.contentType("application/json")
					.body(body)
				.when()
					.post("http://localhost:9191/authentication")
				.then()
					.assertThat()
					.statusCode(500)
			         .contentType(ContentType.JSON)
			        // .toString()
			         //.equals("inavalid username/password");
					.log().body()
					.body("message",equalTo("inavalid username/password"))
					.extract().response();	
		
	}
	




	//Get '/' with authorization
	@Test(priority = 5)
	public void test_getHomePage_withAuth() {
		given()
        .headers(
                "Authorization",
                "Bearer " + token
                )
        	.when()
				.get("http://localhost:9191/")
			.then()
				.statusCode(200);
	}
	
	
	//Get '/getBook{id}' with authorization
	@Test(priority = 6)
	public void test_getBookById_withAuth() {
		given()
        .headers(
                "Authorization",
                "Bearer " + token
                )
        	.when()
				.get("http://localhost:9191/getBook/4")
			.then()
				.statusCode(200)
				.log().body()
				.assertThat()
				.body("book_id",equalTo(4))
				.body("title",equalTo("#GIRLBOSS"));
				
	}
	
	//Get 'getAllBooks' with authorization
	@Test(priority = 7)
	public void test_getAllBooks_withAuth() {
		given()
        .headers(
                "Authorization",
                "Bearer " + token
                )
        	.when()
				.get("http://localhost:9191/getAllBooks")
			.then()
				.statusCode(200);
				//.log().body()
				//.assertThat()
				//.body("$.-0.title",equalTo("\"I GIVE YOU MY BODY ...\""));
				
	}
	
	
	
	


	
	//Get 'AddToFavourite' with authorization
		@Test(priority = 8)
		public void test_AddToFavourite_withAuth() {
			given()
	        .headers(
	                "Authorization",
	                "Bearer " + token
	                )
	        	.when()
					.post("http://localhost:9191/AddToFavourite/11")
				.then()
					.assertThat()
					.statusCode(201);
					
		}
		
		//Get 'RemoveFromFavourite' with authorization
		@Test(priority = 9)
		public void test_RemoveFromFavourite_withAuth() {
			given()
	        .headers(
	                "Authorization",
	                "Bearer " + token
	                )
	        	.when()
					.delete("http://localhost:9191/RemoveFromFavourite/11")
				.then()
					.assertThat()
					.statusCode(200);
					
		}


		//Post 'addBooksToDb' with authorization
				//@Test(priority = 10 )
				public void test_addBooksToDb_withAuth() {
					given()
			        .headers(
			                "Authorization",
			                "Bearer " + token
			                )

			        	.when()
							.post("http://localhost:9191/addBooksToDb")
						.then()
							.assertThat()
							.statusCode(201);
							
				}
				
				
				//Post 'fav' with authorization

				@Test(priority = 11 )
				public void test_fav_withAuth() {
					given()
			        .headers(
			                "Authorization",
			                "Bearer " + token
			                )

			        	.when()
							.get("http://localhost:9191/fav")
						.then()
							.assertThat()
							.statusCode(200);
							
				}



	
}
