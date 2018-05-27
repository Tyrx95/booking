package com.tira.booking.persistence.model.helpers.forms;


import com.tira.booking.persistence.model.BaseModel;
import com.tira.booking.persistence.model.tables.Reservation;

/**
 * The type Reservation confirmation form.
 */
public class ReservationConfirmationForm extends BaseModel {

	private Reservation reservation;

	/**
	 * Instantiates a new Reservation confirmation form.
	 */
	public ReservationConfirmationForm() { }

	/**
	 * Gets reservation.
	 *
	 * @return the reservation
	 */
	public Reservation getReservation() { return reservation; }

	/**
	 * Sets reservation.
	 *
	 * @param reservation the reservation
	 */
	public void setReservation(Reservation reservation) { this.reservation = reservation; }
}
