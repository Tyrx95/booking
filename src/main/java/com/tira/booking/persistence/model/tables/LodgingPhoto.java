package com.tira.booking.persistence.model.tables;

import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.util.UUID;

/**
 * The type Lodging photo.
 */
@Entity
@Table(name = "lodging_photo")
public class LodgingPhoto extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "lodging_id")
	private UUID lodgingId;

	@Column(name = "photo_path")
	private String path;

	/**
	 * Instantiates a new Lodging photo.
	 */
	public LodgingPhoto() { }

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
	 * Gets path.
	 *
	 * @return the path
	 */
	public String getPath() { return path; }

	/**
	 * Sets path.
	 *
	 * @param path the path
	 */
	public void setPath(String path) { this.path = path; }
}