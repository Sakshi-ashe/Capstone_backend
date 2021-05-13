//package in.stackRoute.food.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import in.stackRoute.food.model.Favourite;
//import in.stackRoute.food.model.Book;
//import in.stackRoute.food.model.User;
//import in.stackRoute.food.service.FavouriteService;
//import in.stackRoute.food.service.UserService;
//import in.stackRoute.food.util.JwtUtil;
//
//@RestController
//public class FavouriteController {
//	@Autowired
//	private JwtUtil jwtUtil;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private FavouriteService fService;
//	@PostMapping("/AddToFavourite/{msovieId}")
//    public ResponseEntity<?> addToFavourite(@PathVariable int movieId, @RequestHeader("Authorization") String authorizationHeader
//    		) {
//        System.out.println("in add to favourite");
//        System.out.println("auth header:"+authorizationHeader);
//        String token=authorizationHeader.substring(7);
//        System.out.println("token "+token);
//		String username = jwtUtil.extractUsername(token);
//        System.out.println(username);
//
//		User u=userService.getUserByName(username);
//		
//		Favourite fav=new Favourite(movieId,u.getUserId());
//        System.out.println(u.getUserId());
//        return new ResponseEntity<Favourite>(fService.save(fav),HttpStatus.CREATED);
//        
//        }
//
//}
