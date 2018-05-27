package com.tira.booking.controllers;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tira.booking.persistence.model.helpers.LodgingFilter;
import com.tira.booking.persistence.model.helpers.forms.ImageUploadForm;
import com.tira.booking.persistence.model.helpers.forms.ReviewForm;
import com.tira.booking.persistence.model.tables.Lodging;
import com.tira.booking.services.LodgingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The type Lodging controller.
 */
@Controller
public class LodgingController extends BaseController {

	@Autowired
	private LodgingService service;

	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_SIZE = "pageSize";
	private static final String NAME_FILTER = "nameFilter";
	private static final String CITY_FILTER = "cityFilter";
	private static final String SORT_BY = "sortBy";
	private static final String PRICE_FILTER = "priceFilter";
	private static final String RATING_FILTER = "ratingFilter";
    private static final String FACILITY_FILTER = "facilityFilter";

	private static final String DEFAULT_PAGE_NUMBER = "1";
	private static final String DEFAULT_PAGE_SIZE = "9";
	private static final String DEFAULT_PRICE_FILTER = "0";
	private static final String DEFAULT_RATING_FILTER = "0";

	/**
	 * Create lodging result.
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/createLodging",
            method = RequestMethod.POST, produces="application/json")
	public ResponseEntity createLodging(@RequestBody Lodging lodging) {
		return wrapForAdmin(() -> this.service.createLodging(lodging));
	}

	/**
	 * Edit lodging result.
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/editLodging",
            method = RequestMethod.PUT, produces="application/json")
	public ResponseEntity editLodging(@RequestBody Lodging lodging) {
		return wrapForAdmin(() -> this.service.editLodging(lodging));
	}

	/**
	 * Delete lodging result.
	 *
	 * @param lodgingId the id
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/deleteLodging/{lodgingId}",
            method = RequestMethod.DELETE, produces="application/json")
	public ResponseEntity deleteLodging(@PathVariable String lodgingId) {
		return wrapForAdmin(() -> this.service.deleteLodging(UUID.fromString(lodgingId)));
	}

	/**
	 * Gets all lodgings.
	 *
	 * @return the all lodgings
	 */
	@RequestMapping(value = "/api/v1/getAllLodgings", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getAllLodgings(
			@RequestParam(name=PAGE_NUMBER, defaultValue = DEFAULT_PAGE_NUMBER) String pageNumber,
			@RequestParam(name=PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) String pageSize,
			@RequestParam(name=PRICE_FILTER, defaultValue = DEFAULT_PRICE_FILTER, required = false) String priceFilter,
			@RequestParam(name=RATING_FILTER, defaultValue = DEFAULT_RATING_FILTER, required = false) String ratingFilter,
			@RequestParam(name=NAME_FILTER,required = false) String nameFilter,
			@RequestParam(name=SORT_BY,required = false) String sortBy,
			@RequestParam(name=CITY_FILTER,required = false) String cityFilter,
			@RequestParam(name=FACILITY_FILTER,required = false) String facilityFilter
			)
	{
		return wrapForPublic(() -> this.service.findLodgingsWithFilter(
				LodgingFilter.createFilter()
						.setPageNumber(Integer.parseInt(pageNumber))
						.setPageSize(Integer.parseInt(pageSize))
						.setNameFilter(nameFilter)
						.setCityFilter(!StringUtils.isBlank(cityFilter)? UUID.fromString(cityFilter) : null)
						.setSort(sortBy)
						.setPriceFilter(Integer.parseInt(priceFilter))
						.setRatingFilter(Integer.parseInt(ratingFilter))
						.setFacilityFilter(facilityFilter)

		));
	}

	/**
	 * Gets lodging.
	 *
	 * @param lodgingId the id
	 * @return the lodging
	 */
    @RequestMapping(value = "/api/v1/getLodging/{lodgingId}",
            method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getLodging(@PathVariable String lodgingId) {
		return wrapForPublic(() -> this.service.getLodgingWithId(UUID.fromString(lodgingId)));
	}

	/**em
	 * Gets nearby lodgings.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @return the nearby lodgings
	 */
    @RequestMapping(value = "/api/v1/getNearbyLodgings/{latitude}/{longitude}",
            method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getNearbyLodgings(@PathVariable("latitude") Float latitude,
                                               @PathVariable("longitude")Float longitude) {
		return wrapForPublic(() -> this.service.getNearbyLodgings(latitude, longitude));
	}

	/**
	 * Gets popular lodgings.
	 *
	 * @return the popular lodgings
	 */
    @RequestMapping(value = "/api/v1/getPopularLodgings", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getPopularLodgings() {
		return wrapForPublic(() -> this.service.getPopularLodgings());
	}

	/**
	 * Gets popular locations.
	 *
	 * @return the popular locations
	 */
    @RequestMapping(value = "/api/v1/getPopularLocations", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getPopularLocations() {
		return wrapForPublic(() -> this.service.getPopularLocations());
	}

	/**
	 * Post review result.?
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/postReview", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity postReview(@RequestBody ReviewForm reviewForm) {
		return wrapForUser(() -> this.service
                .postReview(reviewForm, this.userCache.get((String) httpSession.getAttribute("uid"))));
	}

	/**
	 * Gets number of lodgings.
	 *
	 * @return the number of lodgings
	 */
    @RequestMapping(value = "/api/v1/getNumberOfLodgings", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity getNumberOfLodgings() {
		return wrapForPublic(() -> this.service.getNumberOfLodgings());
	}

	/**
	 * Update picture result.
	 *
	 * @return the result
	 */
    @RequestMapping(value = "/api/v1/admin/updatePicture",
            method = RequestMethod.PATCH, produces="application/json")
    public ResponseEntity updatePicture(@RequestBody ImageUploadForm imageUploadForm) {
		return wrapForAdmin(() -> this.service.
                updatePicture(imageUploadForm));
	}

	/**
	 * Update pictures result.
	 *
	 * @return the result
	 */
	@RequestMapping(value = "/api/v1/admin/updatePictures",
			method = RequestMethod.PATCH, produces="application/json")
	public ResponseEntity updatePictures(@RequestBody List<ImageUploadForm> imageUploadForms) {
		return wrapForAdmin(() -> this.service.
				updatePictures(imageUploadForms));
	}

}
