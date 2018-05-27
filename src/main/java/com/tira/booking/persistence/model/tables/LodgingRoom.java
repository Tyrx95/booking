package com.tira.booking.persistence.model.tables;


import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.util.UUID;

/**
 * The type Lodging room.
 */
@Entity
@Table(name = "lodging_room")
public class LodgingRoom extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "lodging_id")
	private UUID lodgingId;

	@Column(name = "number_of_rooms")
	private Integer numberOfRooms;

	@Column(name = "price")
	private Double price;



	/**
	 * Instantiates a new Lodging room.
	 */
	public LodgingRoom() { }

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
	 * Gets lodging id.
	 *
	 * @return the lodging id
	 */
	public UUID getLodgingId() {  return lodgingId; }

	/**
	 * Sets lodging id.
	 *
	 * @param lodgingId the lodging id
	 */
	public void setLodgingId(UUID lodgingId) { this.lodgingId = lodgingId; }

	/**
	 * Gets number of rooms.
	 *
	 * @return the number of rooms
	 */
	public Integer getNumberOfRooms() { return numberOfRooms; }

	/**
	 * Sets number of rooms.
	 *
	 * @param numberOfRooms the number of rooms
	 */
	public void setNumberOfRooms(Integer numberOfRooms) { this.numberOfRooms = numberOfRooms; }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setPrice(String price) {
		this.price = Double.parseDouble(price);
	}
}