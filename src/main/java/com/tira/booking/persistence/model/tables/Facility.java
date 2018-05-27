package com.tira.booking.persistence.model.tables;



import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.util.UUID;

/**
 * The type Facility.
 */
@Entity
@Table(name = "facility")
public class Facility extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	/**
	 * Instantiates a new Facility.
	 */
	public Facility() { }

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
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() { return name; }

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) { this.name = name; }
}