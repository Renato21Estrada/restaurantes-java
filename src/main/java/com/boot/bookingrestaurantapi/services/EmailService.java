package com.boot.bookingrestaurantapi.services;

import com.boot.bookingrestaurantapi.exceptions.BookingException;

public interface EmailService {

	public String ProcessSendEmail(final String receiver , String templateCode , String currentName)
			throws BookingException;
}
