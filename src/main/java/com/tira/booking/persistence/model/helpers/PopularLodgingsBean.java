package com.tira.booking.persistence.model.helpers;

import java.util.UUID;

/**
 * The type Popular lodgings bean.
 */
public class PopularLodgingsBean {
	private UUID lodgingId;
	private Long roomCount;

	/**
	 * Instantiates a new Popular lodgings bean.
	 */
	public PopularLodgingsBean() { }

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
	 * Gets room count.
	 *
	 * @return the room count
	 */
	public Long getRoomCount() { return this.roomCount; }

	/**
	 * Sets room count.
	 *
	 * @param roomCount the room count
	 */
	public void setRoomCount(Long roomCount) { this.roomCount = roomCount; }
}
