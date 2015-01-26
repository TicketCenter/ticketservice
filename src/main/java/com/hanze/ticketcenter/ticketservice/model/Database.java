package com.hanze.ticketcenter.ticketservice.model;

import java.sql.*;

public class Database {

	private String DBHost, DBName, DBUser, DBPass;
	
	public Database(String DBHost, String DBName, String DBUser, String DBPass) {
		this.DBHost = DBHost;
		this.DBName = DBName;
		this.DBUser = DBUser;
		this.DBPass = DBPass;
	}
	
	public Connection getConnection() {
		try {
			String URI = "jdbc:mysql://" + this.DBHost + "/" + this.DBName;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(URI, this.DBUser, this.DBPass);
		} catch (Exception e) {
			System.out.println("DB connection failed.");
			e.printStackTrace();
			return null;
		}	
	}
	
}
