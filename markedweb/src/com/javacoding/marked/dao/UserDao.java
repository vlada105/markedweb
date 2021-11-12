package com.javacoding.marked.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.javacoding.marked.DataStore;
import com.javacoding.marked.constants.Gender;
import com.javacoding.marked.constants.UserType;

import com.javacoding.marked.entities.User;

import com.javacoding.marked.services.UserService;

public class UserDao {
	
	public List<User> getUsers() {
		return DataStore.getUsers();
	}

	public User getUser(long userId) {

		User user = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marked_project", "root", "root");
				Statement stmt = conn.createStatement();) {	
			
			String query = "Select * from User where id = " + userId;
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int gender_id = rs.getInt("gender_id");
				Gender gender = Gender.values()[gender_id];
				int user_type_id = rs.getInt("user_type_id");
				UserType userType = UserType.values()[user_type_id];
				
				user = UserService.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return user;
		
	}

	public long authenticate(String email, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marked_project", "root", "root");
				Statement stmt = conn.createStatement();) {	
			String query = "Select id from User where email = '" + email + "' and password = '" + password + "'";
			System.out.println("query: " + query);
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				return rs.getLong("id");				
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return -1;
	}
	
	
	public long userCheck(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marked_project", "root", "root");
				Statement stmt = conn.createStatement();) {	
			String query = "Select id from User where email = '" + email +"'";
			System.out.println("query: " + query);			
			ResultSet rs = stmt.executeQuery(query);			
			while (rs.next()) {
				return rs.getLong("id");				
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return -1;
	}

	public void registerUser(String firstName, String lastName, String email, String encPassword) {
			
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marked_project", "root", "root"); 
					Statement stmnt = conn.createStatement();) {				
				
				String query = "Insert into user (first_name, last_name, email, password, created_date) values " +						
						"('" + firstName + "', '" + lastName + "', '" + email + "', '" + encPassword + "', NOW())" ;		
				
				stmnt.executeUpdate(query);	
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
	}
		
		
		
}
	
	
	
	
	
		
















