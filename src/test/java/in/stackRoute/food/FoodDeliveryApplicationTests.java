package in.stackRoute.food;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import in.stackRoute.food.model.Book;
import in.stackRoute.food.repository.BookRepository;
import in.stackRoute.food.service.BookService;

@SpringBootTest
class FoodDeliveryApplicationTests {

	@Test
	void contextLoads() {
	}
	@MockBean
	private BookRepository bookRepository;
	
	@Autowired
	BookService bookService;



 
    @BeforeEach
	public void setup() {
    
	 
	}
	@Test
	public void testGetAllBooksSuccess(){
		List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		Book book2=new Book(2,"Book2","Description2","Contributor2","Author2");
		books.add(book1);
		books.add(book2);
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(2,bookService.getAllBooks().size());
		
	}
	@Test
	public void testSaveBookSuccess(){
		List<Book> books=new ArrayList<Book>();
		Book book1=new Book(1,"Book1","Description1","Contributor1","Author1");
		Book book2=new Book(2,"Book2","Description2","Contributor2","Author2");
		books.add(book1);
        books.add(book2);
		when(bookRepository.saveAll(books)).thenReturn(books);
		bookService.save(books);
	    verify(bookRepository,times(1)).saveAll(books);
		
	}
//	@Test
//	public void testGetBookById(){
//		//List<Book> books=new ArrayList<Book>();
//		Optional<Book> book1=Optional.ofNullable(new Book(1,"Book1","Description1","Contributor1","Author1"));
//		//Book book2=new Book(2,"Book2","Description2","Contributor2","Author2");
//		//books.add(book1);
//        //books.add(book2);
//		when(bookRepository.findById(1)).thenReturn(book1);
//	    assertEquals("Author1",bookService.);
//	    
//		
//	}
}
