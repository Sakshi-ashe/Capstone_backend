package in.stackRoute.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import in.stackRoute.food.FoodDeliveryApplication;
import in.stackRoute.food.model.Book;
import in.stackRoute.food.model.Favourite;
import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.FavouriteRepository;
import in.stackRoute.food.service.FavouriteService;


@SpringBootTest(classes=FoodDeliveryApplication.class)
public class FavouriteServiceTest {
	@MockBean
	private FavouriteRepository favouriteRepository;
	
	@Autowired
	FavouriteService favouriteService;
	@Test
	public void testGetAllFavouriteSuccess(){
	//	List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		User user=new User(1,"user1","name1","email1","password1","imagePath1",
			"privilege1");
		//Book book2=new Book(2,"Book2","Description2","Contributor2","Author2");
		//books.add(book1);
		//books.add(book2);
		Favourite fav=new Favourite(book1,user);
		List<Favourite> f=new ArrayList<Favourite>();
		f.add(fav);
		when(favouriteRepository.findAll()).thenReturn(f);
		assertEquals(1,favouriteService.getAllFavourite(user).size());
		
	}
	@Test
	public void testGetAllFavouriteSuccessFailure(){
	//	List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		User user=new User();
		//Book book2=new Book(2,"Book2","Description2","Contributor2","Author2");
		//books.add(book1);
		//books.add(book2);
		Favourite fav=new Favourite(book1,user);
		List<Favourite> f=new ArrayList<Favourite>();
		f.add(fav);
		
		when(favouriteRepository.findAll()).thenReturn(f);
		System.out.println(favouriteService.getAllFavourite(user).get(0).getUser().getEmail());
		assertEquals(null,favouriteService.getAllFavourite(user).get(0).getUser().getEmail());
		
	}
	@Test
	public void testSaveFavourite(){
	//	List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
		
		Favourite fav=new Favourite(book1,user);
		//List<Favourite> f=new ArrayList<Favourite>();
		when(favouriteRepository.save(fav)).thenReturn(fav);
		favouriteService.save(user, book1);
        verify(favouriteRepository,times(0)).save(fav);		
	}
	@Test
	public void testSaveFavouriteFailure(){
	//	List<Book> books=new ArrayList<Book>();
		//System.out.println(favouriteRepository.findAll().get(0).getBook().getAuthor());
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		User user=new User();
		
		Favourite fav=new Favourite(book1,user);
		//List<Favourite> f=new ArrayList<Favourite>();
		when(favouriteRepository.save(fav)).thenReturn(fav);
		favouriteService.save(user, book1);
        verify(favouriteRepository,times(0)).save(fav);		
	}
	@Test
	public void testContainFavourite(){
	//	List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
		
		Favourite fav=new Favourite(book1,user);
		List<Favourite> f=new ArrayList<Favourite>();
		f.add(fav);
		when(favouriteRepository.findAll()).thenReturn(f);
		assertEquals("Author1",favouriteService.contains(book1,user).getBook().getAuthor());
		
	}
	@Test
	public void testContainFavouriteFailure(){
	//	List<Book> books=new ArrayList<Book>();
		Book book1=new Book(2,"Book1","Description1","Contributor1","Author1");
		User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
		
		Favourite fav=new Favourite(book1,user);
		List<Favourite> f=new ArrayList<Favourite>();
		//f.add(fav);
		
		when(favouriteRepository.findAll()).thenReturn(f);
		assertEquals(null,favouriteService.contains(book1,user));
		
	}
	@Test
	public void testDeleteFavourite() {
		Book book1=new Book(2,"Book1","Description1","Contributor1","Author1");
		User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
		
		Favourite fav=new Favourite(book1,user);
		List<Favourite> f=new ArrayList<Favourite>();
		f.add(fav);
		
	    favouriteService.remove(fav);
		verify(favouriteRepository,times(1)).deleteById(fav.getId());
	}
	
}
