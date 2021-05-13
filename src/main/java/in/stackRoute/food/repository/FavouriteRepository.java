package in.stackRoute.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stackRoute.food.model.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite,Integer>{

	//List<Favourite> findByUserId(int userId);

}
