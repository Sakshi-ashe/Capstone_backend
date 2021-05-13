package in.stackRoute.food.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
@Autowired
private UserRepository repository;
	@Override//fetch user from db
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repository.findByUserName(username);
		//validating the User provided by springSecurity with the one fetched from DB
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
