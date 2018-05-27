package com.tira.booking.controllers;

import com.tira.booking.persistence.model.helpers.forms.ReservationConfirmationForm;
import com.tira.booking.persistence.model.helpers.forms.ReservationForm;
import com.tira.booking.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * The type Reservation controller.
 */
@Controller
public class ReservationController extends BaseController {

	@Autowired
	private ReservationService service;

	/**
	 * Gets reservation.
	 *
	 * @param reservationId the id
	 * @return the reservation
	 */
	@RequestMapping(value = "/api/v1/getReservation/{reservationId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getReservation(@PathVariable final String reservationId) {
		return wrapForPublic(() -> this.service.getReservation(UUID.fromString(reservationId)));
	}

	/**
	 * Post reservation inquiry result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/postReservationInquiry", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity postReservationInquiry(@RequestBody ReservationForm reservationForm) {
		return wrapForPublic(() -> this.service.processInquiry(reservationForm));
	}

	/**
	 * Post reservation result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/postReservation", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity postReservation(@RequestBody ReservationForm reservationForm) {
		return wrapForPublic(() -> this.service.postReservation(reservationForm));
	}

	/**
	 * Confirm reservation result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/confirmReservation", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity confirmReservation(@RequestBody ReservationConfirmationForm reservationConfirmationForm) {
		return wrapForUser(() -> this.service.confirmReservation(reservationConfirmationForm));
	}

	/**
	 * Gets my reservations.
	 *
	 * @return the my reservations
	 */
    @RequestMapping(value = "/api/v1/getMyReservations", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getMyReservations() {
		return wrapForUser(() -> this.service.
                getMyReservations(this.userCache.get((String) httpSession.getAttribute("uid"))));
	}

	/**
	 * Gets all reservations.
	 *
	 * @param lodgingId the lodging id
	 * @param dateFilter   the date filter
	 * @return the all reservations
	 */

    @RequestMapping(value = "/api/v1/admin/getAllReservations/{rId}/{date}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getAllReservations(@PathVariable("rId") String lodgingId,
                                             @PathVariable("date") String dateFilter) {
		return wrapForAdmin(() -> this.service.getAllReservations(UUID.fromString(lodgingId), dateFilter));
	}
}
