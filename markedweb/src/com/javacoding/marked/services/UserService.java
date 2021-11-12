package com.javacoding.marked.services;

import java.util.List;

import com.javacoding.marked.constants.Gender;
import com.javacoding.marked.constants.UserType;
import com.javacoding.marked.dao.UserDao;
import com.javacoding.marked.entities.User;
import com.javacoding.marked.util.StringUtil;

public class UserService { //singleton pattern
	
	private static UserService instance = new UserService();
	
	private static UserDao userDao = new UserDao();	
			
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		return instance;
	};
	
	
	public User createUser (long id, String email, String password,
			String firstName, String lastName, Gender gender, UserType userType ) {
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender); 
		user.setUserType(userType);
		
		return user;
		
	}
	
	
	public List<User> getUsers() {		
		return userDao.getUsers();
	}

	public User getUser(long userId) {		
		return userDao.getUser(userId);
	}

	public long authenticate(String email, String password) {		
		return userDao.authenticate(email, StringUtil.encodePassword(password));		
	}
	
	
	public long userCheck(String email) {			
		return userDao.userCheck(email);		
	}

	public void registerUser(String firstName, String lastName, String email, String password) {
		
		userDao.registerUser(firstName, lastName, email, password);
		
	}
	
	

}


















