package com.hanze.ticketcenter.ticketservice;

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

import com.hanze.ticketcenter.ticketservice.model.Database;

@Path("/orders")
public class Orders {

	//DB connection for interaction with rest DB
	private Connection DBConn = new Database("localhost", "ticketcenter", "hanze", "g$|Jx&V03Ynu5s7L").getConnection();
	
	/*********************
	*** CREATE METHODS ***
	*********************/	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(com.hanze.ticketcenter.ticketservice.dto.Order order) {
		return new com.hanze.ticketcenter.ticketservice.dao.Order(this.DBConn).createOrder(order);
	}
	
	/*******************
	*** READ METHODS ***
	*******************/
	
	@GET
	@Path("/{token}/{user_id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrdersByUser(@PathParam("token") String token, @PathParam("user_id") int userId) {
		return new com.hanze.ticketcenter.ticketservice.dao.Order(this.DBConn).getOrdersByUser(token, userId);
	}
	
	@GET
	@Path("/{token}/{user_id}/{order_id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrder(@PathParam("token") String token, @PathParam("user_id") int userId, @PathParam("order_id") int orderId) {
		return new com.hanze.ticketcenter.ticketservice.dao.Order(this.DBConn).getOrder(token, userId, orderId);
	}
	
	/*********************
	*** UPDATE METHODS ***
	*********************/
	
//	@PUT
//	@Path("/{ID}/{token}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateUser(@PathParam("token") String token, @PathParam("ID") int userId, restful.dto.User user) {
//		return new restful.dao.User(this.DBConn).updateUser(token, userId, user);
//	}
	
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
