package com.tira.booking.controllers;

import com.tira.booking.persistence.model.helpers.forms.RegisterForm;
import com.tira.booking.persistence.model.tables.City;
import com.tira.booking.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * The type City controller.
 */
@Controller
public class CityController extends BaseController {

	@Autowired
	private CityService service;

	/**
	 * Gets city.
	 *
	 * @param cityId the location id
	 * @return the city
	 */
	@RequestMapping(value = "/api/v1/getCity/{cityId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getCity(@PathVariable final String cityId) {
		return wrapForPublic(() -> this.service.getCity(UUID.fromString(cityId)));
	}


	/**
	 * Create city result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/admin/createCity", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity createCity(@RequestBody City city) {
		return wrapForAdmin(() -> this.service.createCity(city));
	}

	/**
	 * Edit city result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/admin/editCity", method = RequestMethod.PUT, produces="application/json")
	public ResponseEntity editCity(@RequestBody City city) {
		return wrapForAdmin(() -> this.service.editCity(city));
	}

	/**
	 * Delete city result.
	 *
	 * @param cityId the id
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/deleteCity/{cityId}", method = RequestMethod.DELETE, produces="application/json")
	public ResponseEntity deleteCity(@PathVariable final String cityId) {
		return wrapForAdmin(() -> this.service.deleteCity(UUID.fromString(cityId)));
	}

	/**
	 * Gets all cities.
	 *
	 * @return the all cities
	 */
    @RequestMapping(value = "/api/v1/getAllCities", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getAllCities() {
		return wrapForPublic(() -> this.service.getAllCities());
	}
}
