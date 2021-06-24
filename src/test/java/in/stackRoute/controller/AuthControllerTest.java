package in.stackRoute.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import in.stackRoute.food.FoodDeliveryApplication;
import in.stackRoute.food.controller.AuthController;
import in.stackRoute.food.model.Auth;
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
		

		@InjectMocks	
		private JwtUtil jwtUtil;

		@MockBean	
		private AuthenticationManager authenticationManager;


		String bearerToken =  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNTAwIiwiZXhwIjoxNjIyMDUwODYzLCJpYXQiOjE2MjIwMTQ4NjN9.MPbIEffz9MX7XBJLQyzwW6ychFUlpXz55Kdu7fhHR28";
				//"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNTAwIiwiZXhwIjoxNjIxOTcxMDM3LCJpYXQiOjE2MjE5MzUwMzd9.ofPtTFkJI5MI-YtWoBnlkW9meFYmMW6EvDACi3t2GlE";
				//"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNTAwIiwiZXhwIjoxNjIxODcwMzMzLCJpYXQiOjE2MjE4MzQzMzN9.iG5cYWQjfgQV2NxY1VDCoNAzBcjPZl42otxPWIA0h38";

		@Mock
		AuthController authController;
		
		
	@Before 
	public void setUp() throws Exception { 
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		bearerToken=jwtUtil.generateToken("user100");
			}
	
	 @Test 
	 void testAuthentication() throws Exception {
		 System.out.println("tokennn"+jwtUtil.generateToken("user100"));
		  Auth auth=new Auth("sakshi","sakhi");
		System.out.println("purviresult");
		ResultActions result=mockMvc.perform(post("http://localhost:9191/authentication")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(asJsonString(auth)) 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk())
		           //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		          // .andExpect(jsonPath("$.response").value(""))
		           ; 
		
	System.out.println("purviresult"+result.andReturn().getResponse().getContentAsString());

	}
		@Test
		void testWelcome() throws Exception{
	    	
	    	mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9191/")
		   .header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))
	       .andExpect(status().isOk());
		}
	@Test
	void testGetBookByID() throws Exception{
    	Book mockBook = new Book(502,"xyz","xyz","xyz","xyz");
    	
    	Mockito.when(bookService.getBookByID(502)).thenReturn(mockBook);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9191/getBook/"+mockBook.getBook_id())
	   .header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))
       .andExpect(status().isOk());
	}
	

	@Test
	void testGwtBookByID_withoutAuth() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9191/getBook/1"))
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
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9191/getAllBooks"))
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
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9191/fav"))
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

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:9191/AddToFavourite/502")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+bearerToken))

		.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testPostFav_withoutAuth() throws Exception{
		User mockUser = new User(502,"sakshi502","SakshiGupta","sakshi502official@gmail.com","pass","", "");
		Mockito.when(userService.getUserByName(ArgumentMatchers.any())).thenReturn(mockUser);
		Book mockBook = new Book(502,"xyz","xyz","xyz","xyz");
		Mockito.when(bookService.getBookByID(ArgumentMatchers.anyInt())).thenReturn(mockBook);
		Favourite fav = new Favourite(mockBook,mockUser);
		Mockito.when(fService.save(mockUser,mockBook)).thenReturn(fav);

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:9191/AddToFavourite/502"))

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

		mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:9191/RemoveFromFavourite/504")
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

		mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:9191/RemoveFromFavourite/504"))

		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test 
	void testPostRegister_positive() throws Exception{
		User user = new  User(200,"user1","user2","user1","user1", "user1", "user1");
	Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(user);
	mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:9191/register")
			.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	.andExpect(MockMvcResultMatchers.status().isCreated()); }


	public static String asJsonString(final Object obj) { try {

		return new ObjectMapper().writeValueAsString(obj); } catch (Exception e) {
			throw new RuntimeException(e); } }
	
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private String getJwt() throws Exception {
		Auth auth=new Auth("user500","pass500");
		String userJson=asJsonString(auth);
//		User u=new User(500,"user500","usr500","user500@gmail.com","pass500",null,null);
		org.springframework.security.core.userdetails.User userT=
				new org.springframework.security.core.userdetails.User(
						"user500","pass500",new ArrayList<>());
 Authentication authentication = Mockito.mock(Authentication.class);
	   Mockito.when(authenticationManager.authenticate(  ArgumentMatchers.any())).thenReturn(authentication);
	    MvcResult result=mockMvc.perform(post("/authentication")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(userJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	    String token=jwtUtil.generateToken("user500");
	    System.out.println("line11"+token);
	    return JsonPath.read(result.getResponse().getContentAsString(), "$.response");
	}
	 
	 
}
