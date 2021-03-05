package com.boot.bookingrestaurantapi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.services.CancelReservationService;
import com.boot.bookingrestaurantapi.services.EmailService;

@Service
public class CancelServiceImpl implements CancelReservationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancelServiceImpl.class);

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired 
	private EmailService emailService;
	
	@Override
	public String deleteReservation(String locator) throws BookingException {

		Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFoundException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));

		try {
			reservationRepository.deleteByLocator(locator);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		this.emailService.ProcessSendEmail(reservation.getEmail(), "CANCEL", reservation.getName());
		
		return "LOCATOR_DELETE";
	}

}
