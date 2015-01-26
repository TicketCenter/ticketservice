package com.hanze.ticketcenter.ticketservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class User {

	private Connection DBConn;
	
	public User(Connection DBConn) {
		this.DBConn = DBConn;
	}
	
	/**********************
	 *** CREATE METHODS ***
	 *********************/
	
	/**
	 * @method createUser
	 * @param user
	 * @return user
	 * Get list with user (DTO) of all users in DB
	 */
	public Response createUser(com.hanze.ticketcenter.ticketservice.dto.User user) {
		try {
			//Update user
		    String query = "INSERT INTO user VALUES (NULL, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		    PreparedStatement ps = this.DBConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    ps.setString(1, user.getEmail_address());
		    ps.setString(2, user.getPassword());
		    ps.setString(3, user.getFirst_name());
		    ps.setString(4, user.getLast_name());
		    ps.setString(5, user.getDate_of_birth());
		    ps.setString(6, user.getStreet_name());
		    ps.setInt(7, user.getStreet_number());
		    ps.setString(8, user.getStreet_number_add());
		    ps.setString(9, user.getPostal_code());
		    ps.setString(10, user.getCity());
		    ps.setString(11, user.getCountry());
		    ps.executeUpdate();

		    ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);
		    
		    return this.getUserById(userId);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("DB -- query -- failed.");
			
			return Response.serverError().build();
		}
	}
	
	
	
	/**********************
	 *** GETTER METHODS ***
	 *********************/
	
	/**
	 * @method getUsers
	 * @return users
	 * Get list with user (DTO) of all users in DB
	 */
	public Response getUsers() {
		try {
			ArrayList<com.hanze.ticketcenter.ticketservice.dto.User> users = new ArrayList<com.hanze.ticketcenter.ticketservice.dto.User>();
			PreparedStatement ps = this.DBConn.prepareStatement("SELECT * FROM user");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				users.add(this.resultSetToUser(rs));
			}

			com.hanze.ticketcenter.ticketservice.dto.User[] userArr = new com.hanze.ticketcenter.ticketservice.dto.User[users.size()];
			userArr = users.toArray(userArr);
			
			return Response.ok(userArr, MediaType.APPLICATION_JSON).build();
		} catch(Exception e) {			
			e.printStackTrace();
			
			return Response.serverError().build();
		}
	}
	
	/**
	 * @method getUser
	 * @param userId
	 * @return user
	 * Get user (DTO) of user by userId
	 */	
	public Response getUser(String token, int userId) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		return getUserById(userId);
	}
	
	public Response login(String email, String password) {
		try {
			PreparedStatement ps = this.DBConn.prepareStatement("SELECT user_id FROM user WHERE email_address = ? AND password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return Response.ok("{ \"token\": \"" + this.generateToken(email, password) + "\" }", MediaType.APPLICATION_JSON).build();				
			}
			
			return Response.status(Response.Status.NOT_FOUND).build();			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return Response.serverError().build();
		}
		
	}

	/**********************
	 *** UPDATE METHODS ***
	 *********************/
	
	public Response updateUser(String token, int userId, com.hanze.ticketcenter.ticketservice.dto.User user) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		try {
			//Update user
		    String query = 
		    	"UPDATE "
		    		+ "user "
	    		+ "SET "
	    			+ "email_address = ?,"
	    			+ "password = ?,"
	    			+ "first_name = ?,"
	    			+ "last_name = ?, "
	    			+ "date_of_birth = ?, "
	    			+ "street_name = ?, "
	    			+ "street_number = ?, "
	    			+ "street_number_add = ?, "
	    			+ "postal_code = ?, "
	    			+ "city = ?, "
	    			+ "country = ? "
				+ "WHERE "
					+ "user_id = ?";
		    PreparedStatement ps = this.DBConn.prepareStatement(query);
		    ps.setString(1, user.getEmail_address());
		    ps.setString(2, user.getPassword());
		    ps.setString(3, user.getFirst_name());
		    ps.setString(4, user.getLast_name());
		    ps.setString(5, user.getDate_of_birth());
		    ps.setString(6, user.getStreet_name());
		    ps.setInt(7, user.getStreet_number());
		    ps.setString(8, user.getStreet_number_add());
		    ps.setString(9, user.getPostal_code());
		    ps.setString(10, user.getCity());
		    ps.setString(11, user.getCountry());
		    ps.setInt(12, userId);
		    ps.executeUpdate();
		    
		    return this.getUserById(userId);
		    
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("DB -- query -- failed.");
			
			return Response.serverError().build();
		}
		
	}
	
	/**********************
	 *** DELETE METHODS ***
	 *********************/
	
	public Response deleteUser(String token, int userId) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		try {
			//Delete user
		    String query = 
		    	"DELETE FROM "
		    		+ "user "
				+ "WHERE "
					+ "user_id = ?";
		    PreparedStatement ps = this.DBConn.prepareStatement(query);
		    ps.setInt(1, userId);
		    ps.executeUpdate();
		    
		    return Response.ok().build();
		} catch(Exception e) {
			e.printStackTrace();
			
			return Response.serverError().build();			
		}
		
	}	
	
	/**********************
	 *** GLOBAL METHODS ***
	 *********************/
	
	/**
	 * @method resultSetToUser
	 * @param rs
	 * @return user
	 * @throws Exception
	 * Format result set to user (DTO)
	 */
	private com.hanze.ticketcenter.ticketservice.dto.User resultSetToUser(ResultSet rs) throws Exception {
		com.hanze.ticketcenter.ticketservice.dto.User user = new com.hanze.ticketcenter.ticketservice.dto.User();
		user.setUser_id(rs.getInt("user_id"));
		user.setEmail_address(rs.getString("email_address"));
		user.setPassword(rs.getString("password"));
		user.setFirst_name(rs.getString("first_name"));
		user.setLast_name(rs.getString("last_name"));
		user.setDate_of_birth(rs.getDate("date_of_birth").toString());
		user.setStreet_name(rs.getString("street_name"));
		user.setStreet_number(rs.getInt("street_number"));
		user.setStreet_number_add(rs.getString("street_number_add"));
		user.setPostal_code(rs.getString("postal_code"));
		user.setCity(rs.getString("city"));
		user.setCountry(rs.getString("country"));
		user.setRegistration_date(rs.getTimestamp("registration_date").toString());

		return user;
	}
	
	public Response getUserById(int userId) {
		try {
			PreparedStatement ps = this.DBConn.prepareStatement("SELECT * FROM user WHERE user_id = " + userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return Response.ok(this.resultSetToUser(rs), MediaType.APPLICATION_JSON).build();
			}

			return Response.status(Response.Status.NOT_FOUND).build();
		} catch(Exception e) {
			System.out.println("DB query failed.");
			e.printStackTrace();
			
			return Response.serverError().build();
		}
	}	
	
	//TODO
	private String generateToken(String email, String password) {
		String token = email + "@" + password;
		return token;
	}
	
	private boolean validateToken(String token, int userId) {	
		int splitTokenIndex = token.lastIndexOf("@");
		
		String email = token.substring(0, splitTokenIndex);	
		String password = token.substring(splitTokenIndex + 1);	
		
		try {
			PreparedStatement pStatement = this.DBConn.prepareStatement("SELECT user_id FROM user WHERE user_id = ? AND email_address = ? AND password = ?");
			pStatement.setInt(1, userId);
			pStatement.setString(2, email);
			pStatement.setString(3, password);
			ResultSet rSet = pStatement.executeQuery();

			return rSet.next();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
