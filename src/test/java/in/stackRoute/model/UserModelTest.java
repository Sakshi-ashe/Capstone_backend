
package in.stackRoute.model;

import static org.mockito.Mockito.mock;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.meanbean.test.BeanTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import in.stackRoute.food.FoodDeliveryApplication;
import in.stackRoute.food.controller.AuthController;
import in.stackRoute.food.model.Book;
import in.stackRoute.food.model.Favourite;
import in.stackRoute.food.model.User;
import in.stackRoute.food.service.BookService;
import in.stackRoute.food.service.FavouriteService;
import in.stackRoute.food.service.UserService;
import in.stackRoute.food.util.JwtUtil;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=FoodDeliveryApplication.class)
@AutoConfigureMockMvc
class UserModelTest {
	
	
	@Test
	void contextLoads() {
	}

	private User user;

	


	@Before 
	public void setUp() throws Exception { 
		

		 user=new User(1,"xyz","xyz","xyz","xyz","xyz", "xyz");




		
	}

	@Test 
	public void userTest() {
		//new BeanTester().testBean(User.class);
	}
	
}