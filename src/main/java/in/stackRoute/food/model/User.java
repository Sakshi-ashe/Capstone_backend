package in.stackRoute.food.model;

import java.time.LocalDateTime;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;
	@Column(unique = true)
	private String userName;
	private String name;
	private String email;
	private String password;
	private String imagePath;
	private LocalDateTime createdAt;
	private String privilege;
	
    @OneToMany
    @JsonIgnore
    private Set<Favourite> favourite=new HashSet<>();

	

	public User() {}
//	public User(int user_id, String userName, String password, String privilege) {
//		super();
//		this.user_id = user_id;
//		this.userName = userName;
//		this.password = password;
//		this.privilege = privilege;
//	}
//	



	public User(int user_id, String userName, String name, String email, String password, String imagePath,String privilege) {
		super();
		this.user_id = user_id;
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
		//this.createdAt = createdAt;
		this.privilege = privilege;
	}
	

	public Set<Favourite> getFav() {
		return favourite;
	}

	public void setFav(Set<Favourite> fav) {
		this.favourite= fav;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	
	

}
