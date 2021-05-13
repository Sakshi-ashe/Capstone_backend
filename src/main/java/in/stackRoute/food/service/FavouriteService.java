package in.stackRoute.food.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stackRoute.food.model.Book;
import in.stackRoute.food.model.Favourite;
import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.FavouriteRepository;

@Service
public class FavouriteService{
	@Autowired
	public FavouriteRepository fRepository;
	
	
	public List<Favourite> getAllFavourite(User user) {
		List<Favourite> fList= fRepository.findAll();
		List<Favourite>result=new ArrayList<>();
		for(int i=0;i<fList.size();i++) {
			if(fList.get(i).getUser().getUser_id()==user.getUser_id()) {
				result.add(fList.get(i));
			}
		}
		return result;
	}
	
	public Favourite save(User u,Book b) {
		Favourite fav=new Favourite(b,u);
		return fRepository.save(fav);
	}

	public Favourite contains(Book book,User user) {
		List<Favourite> fList=getAllFavourite(user);
		for(int i=0;i<fList.size();i++) {
			Favourite f=fList.get(i);
			if(f.getBook().getBook_id()==book.getBook_id() && f.getUser().getUser_id()==user.getUser_id()) {
				return f;
			}
		}
		return null;
	}

	public void remove(Favourite f) {
		fRepository.deleteById(f.getId());
		
	}
	
	
	
}
