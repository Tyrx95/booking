package com.tira.booking.controllers;

import com.tira.booking.persistence.model.tables.Facility;
import com.tira.booking.services.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

/**
 * The type Facility controller.
 */
@Controller
public class FacilityController extends BaseController {

	@Autowired
	private FacilityService service;

	/**
	 * Gets facility.
	 *
	 * @param facilityId the id
	 * @return the facility
	 */
	@RequestMapping(value = "/api/v1/getFacility/{facilityId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getFacility(@PathVariable final String facilityId) {
		return wrapForPublic(() -> this.service.getFacility(UUID.fromString(facilityId)));
	}

	/**
	 * Create facility result.
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/createFacility", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity createFacility(@RequestBody Facility facility) {
		return wrapForAdmin(() -> this.service.createFacility(facility));
	}

	/**
	 * Edit facility result.
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/editFacility", method = RequestMethod.PUT, produces="application/json")
	public ResponseEntity editFacility(@RequestBody Facility facility) {
		return wrapForAdmin(() -> this.service.editFacility(facility));
	}

	/**
	 * Delete facility result.
	 *
	 * @param facilityId the id
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/deleteFacility/{facilityId}", method = RequestMethod.DELETE, produces="application/json")
	public ResponseEntity deleteFacility(@PathVariable final String facilityId) {
		return wrapForAdmin(() -> this.service.deleteFacility(UUID.fromString(facilityId)));
	}

	/**
	 * Gets all facilities.
	 *
	 * @return the all facilities
	 */
	@RequestMapping(value = "/api/v1/getAllFacilities", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getAllFacilities() {
		return wrapForPublic(() -> this.service.getAllFacilities());
	}

	/**
	 * Gets all facilities as string.
	 *
	 * @return the all facilities as string
	 */
	@RequestMapping(value = "/api/v1/getAllFacilitiesAsString", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getAllFacilitiesAsString() {
		return wrapForPublic(() -> this.service.getAllFacilitiesAsString());
	}
}
