package in.stackRoute.controller;

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
class AuthControllerTest {
	
		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private UserService userService;

		@MockBean
		private FavouriteService fService;	

		@MockBean
		private BookService bookService;	

		@MockBean
		private RestTemplate restTemplate;

		@Autowired
		private WebApplicationContext webApplicationContext;


		@MockBean	
		private JwtUtil jwtUtil;

		@MockBean	
		private AuthenticationManager authenticationManager;



	@Before 
	public void setUp() throws Exception { 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	String bearerToken =

"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMjAwIiwiZXhwIjoxNjIxMjY4NzA2LCJpYXQiOjE2MjEyMzI3MDZ9.Ywu7vjtXfTgJzKm7dhsbjwOwDh6pmkjTFuA9S92wnpE";
	@Test
	void testGetBookByID() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/getBook/1")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGwtBookByID_withoutAuth() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/getBook/1"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testGetAllBooks() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/getAllBooks")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetAllBooks_withoutAuth() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/getAllBooks"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testFav() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/fav")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testFav_withoutAuth() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/fav"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}


	@Test
	void testPostFav() throws Exception{
		User mockUser = new User(502,"sakshi502","SakshiGupta","sakshi502official@gmail.com","pass","", "");
		Mockito.when(userService.getUserByName(ArgumentMatchers.anyString())).thenReturn(mockUser);
		Book mockBook = new Book(502,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook);
		Favourite fav = new Favourite(mockBook,mockUser);
		Mockito.when(fService.save(mockUser,mockBook)).thenReturn(fav);

		mockMvc.perform(MockMvcRequestBuilders.post("/AddToFavourite/502")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))

		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testPostFav_withoutAuth() throws Exception{
		User mockUser = new User(502,"sakshi502","SakshiGupta","sakshi502official@gmail.com","pass","", "");
		Mockito.when(userService.getUserByName(ArgumentMatchers.any())).thenReturn(mockUser);
		Book mockBook = new Book(502,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook);
		Favourite fav = new Favourite(mockBook,mockUser);
		Mockito.when(fService.save(mockUser,mockBook)).thenReturn(fav);

		mockMvc.perform(MockMvcRequestBuilders.post("/AddToFavourite/502"))

		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testRemoveFromFav() throws Exception{
		User mockUser = new User(502,"sakshi502","SakshiGupta","sakshi502official@gmail.com","pass","", "");
		Mockito.when(userService.getUserByName(ArgumentMatchers.anyString())).thenReturn(mockUser);
		Book mockBook1 = new Book(502,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook1);
		Book mockBook2 = new Book(503,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook2);
		Book mockBook3 = new Book(504,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook3);
		List<Favourite> favs = new ArrayList<>();
		favs.add(new Favourite(mockBook1,mockUser));
		favs.add(new Favourite(mockBook2,mockUser));
		favs.add(new Favourite(mockBook3,mockUser));


		Mockito.when(fService.contains(mockBook1,mockUser)).thenReturn(favs.get(0));
		Mockito.when(fService.contains(mockBook2,mockUser)).thenReturn(favs.get(1));
		Mockito.when(fService.contains(mockBook3,mockUser)).thenReturn(favs.get(2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/RemoveFromFavourite/504")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))

		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void testGetFav_withoutAuth() throws Exception{
		User mockUser = new User(502,"sakshi502","SakshiGupta","sakshi502official@gmail.com","pass","", "");
		Mockito.when(userService.getUserByName(ArgumentMatchers.anyString())).thenReturn(mockUser);
		Book mockBook1 = new Book(502,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook1);
		Book mockBook2 = new Book(503,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook2);
		Book mockBook3 = new Book(504,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook3);
		List<Favourite> favs = new ArrayList<>();
		favs.add(new Favourite(mockBook1,mockUser));
		favs.add(new Favourite(mockBook2,mockUser));
		favs.add(new Favourite(mockBook3,mockUser));


		Mockito.when(fService.contains(mockBook1,mockUser)).thenReturn(favs.get(0));
		Mockito.when(fService.contains(mockBook2,mockUser)).thenReturn(favs.get(1));
		Mockito.when(fService.contains(mockBook3,mockUser)).thenReturn(favs.get(2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/RemoveFromFavourite/504"))

		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}



	@Test
	void testPostRegister_positive() throws Exception{
		User user = new User();
		user.setUserName("Aniket90");
		user.setPassword("pass");
		user.setEmail("aniket@gmail.com");
		user.setName("Aniket Pant");
		user.setUser_id(900);

		Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/register")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}


}
