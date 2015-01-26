package com.hanze.ticketcenter.ticketservice;

import com.hanze.ticketcenter.ticketservice.model.Database;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class Users {

	//DB connection for interaction with rest DB
	private Connection DBConn = new Database("localhost", "ticketcenter", "hanze", "g$|Jx&V03Ynu5s7L").getConnection();
	
	/*********************
	*** CREATE METHODS ***
	*********************/	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(com.hanze.ticketcenter.ticketservice.dto.User user) {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).createUser(user);
	}
	
	/*******************
	*** READ METHODS ***
	*******************/
	
	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON })
	public String doTest() {
		return "TEST";
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsers() {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).getUsers();
	}
	
	@GET
	@Path("/{ID}/{token}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("ID") int userId, @PathParam("token") String token) {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).getUser(token, userId);
	}
	
	@GET
	@Path("/login/{email_address}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Login(@PathParam("email_address") String email, @PathParam("password") String password) {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).login(email, password);
	}
	
	/*********************
	*** UPDATE METHODS ***
	*********************/
	
	@PUT
	@Path("/{ID}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("token") String token, @PathParam("ID") int userId, com.hanze.ticketcenter.ticketservice.dto.User user) {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).updateUser(token, userId, user);
	}
	
	/*********************
	*** DELETE METHODS ***
	*********************/

	@DELETE
	@Path("/{ID}/{token}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("token") String token, @PathParam("ID") int userId) {
		return new com.hanze.ticketcenter.ticketservice.dao.User(this.DBConn).deleteUser(token, userId);
	}
	
}
