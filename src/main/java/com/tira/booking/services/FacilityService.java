package com.tira.booking.services;


import com.tira.booking.persistence.model.tables.Facility;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Facility service.
 */
@Service
public class FacilityService extends BaseService {

	private static final String ORDER_KEY = "name";


	/**
	 * Gets all facilities.
	 *
	 * @return the all facilities
	 */
	@SuppressWarnings("unchecked")
	public List<Facility> getAllFacilities() {
		return (List<Facility>) getSession().createCriteria(Facility.class)
				.addOrder(Order.asc(ORDER_KEY))
				.list();
	}

	/**
	 * Gets all facilities as string.
	 *
	 * @return the all facilities as string
	 */
	public List<String> getAllFacilitiesAsString() {
		return this.getAllFacilities().stream()
				.map(Facility::getName)
				.collect(Collectors.toList());
	}

	/**
	 * Gets facility.
	 *
	 * @param id the id
	 * @return the facility
	 */
	public Facility getFacility(final UUID id) {
		return (Facility) getSession().createCriteria(Facility.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Create facility boolean.
	 *
	 * @param facility the facility
	 * @return the boolean
	 */
	public Boolean createFacility(final Facility facility) {
		getSession().save(facility);
		return true;
	}

	/**
	 * Edit facility boolean.
	 *
	 * @param facility the facility
	 * @return the boolean
	 */
	public Boolean editFacility(final Facility facility) {
		getSession().update(facility);
		return true;
	}

	/**
	 * Delete facility boolean.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean deleteFacility(final UUID id) {
		Facility facility = (Facility) getSession().createCriteria(Facility.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(facility);
		return true;
	}
}
