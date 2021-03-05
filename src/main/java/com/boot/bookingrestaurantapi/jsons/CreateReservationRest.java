package com.boot.bookingrestaurantapi.jsons;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReservationRest {

	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("person")
	private Long person;
	
	@JsonProperty("turnid")
	private Long turnid;
	
	@JsonProperty("restaurantid")
	private Long restaurantid;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
	private String email;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public Long getTurnid() {
		return turnid;
	}

	public void setTurnid(Long turnid) {
		this.turnid = turnid;
	}

	public Long getRestaurantid() {
		return restaurantid;
	}

	public void setRestaurantid(Long restaurantid) {
		this.restaurantid = restaurantid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
