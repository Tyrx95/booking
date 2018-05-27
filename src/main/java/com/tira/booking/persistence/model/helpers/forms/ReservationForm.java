package com.tira.booking.persistence.model.helpers.forms;


import com.tira.booking.persistence.model.BaseModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The type Reservation form.
 */
public class ReservationForm extends BaseModel {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private UUID lodgingId;
	private Integer numberOfPeople;
	private Date date;
	private Date endDate;

	/**
	 * Instantiates a new Reservation form.
	 */
	public ReservationForm() {}

	/**
	 * Gets lodging id.
	 *
	 * @return the lodging id
	 */
	public UUID getLodgingId() { return this.lodgingId; }

	/**
	 * Sets lodging id.
	 *
	 * @param lodgingId the lodging id
	 */
	public void setLodgingId(UUID lodgingId) { this.lodgingId = lodgingId; }

	/**
	 * Gets number of people.
	 *
	 * @return the number of people
	 */
	public Integer getNumberOfPeople() { return this.numberOfPeople; }

	/**
	 * Sets number of people.
	 *
	 * @param numberOfPeople the number of people
	 */
	public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

	/**
	 * Gets date.
	 *
	 * @return the date
	 */
	public Date getDate() { return this.date; }

	/**
	 * Sets date.
	 *
	 * @param date the date
	 * @throws ParseException the parse exception
	 */
	public void setDate(String date) throws ParseException {
		this.date = formatter.parse(date);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) throws ParseException {
		this.endDate = formatter.parse(endDate);
	}
}
