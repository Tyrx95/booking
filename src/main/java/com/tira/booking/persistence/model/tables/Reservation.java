package com.tira.booking.persistence.model.tables;


import com.tira.booking.persistence.model.BaseModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The type Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation extends BaseModel {
	private static final String DATE_PATTERN = "EEEE, MMM dd, yyyy";
	private static final String TIME_PATTERN = "h:mm a";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private LodgingRoom room;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "reserved_on")
	private Timestamp reservedOn;

	@Column(name = "is_confirmed")
	private Boolean isConfirmed = false;

	@Transient
	private String date;

	@Transient
	private String time;

	/**
	 * Instantiates a new Reservation.
	 */
	public Reservation() { }

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public UUID getId() { return id; }

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(UUID id) { this.id = id; }

	/**
	 * Gets room.
	 *
	 * @return the room
	 */
	public LodgingRoom getRoom() { return room; }

	/**
	 * Sets room.
	 *
	 * @param room the room
	 */
	public void setRoom(LodgingRoom room) { this.room = room; }

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser() { return user; }

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(User user) { this.user = user; }

	/**
	 * Gets reserved on.
	 *
	 * @return the reserved on
	 */
	public Timestamp getReservedOn() { return reservedOn; }

	/**
	 * Sets reserved on.
	 *
	 * @param reservedOn the reserved on
	 */
	public void setReservedOn(Long reservedOn) { this.reservedOn = new Timestamp(reservedOn); }

	/**
	 * Gets confirmed.
	 *
	 * @return the confirmed
	 */
	public Boolean getConfirmed() { return isConfirmed; }

	/**
	 * Sets confirmed.
	 *
	 * @param confirmed the confirmed
	 */
	public void setConfirmed(Boolean confirmed) { isConfirmed = confirmed; }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}