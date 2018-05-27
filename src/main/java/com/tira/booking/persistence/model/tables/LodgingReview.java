package com.tira.booking.persistence.model.tables;


import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.util.UUID;

/**
 * The type Lodging review.
 */
@Entity
@Table(name = "lodging_review")
public class LodgingReview extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "lodging_id")
	private UUID lodgingId;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "rating")
	private Integer rating;

	@Column(name = "review")
	private String review;

	/**
	 * Instantiates a new Lodging review.
	 */
	public LodgingReview() { }

	/**
	 * Instantiates a new Lodging review.
	 *
	 * @param lodgingId the lodging id
	 * @param userId       the user id
	 * @param rating       the rating
	 * @param review       the review
	 */
	public LodgingReview(UUID lodgingId, UUID userId, Integer rating, String review) {
		this.lodgingId = lodgingId;
		this.userId = userId;
		this.rating = rating;
		this.review = review;
	}


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
	public UUID getLodgingId() { return lodgingId; }

	/**
	 * Sets lodging id.
	 *
	 * @param lodgingId the lodging id
	 */
	public void setLodgingId(UUID lodgingId) { this.lodgingId = lodgingId; }

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public UUID getUserId() { return userId; }

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(UUID userId) { this.userId = userId; }

	/**
	 * Gets rating.
	 *
	 * @return the rating
	 */
	public Integer getRating() { return rating; }

	/**
	 * Sets rating.
	 *
	 * @param rating the rating
	 */
	public void setRating(Integer rating) { this.rating = rating; }

	/**
	 * Gets review.
	 *
	 * @return the review
	 */
	public String getReview() { return review; }

	/**
	 * Sets review.
	 *
	 * @param review the review
	 */
	public void setReview(String review) { this.review = review; }
}
