package in.stackRoute.service;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import in.stackRoute.food.FoodDeliveryApplication;
import in.stackRoute.food.model.User;
import in.stackRoute.food.repository.UserRepository;
import in.stackRoute.food.service.UserService;


@SpringBootTest(classes=FoodDeliveryApplication.class)
public class UserServiceTest {
    @MockBean
	UserRepository urepo;
    
    @Autowired
    UserService uservice;
    @Test
    public void testMatchUserByName() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByUserName(user.getUserName())).thenReturn(user);
        assertEquals(true,uservice.matchUserName(user.getUserName()));
    	
    }
    @Test
    public void testMatchUserByNameFailure() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByUserName(user.getUserName())).thenReturn(user);
        assertEquals(false,uservice.matchUserName("user2"));
    	
    }
    @Test
    public void testGetUserByName() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByUserName(user.getUserName())).thenReturn(user);
        assertEquals("user1",uservice.getUserByName(user.getUserName()).getUserName());
    	
    }
    @Test
    public void testGetUserByNameFailure() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByUserName(user.getUserName())).thenReturn(user);
        assertEquals(null,uservice.getUserByName("user2"));
    	
    }
    @Test
    public void testMatchUserByEmail() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByEmail(user.getEmail())).thenReturn(user);
        assertEquals(true,uservice.matchEmail(user.getEmail()));
    	
    }
    @Test
    public void testMatchUserByEmailFailure() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.findByEmail(user.getEmail())).thenReturn(user);
        assertEquals(false,uservice.matchEmail("adasdsa"));
    	
    }
    @Test
    public void testSaveUser() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.save(user)).thenReturn(user);
        assertEquals("email1",uservice.createUser(user).getEmail());
    	
    }
    @Test
    public void testSaveUserSecond() {
    	
    	//User user=new User(1,"user1", String name, String email, String password, String imagePath,String privilege)
    	User user=new User(1,"user1","name1","email1","password1","imagePath1","privilege1");
    	when(urepo.save(user)).thenReturn(user);
        assertEquals("user1",uservice.createUser(user).getUserName());
    	
    }
}
