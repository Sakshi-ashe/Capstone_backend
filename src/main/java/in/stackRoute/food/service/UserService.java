package in.stackRoute.food.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.stackRoute.food.model.Book;
import in.stackRoute.food.model.Favourite;
import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.FavouriteRepository;
import in.stackRoute.food.repository.UserRepository;
@Service
public class UserService{
	@Autowired
	UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(@Lazy PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User createUser(User user) {
		// TODO Auto-generated method stub
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		user.setCreatedAt(LocalDateTime.now());
		return userRepository.save(user);
	}
	
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	//for registraton end point:
	public boolean matchUserName(String asText) {
		if((userRepository.findByUserName(asText))!=null) return true;
		else return false;
	}

	public boolean matchEmail(String asText) {
		if((userRepository.findByEmail(asText))!=null) return true;
		else return false;
	}


	}
