package com.hanze.ticketcenter.ticketservice.dto;

public class User {

	private int user_id;
	private String email_address;
	private String password;
	private String registration_date;
	private String first_name;
	private String last_name;
	private String date_of_birth;
	private String street_name;
	private int street_number;
	private String street_number_add;
	private String postal_code;
	private String city;
	private String country;
	
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the registration_date
	 */
	public String getRegistration_date() {
		return registration_date;
	}
	/**
	 * @param registration_date the registration_date to set
	 */
	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return the date_of_birth
	 */
	public String getDate_of_birth() {
		return date_of_birth;
	}
	/**
	 * @param date_of_birth the date_of_birth to set
	 */
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	/**
	 * @return the street_name
	 */
	public String getStreet_name() {
		return street_name;
	}
	/**
	 * @param street_name the street_name to set
	 */
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	/**
	 * @return the street_number
	 */
	public int getStreet_number() {
		return street_number;
	}
	/**
	 * @param street_number the street_number to set
	 */
	public void setStreet_number(int street_number) {
		this.street_number = street_number;
	}
	/**
	 * @return the street_number_add
	 */
	public String getStreet_number_add() {
		return street_number_add;
	}
	/**
	 * @param street_number_add the street_number_add to set
	 */
	public void setStreet_number_add(String street_number_add) {
		this.street_number_add = street_number_add;
	}
	/**
	 * @return the postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}
	/**
	 * @param postal_code the postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		
		
		
		return "user[email=" + email_address + "]";		
	}
	
	
}
