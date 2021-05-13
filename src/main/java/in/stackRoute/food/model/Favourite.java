package in.stackRoute.food.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Favourite {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;	
	
public Favourite() {
		
	}

public Book getBook() {
	return book;
}

public void setBook(Book book) {
	this.book = book;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Favourite(Book book, User user) {
	super();
	this.book = book;
	this.user = user;
}
	
	
}
