package com.hanze.ticketcenter.ticketservice.dto;

public class Order {

	private int order_id;
	private String order_date;
	private int user_id;
	private int ticket_id;
	private String email_address;
	private float price;
	private int amount;
	
	/**
	 * @return the order_id
	 */
	public int getOrder_id() {
		return order_id;
	}


	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}


	/**
	 * @return the order_date
	 */
	public String getOrder_date() {
		return order_date;
	}


	/**
	 * @param order_date the order_date to set
	 */
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}


	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}


	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	/**
	 * @return the ticket_id
	 */
	public int getTicket_id() {
		return ticket_id;
	}


	/**
	 * @param ticket_id the ticket_id to set
	 */
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}


	/**
	 * @return the email_address
	 */
	public String getEmail_address() {
		return email_address;
	}


	/**
	 * @param email_address the email_address to set
	 */
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}


	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}


	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	@Override
	public String toString() {
		
		
		
		return "order[email=" + email_address + "]";		
	}
	
	
}
