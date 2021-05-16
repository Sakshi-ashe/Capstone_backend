package in.stackRoute.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stackRoute.food.repository.BookRepository;
import in.stackRoute.food.model.Book;
@Service
public class BookService {
	@Autowired
	public BookRepository repository;
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	public void save(List<Book> books) {
	
		repository.saveAll(books);
		
	}
	public Book getBookByID(int bookId) {
		if(repository.existsById(bookId)) {
			return repository.findById(bookId).get();
		}
		return null;
		
	}

}
