package com.tira.booking.persistence.model.helpers.forms;


import com.tira.booking.persistence.model.BaseModel;

import java.util.UUID;

/**
 * The type Review form.
 */
public class ReviewForm extends BaseModel {

	private UUID lodgingId;
	private Integer reviewScore;
	private String reviewText;

	/**
	 * Instantiates a new Review form.
	 */
	public ReviewForm() { }

	/**
	 * Gets lodging id.
	 *
	 * @return the lodging id
	 */
	public UUID getLodgingId()  { return this.lodgingId; }

	/**
	 * Sets lodging id.
	 *
	 * @param lodgingId the lodging id
	 */
	public void setLodgingId(UUID lodgingId) { this.lodgingId = lodgingId; }

	/**
	 * Gets review text.
	 *
	 * @return the review text
	 */
	public String getReviewText() { return this.reviewText; }

	/**
	 * Sets review text.
	 *
	 * @param reviewText the review text
	 */
	public void setReviewText(String reviewText) { this.reviewText = reviewText; }

	/**
	 * Gets review score.
	 *
	 * @return the review score
	 */
	public Integer getReviewScore() { return this.reviewScore; }

	/**
	 * Sets review score.
	 *
	 * @param reviewScore the review score
	 */
	public void setReviewScore(Integer reviewScore) { this.reviewScore = reviewScore; }
}
