package com.hanze.ticketcenter.ticketservice.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hanze.ticketcenter.ticketservice.general.TicketGenerator;
import com.itextpdf.text.DocumentException;

public class Order {

	private Connection DBConn;

	public Order(Connection DBConn) {
		this.DBConn = DBConn;
	}

	/**********************
	 *** CREATE METHODS ***
	 *********************/

	/**
	 * @method createOrder
	 * @param Order
	 *            dto
	 * @return Response object Add a new order
	 */
	public Response createOrder(com.hanze.ticketcenter.ticketservice.dto.Order order) {
		try {
			// Create order
			String query = "INSERT INTO `order` VALUES (NULL, NULL, ?, ?, ?, ?, ?)";

			PreparedStatement ps = this.DBConn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, order.getUser_id());
			ps.setInt(2, order.getTicket_id());
			ps.setString(3, order.getEmail_address());
			ps.setFloat(4, order.getPrice());
			ps.setInt(5, order.getAmount());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int orderId = rs.getInt(1);
			
//			sendMail(order.getEmail_address());

			return this.getOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB -- query -- failed.");

			return Response.serverError().build();
		}
	}

	/**********************
	 *** GETTER METHODS ***
	 *********************/

	/**
	 * @method getOrdersByUser
	 * @return users Get list with orders (DTO) for a user
	 */
	public Response getOrdersByUser(String token, int userId) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		try {
			ArrayList<com.hanze.ticketcenter.ticketservice.dto.Order> orders = new ArrayList<com.hanze.ticketcenter.ticketservice.dto.Order>();
			PreparedStatement ps = this.DBConn
					.prepareStatement("SELECT * FROM `order` WHERE user_id = ?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				orders.add(this.resultSetToOrder(rs));
			}

			com.hanze.ticketcenter.ticketservice.dto.Order[] orderArr = new com.hanze.ticketcenter.ticketservice.dto.Order[orders.size()];
			orderArr = orders.toArray(orderArr);

			return Response.ok(orderArr, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			e.printStackTrace();

			return Response.serverError().build();
		}
	}

	/**
	 * @method getOrder
	 * @param token
	 * @param userId
	 * @param orderId
	 * @return Response Get order (DTO) of by orderId
	 */
	public Response getOrder(String token, int userId, int orderId) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return getOrderById(orderId);
	}

	/**********************
	 *** UPDATE METHODS ***
	 *********************/
	
	/*** NONE ***/

	/**********************
	 *** DELETE METHODS ***
	 *********************/

	public Response deleteOrder(String token, int userId, int orderId) {
		if (!validateToken(token, userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		try {
			// Delete user
			String query = "DELETE FROM " + "`order` " + "WHERE " + "order_id = ?";
			PreparedStatement ps = this.DBConn.prepareStatement(query);
			ps.setInt(1, orderId);
			ps.executeUpdate();

			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();

			return Response.serverError().build();
		}

	}

	/**********************
	 *** GLOBAL METHODS ***
	 *********************/

	/**
	 * @method resultSetToOrder
	 * @param rs
	 * @return order
	 * @throws Exception
	 *             Format result set to order (DTO)
	 */
	private com.hanze.ticketcenter.ticketservice.dto.Order resultSetToOrder(ResultSet rs) throws Exception {
		com.hanze.ticketcenter.ticketservice.dto.Order order = new com.hanze.ticketcenter.ticketservice.dto.Order();
		order.setOrder_id(rs.getInt("order_id"));
		order.setOrder_date(rs.getDate("order_date").toString());
		order.setUser_id(rs.getInt("user_id"));
		order.setTicket_id(rs.getInt("ticket_id"));
		order.setEmail_address(rs.getString("email_address"));
		order.setPrice(rs.getFloat("price"));
		order.setAmount(rs.getInt("amount"));

		return order;
	}

	public Response getOrderById(int orderId) {
		try {
			PreparedStatement ps = this.DBConn
					.prepareStatement("SELECT * FROM `order` WHERE order_id = "
							+ orderId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return Response.ok(this.resultSetToOrder(rs),
						MediaType.APPLICATION_JSON).build();
			}

			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (Exception e) {
			System.out.println("DB query failed.");
			e.printStackTrace();

			return Response.serverError().build();
		}
	}

	private boolean validateToken(String token, int userId) {
		int splitTokenIndex = token.lastIndexOf("@");

		String email = token.substring(0, splitTokenIndex);
		String password = token.substring(splitTokenIndex + 1);

		try {
			PreparedStatement pStatement = this.DBConn
					.prepareStatement("SELECT user_id FROM user WHERE user_id = ? AND email_address = ? AND password = ?");
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

	private boolean sendMail(String to) {
		String from = "jorian_09@hotmail.com";
		String host = "localhost";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		ByteArrayOutputStream outputStream = null;
		String concert = "Concertnaam", 
			   artist = "artierstnaam", 
			   location = "Groningen", 
			   date = "20-1-2015", 
			   time = "21:00 uur", 
			   userName = "Jorian Plat", 
			   userDate = "02-10-1993", 
			   userEmail = "jorianplat@hotmail.com",
			   subject = "Uw ticket";
		
		try {
			MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Ticket blabla", "text/html");
                        
            TicketGenerator tGenerator = new TicketGenerator(concert, artist, location, date, time, userName, userDate, userEmail);
            outputStream = tGenerator.generateTicket();
            byte[] bytes = outputStream.toByteArray();
            
            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("Ticket.pdf");
                         
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(from);
            InternetAddress iaRecipient = new InternetAddress(userEmail);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport.send(mimeMessage);
			
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}		
	}

}
