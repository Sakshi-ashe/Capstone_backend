package in.stackRoute.food;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.UserRepository;

@SpringBootApplication
public class FoodDeliveryApplication {
	 @Autowired
	    private UserRepository repository;

		/*
		 * @PostConstruct public void initUsers() { List<User> users = Stream.of( new
		 * User(101, "javatechie", "password", "Admin"), new User(102, "user1", "pwd1",
		 * "Customer"), new User(103, "user2", "pwd2", "Admin"), new User(104, "user3",
		 * "pwd3", "Customer") ).collect(Collectors.toList());
		 * repository.saveAll(users); }
		 * 
		 * 
		 */
	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
