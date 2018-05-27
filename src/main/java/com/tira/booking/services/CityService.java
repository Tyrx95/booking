package com.tira.booking.services;

import com.tira.booking.persistence.model.tables.City;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

/**
 * The type City service.
 */
@Service
public class CityService extends BaseService {

	private static final String ORDER_KEY = "name";


	/**
	 * Gets all cities.
	 *
	 * @return the all cities
	 */
	@SuppressWarnings("unchecked")
	public List<City> getAllCities() {
		return (List<City>) getSession().createCriteria(City.class)
				.addOrder(Order.asc(ORDER_KEY))
				.list();
	}

	/**
	 * Gets city.
	 *
	 * @param locationId the location id
	 * @return the city
	 */
	public City getCity(final UUID locationId) {
		return (City) getSession().createCriteria(City.class)
				.add(Restrictions.eq("id", locationId))
				.uniqueResult();
	}

	/**
	 * Create city boolean.
	 *
	 * @param city the city
	 * @throws Exception the exception
	 */
	public Boolean createCity(final City city) throws Exception {
		getSession().save(city);
		return true;
	}

	/**
	 * Edit city boolean.
	 *
	 * @param city the city
	 * @throws Exception the exception
	 */
	public Boolean editCity(final City city) throws Exception {
		getSession().update(city);
		return true;
	}

	/**
	 * Delete city boolean.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	public Boolean deleteCity(final UUID id) throws Exception {
		City city = (City) getSession().createCriteria(City.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(city);
		return true;
	}
}
