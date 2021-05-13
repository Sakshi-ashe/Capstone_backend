package in.stackRoute.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stackRoute.food.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	User findByUserName(String username);
//	User findByUser_id(String user_id);
	User findByEmail(String email);

}
