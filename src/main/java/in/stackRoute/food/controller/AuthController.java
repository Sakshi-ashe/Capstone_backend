package in.stackRoute.food.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

import in.stackRoute.food.model.Auth;
import in.stackRoute.food.model.Book;
import in.stackRoute.food.model.BookApiModel;
import in.stackRoute.food.model.Favourite;
import in.stackRoute.food.model.Response;
import in.stackRoute.food.model.User;
import in.stackRoute.food.service.BookService;
import in.stackRoute.food.service.FavouriteService;
import in.stackRoute.food.service.UserService;
import in.stackRoute.food.util.JwtUtil;

@CrossOrigin
@RestController
public class AuthController {
	String token="";
	 @Value("${api.key}")
	 private String apiKey;
	 
    @Autowired
    private RestTemplate restTemplate;

	   
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouriteService fService;
	
	@Autowired
	private BookService bService;
	
	
	@GetMapping("/")
	public String welcome() {
		return "index";
	}
	
	@PostMapping("/authentication")//generating token
	public Response generateToken(@RequestBody Auth auth) throws Exception {
		  try {
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getPassword())
	            );
	        } catch (Exception ex) {
	        	System.out.println("invalid credentials");
	            throw new Exception("inavalid username/password");
	            
	        }
	        token= jwtUtil.generateToken(auth.getUserName());
	        System.out.println(token);
	        return new Response(token);
	    }
	
	
	
	@GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks() {
		List<Book> books=bService.getAllBooks();
		
        return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
        
        }

	@GetMapping("/getBook/{bookId}")
    public ResponseEntity<?> getBookInfo(@PathVariable("bookId") int bookId,@RequestHeader HttpHeaders headers ) {
		
        Book book=bService.getBookByID(bookId);
     if(book==null)
    	 return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<Book>(book,HttpStatus.OK);
        
        }



@GetMapping("/fav")
public ResponseEntity<?> getFavs( @RequestHeader("Authorization") String authorizationHeader) {
    String token=authorizationHeader.substring(7);
	String username = jwtUtil.extractUsername(token);
	User u=userService.getUserByName(username);
	
	
	List<Favourite> fav=fService.getAllFavourite(u);
	return new ResponseEntity<List<Favourite>>(fav, HttpStatus.OK);
}


@PostMapping("/AddToFavourite/{book_id}")
public ResponseEntity<?> addToFavourite(@PathVariable int book_id, @RequestHeader("Authorization") String authorizationHeader
		) {
	Book book =bService.getBookByID(book_id);
	System.out.println(book.getAuthor());
    String token=authorizationHeader.substring(7);
	String username = jwtUtil.extractUsername(token);
	User u=userService.getUserByName(username);

	if(fService.contains(book,u)!=null) {
		return new ResponseEntity<Response>(new Response("already added"),HttpStatus.CONFLICT);
	}
	
	Favourite fav=fService.save(u, book);
	System.out.println(book);
		return new ResponseEntity<Favourite>(fav,HttpStatus.CREATED);
    
    
    }


@DeleteMapping("/RemoveFromFavourite/{book_id}")
public ResponseEntity<?> removeFromFavourite(@PathVariable int book_id, @RequestHeader("Authorization") String authorizationHeader
		) {
	Book book =bService.getBookByID(book_id);
	System.out.println(book.getAuthor());
    String token=authorizationHeader.substring(7);
	String username = jwtUtil.extractUsername(token);
	User u=userService.getUserByName(username);
	Favourite f=fService.contains(book,u);
	if(f!=null)
	 {
		fService.remove(f);
		return new ResponseEntity<Response>(new Response("deleted"),HttpStatus.OK);
	}
	
		return new ResponseEntity<Response>(new Response("Not found"),HttpStatus.NOT_FOUND);
    
    
    }

	
	  @PostMapping("/addBooksToDb") 
	  public ResponseEntity<?> addBookToDB() {
		  String api="https://api.nytimes.com/svc/books/v3/lists/best-sellers/history.json?api-key="+apiKey;
			System.out.println(api);
		        BookApiModel bookApiModel = restTemplate.getForObject(api, BookApiModel.class);
		      //  bookApiModel= new BookApiModel(bookApiModel.getStatus(),bookApiModel.getCopyright(),bookApiModel.getNum_results(),bookApiModel.getResults());
		        
		  bService.save(bookApiModel.getResults());
				return new ResponseEntity<Response>(new Response("ADDED"),HttpStatus.CREATED);
		    
		    
		    }

@PostMapping(path="/register",produces = "application/json")
public ResponseEntity<?> createUser(@RequestBody ObjectNode objectNode ) {


	User user = new User();
	
	//username
	if(objectNode.get("userName")!=null)
		{
			if(userService.matchUserName(objectNode.get("userName").asText())) {
				Response r=new Response("Username already exists!");
			    return new ResponseEntity<Response>(r,HttpStatus.CONFLICT);
			}
			user.setUserName(objectNode.get("userName").asText());

		}

	else
	    return new ResponseEntity<Response>(new Response("Please enter username!"),HttpStatus.CONFLICT);


    //password
	if(objectNode.get("password")!=null)
		user.setPassword(objectNode.get("password").asText());
	else
	    return new ResponseEntity<Response>(new Response("Please enter password!"),HttpStatus.CONFLICT);
	    
	    
	    
    //email
	if(objectNode.get("email")!=null) {

		if(userService.matchEmail(objectNode.get("email").asText())) {
		    return new ResponseEntity<Response>(new Response("email already exists!"),HttpStatus.CONFLICT);
		}
		user.setEmail(objectNode.get("email").asText());


	}
	else
	    return new ResponseEntity<Response>(new Response("Please enter email!"),HttpStatus.CONFLICT);
	    
	    
	    
	   
    //name
	if(objectNode.get("name")!=null)
		user.setName(objectNode.get("name").asText());
	else
		user.setName("Bookaholic");
		
		

	
    User savedUser = userService.createUser(user);
    return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);

}



}
