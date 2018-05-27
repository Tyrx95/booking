package com.tira.booking.persistence.model.helpers;


import com.tira.booking.persistence.model.helpers.forms.ReservationForm;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Reservation inquiry response.
 */
public class ReservationInquiryResponse {

	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private ReservationForm inquiry;
	private Long numberOfRoomsLeft;
	private Long numberOfReservationsToday;
	private List<DateSuggestion> dateSuggestions;

	private final SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);

	private  ReservationInquiryResponse() {}

	/**
	 * Gets object.
	 *
	 * @return the object
	 */
	public static ReservationInquiryResponse getObject() {
		return new ReservationInquiryResponse();
	}

	/**
	 * Gets inquiry.
	 *
	 * @return the inquiry
	 */
	public ReservationForm getInquiry() { return this.inquiry; }

	/**
	 * Sets inquiry.
	 *
	 * @param inquiry the inquiry
	 * @return the inquiry
	 */
	public ReservationInquiryResponse setInquiry(ReservationForm inquiry) {
		this.inquiry = inquiry;
		return this;
	}

	/**
	 * Gets number of rooms left.
	 *
	 * @return the number of rooms left
	 */
	public Long getNumberOfRoomsLeft() { return this.numberOfRoomsLeft; }

	/**
	 * Sets number of rooms left.
	 *
	 * @param numberOfRoomsLeft the number of rooms left
	 * @return the number of rooms left
	 */
	public ReservationInquiryResponse setNumberOfRoomsLeft(Long numberOfRoomsLeft) {
		this.numberOfRoomsLeft = numberOfRoomsLeft;
		return this;
	}

	/**
	 * Gets number of reservations today.
	 *
	 * @return the number of reservations today
	 */
	public Long getNumberOfReservationsToday() { return this.numberOfReservationsToday; }

	/**
	 * Sets number of reservations today.
	 *
	 * @param numberOfReservationsToday the number of reservations today
	 * @return the number of reservations today
	 */
	public ReservationInquiryResponse setNumberOfReservationsToday(Long numberOfReservationsToday) {
		this.numberOfReservationsToday = numberOfReservationsToday;
		return this;
	}

	/**
	 * Gets time suggestions.
	 *
	 * @return the time suggestions
	 */
	public List<DateSuggestion> getDateSuggestions() { return this.dateSuggestions; }

	public ReservationInquiryResponse setDateSuggestions(DateSuggestion dateSuggestion) {
		this.setDateSuggestions(Arrays.asList(dateSuggestion));
		return this;
	}

	@SuppressWarnings("unchecked")
	public ReservationInquiryResponse setDateSuggestions(final List<?> dateSuggestions) {
		if (!dateSuggestions.isEmpty()) {
			if (dateSuggestions.get(0) instanceof DateSuggestion) {
				this.setDateSuggestionsFromDate((List<DateSuggestion>) dateSuggestions);
			} else if (dateSuggestions.get(0) instanceof String) {
				//this.setDateSuggestionsFromString((List<String>) dateSuggestions);
			}
		}
		return this;
	}

	private void setDateSuggestionsFromDate(final List<DateSuggestion> dateSuggestions) {
		this.dateSuggestions = dateSuggestions;
	}

	/*
	private void setDateSuggestionsFromString(final List<String> dateSuggestions) {
		this.dateSuggestions = dateSuggestions.stream().map(this::dateFromString).collect(Collectors.toList());
	}

	private Date dateFromString(final String dateString) {
		try {
			return new Date(df.parse(dateString).getDate());
		} catch (ParseException e) {
			return null;
		}
	}
	*/
}
