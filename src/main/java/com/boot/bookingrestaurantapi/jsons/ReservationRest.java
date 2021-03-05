package com.boot.bookingrestaurantapi.jsons;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationRest {

	@JsonProperty("locator")
	private String locator;
	
	@JsonProperty("restaurantid")
	private Long restaurantid;
	
	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("person")
	private Long person;
	
	@JsonProperty("turnid")
	private Long turnid;

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public Long getRestaurantid() {
		return restaurantid;
	}

	public void setRestaurantid(Long restaurantid) {
		this.restaurantid = restaurantid;
	}

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
}
