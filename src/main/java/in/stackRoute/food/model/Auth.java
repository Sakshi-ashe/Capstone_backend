package in.stackRoute.food.model;

public class Auth {
private String userName;
private String password;
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
public Auth() {}
public Auth(String userName, String password) {
	super();
	this.userName = userName;
	this.password = password;
}

	
}
