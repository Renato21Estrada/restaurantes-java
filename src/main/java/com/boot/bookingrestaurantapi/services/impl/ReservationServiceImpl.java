package com.boot.bookingrestaurantapi.services.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.EmailService;
import com.boot.bookingrestaurantapi.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	public static ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TurnRepository turnRepository;

	public ReservationRest getReservation(Long reservationid) throws BookingException {

		return modelMapper.map(getReservationEntity(reservationid), ReservationRest.class);
	}

	public String createReservation(CreateReservationRest createReservationRest) throws BookingException {

		final Restaurant restaurantid = restaurantRepository.findById(createReservationRest.getRestaurantid())
				.orElseThrow(() -> new NotFoundException("RESTAURANT_NOT_FOUND", "RESTAURANT_NOT_FOUND"));

		final Turn turn = turnRepository.findById(createReservationRest.getTurnid())
				.orElseThrow(() -> new NotFoundException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));

		String locator = generateLocator(restaurantid, createReservationRest);

		final Reservation reservation = new Reservation();
		reservation.setLocator(locator);
		reservation.setPerson(createReservationRest.getPerson());
		reservation.setDate(createReservationRest.getDate());
		reservation.setRestaurant(restaurantid);
		reservation.setTurn(turn.getName());
		reservation.setName(createReservationRest.getName());
		reservation.setEmail(createReservationRest.getEmail());

		try {
			reservationRepository.save(reservation);
		} catch (final Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		this.emailService.ProcessSendEmail(createReservationRest.getEmail(), "RESERVATION",
				createReservationRest.getName());

		return locator;
	}

	private String generateLocator(Restaurant restaurantid, CreateReservationRest createReservationRest)
			throws BookingException {
		return restaurantid.getName() + createReservationRest.getTurnid();
	}

	private Reservation getReservationEntity(Long reservationid) throws BookingException {

		return reservationRepository.findById(reservationid)
				.orElseThrow(() -> new NotFoundException("NOT-404-1", "RESERVATION NOT FOUND"));
	}

}
