package in.stackRoute.food.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book {
	@Id
	@Column(length = 255)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int book_id;
	private String title;
	private String description;
	private String contributor;
	private String author;
	
	@OneToMany
	@JsonIgnore
    private Set<Favourite> favourite=new HashSet<>();
	 

		/*
		 * public int getBook_id() { return book_id; } public void setBook_id(int book_id) {
		 * this.book_id = book_id; }
		 */
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getcontributor() {
		return contributor;
	}
	public void setcontributor(String contributor) {
		this.contributor = contributor;
	}

	/*
	 * public List<User> getUsers() { return users; } public void
	 * setUsers(List<User> users) { this.users = users; }
	 */
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Book(int book_id,String title, String description, String contributor, String author) {
		super();
		this.book_id = book_id;
		this.title = title;
		this.description = description;
		this.contributor = contributor;
		this.author = author;
	}
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
