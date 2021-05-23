package in.stackRoute.RestAssured;

import static io.restassured.RestAssured.given;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import in.stackRoute.food.FoodDeliveryApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FoodDeliveryApplication.class)


public class WithoutAuthTest {

	//Get '/' without authorization
	@Test
	public void test_getHomePage_WithoutAuth() {
		given()
			.when()
				.get("http://localhost:9191/")
			.then()
				.statusCode(403);
	}
	
	
	@Test
	public void test_AddToFav_WithoutAuth() {
		given()
			.when()
				.post("http://localhost:9191/AddToFavourite/1")
			.then()
				.statusCode(403);
	}
	
	@Test
	public void test_RemoveFromFav_WithoutAuth() {
		given()
			.when()
				.delete("http://localhost:9191/RemoveFromFavourite/1")
			.then()
				.statusCode(403);
	}
	
	@Test
	public void test_AddBookToDB_WithoutAuth() {
		given()
			.when()
				.post("http://localhost:9191/addBooksToDb")
			.then()
				.statusCode(403);
	}
	
	@Test
	public void test_getAllBooks_WithoutAuth() {
		given()
			.when()
				.get("http://localhost:9191/getAllBooks")
			.then()
				.statusCode(403);
	}


	//Get 'getBook/{bookId}' without authorization
	@Test
	public void test_getBookByID_WithoutAuth() {
		given()
			.when()
				.get("http://localhost:9191/getBook/20")			
			.then()
				.statusCode(403);
	}

	@Test
	public void test_getFav_WithoutAuth() {
		given()
			.when()
				.get("http://localhost:9191/fav")
			.then()
				.statusCode(403);
	}
	

}
