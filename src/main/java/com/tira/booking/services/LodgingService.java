package com.tira.booking.services;

import com.tira.booking.exceptions.ServiceException;
import com.tira.booking.persistence.model.helpers.*;
import com.tira.booking.persistence.model.helpers.forms.ImageUploadForm;
import com.tira.booking.persistence.model.helpers.forms.ReviewForm;
import com.tira.booking.persistence.model.tables.*;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;


import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Lodging service.
 */
@Service
public class LodgingService extends BaseService {

	private static final String BASE_PATH = "/images/";
	private static final String AIRPORT_TYPE = "airport";
	private static final String MARKET_TYPE = "market";
	private static final String LANDMARK_TYPE = "landmark";

	public UUID createLodging(final Lodging lodging) throws Exception {
	    UUID savedLodgingId = (UUID) getSession().save(lodging);
	    if(lodging.getAreaInfo() != null) {
			saveAreaInfo(lodging);
		}
		return savedLodgingId;
	}

	private void saveAreaInfo(Lodging lodging) throws Exception{
        AreaInfo areaInfo = lodging.getAreaInfo();
        if(areaInfo.getId()==null){
        	getSession().save(areaInfo);
		}
        if(areaInfo.getLodging() == null) {
			areaInfo.setLodging(lodging);
		}
		if(areaInfo.getAirports() != null){
			saveObjectInfo(areaInfo.getAirports(),areaInfo, AIRPORT_TYPE);

		}
		if(areaInfo.getLandmarks() != null){
			saveObjectInfo(areaInfo.getLandmarks(),areaInfo, LANDMARK_TYPE);

		}
		if(areaInfo.getMarkets() != null){
			saveObjectInfo(areaInfo.getMarkets(),areaInfo, MARKET_TYPE);
		}
    }


    private void saveObjectInfo(Set<InfoObject> infoObjects, AreaInfo areaInfo, String type) throws ServiceException{
        for (InfoObject infoObject: infoObjects) {
        	switch(type){
				case AIRPORT_TYPE:
					if(infoObject.getAirportsAreaInfo() == null)
						infoObject.setAirportsAreaInfo(areaInfo);
					break;
				case MARKET_TYPE:
					if (infoObject.getMarketsAreaInfo() == null)
						infoObject.setMarketsAreaInfo(areaInfo);
					break;
				case LANDMARK_TYPE:
					if (infoObject.getLandmarksAreaInfo() == null)
						infoObject.setLandmarksAreaInfo(areaInfo);
					break;
        		default: throw new ServiceException("Invalid type for area info");
			}
			if(infoObject.getId() != null){
				getSession().save(infoObject);
			}
			getSession().merge(areaInfo);
		}
	}

	/**
	 * Edit lodging boolean.
	 *
	 * @param lodging the lodging
	 * @throws Exception the exception
	 */
	public Boolean editLodging(final Lodging lodging) throws Exception {
		getSession().merge(lodging);
		if (lodging.getAreaInfo() != null) {
			saveAreaInfo(lodging);
		}
		return true;
	}

	/**
	 * Delete lodging boolean.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	public Boolean deleteLodging(final UUID id) throws Exception {
		Lodging lodging = (Lodging) getSession().createCriteria(Lodging.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(lodging);
		return true;
	}

	/**
	 * Find lodgings with filter pagination adapter.
	 *
	 * @param lodgingFilter the lodging filter
	 * @return the pagination adapter
	 */
	@SuppressWarnings("unchecked")
	public PaginationAdapter<Lodging> findLodgingsWithFilter(final LodgingFilter lodgingFilter) {
		Criteria criteria = getSession().createCriteria(Lodging.class);

		if (lodgingFilter.name != null) {
			criteria.add(Restrictions.ilike("name", lodgingFilter.name, MatchMode.ANYWHERE));
		}

		if (lodgingFilter.facility != null && !lodgingFilter.facility.isEmpty() ) {
			Criteria facilityCriteria = criteria.createCriteria("facilities");
			Disjunction disjunction = Restrictions.disjunction();
			for(String singleFacility : lodgingFilter.facility.split(",")){
                disjunction.add(Restrictions.eq("name", singleFacility));
            }
			facilityCriteria.add(disjunction);

		}

		if (lodgingFilter.cityId != null) {
			criteria.add(Restrictions.eq("city.id", lodgingFilter.cityId));
		}

		if (lodgingFilter.price != null && lodgingFilter.price != 0) {
			criteria.add(Restrictions.eq("priceRange", lodgingFilter.price));
		}

		if (lodgingFilter.rating != null && lodgingFilter.rating != 0){
			criteria.add(Restrictions.eq("starRating", lodgingFilter.rating));
		}

		Long numberOfPages = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()) / lodgingFilter.pageSize;

		criteria.setProjection(null)
				.setFirstResult((lodgingFilter.pageNumber - 1) * lodgingFilter.pageSize)
				.setMaxResults(lodgingFilter.pageSize);



		List<UUID> restaurantIds = criteria.setProjection(Projections.projectionList()
				.add( Projections.distinct(Projections.property("id")))).list();
		List<Lodging> lodgings = new ArrayList<>();
		for (UUID restaurantID: restaurantIds) {
			lodgings.add(getLodgingWithId(restaurantID));
		}

		switch (lodgingFilter.sortBy) {
			case "rating":
				lodgings.sort((o1, o2) -> o2.getAverageRating().compareTo(o1.getAverageRating()));
				break;
		}

		return PaginationAdapter.createOutput()
				.setPageNumber(lodgingFilter.pageNumber)
				.setPageSize(lodgingFilter.pageSize)
				.setModel(lodgings)
				.setNumberOfPages(numberOfPages);
	}

	/**
	 * Gets lodging with id.
	 *
	 * @param id the id
	 * @return the lodging with id
	 */
	public Lodging getLodgingWithId(final UUID id) {
		return (Lodging) getSession().createCriteria(Lodging.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Gets nearby lodgings.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @return the nearby lodgings
	 */
	@SuppressWarnings("unchecked")
	public List<Lodging> getNearbyLodgings(final Float latitude, final Float longitude) {
		return getSession()
				.createSQLQuery("SELECT * FROM lodging WHERE lodging.longitude <> 0 AND lodging.latitude <> 0 ORDER BY ST_Distance(ST_GeomFromText('POINT(' || lodging.longitude || ' ' || lodging.latitude || ')' ,4326), ST_GeomFromText('POINT(' || :longitude || ' ' || :latitude || ')',4326)) ASC LIMIT 3")
				.addEntity(Lodging.class)
				.setParameter("longitude", longitude)
				.setParameter("latitude", latitude)
				.list();
	}

	/**
	 * Gets popular lodgings.
	 *
	 * @return the popular lodgings
	 */
	@SuppressWarnings("unchecked")
	public List<Lodging> getPopularLodgings() {

		List<PopularLodgingsBean> popularLodgingsBeans = getSession().createCriteria(Reservation.class, "reservation")
				.createAlias("reservation.room", "room")
				.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("room.lodgingId").as("lodgingId"))
						.add(Projections.count("room").as("roomCount")))
				.addOrder(Order.asc("roomCount"))
				.setResultTransformer(Transformers.aliasToBean(PopularLodgingsBean.class))
				.setMaxResults(6)
				.list();

		if (popularLodgingsBeans.size() > 0) {
			List<UUID> popularLodgingsIds = popularLodgingsBeans.stream().map(PopularLodgingsBean::getLodgingId).collect(Collectors.toList());

			return (List<Lodging>) getSession().createCriteria(Lodging.class)
					.add(Restrictions.in("id", popularLodgingsIds))
					.addOrder(Order.asc("name"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.list();
		}

		return new ArrayList<>();
	}

	/**
	 * Gets popular locations.
	 *
	 * @return the popular locations
	 */
	@SuppressWarnings("unchecked")
	public List<PopularLocation> getPopularLocations() {
		List<Object[]> popularLocations = getSession().createCriteria(Lodging.class)
				.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("city"))
						.add(Projections.count("id").as("numberOfLodgings")))
				.addOrder(Order.desc("numberOfLodgings"))
				.list();

		return popularLocations.stream().map(PopularLocation::new).collect(Collectors.toList());
	}

	/**
	 * Post review boolean.
	 *
	 * @param reviewForm the review form
	 * @param user       the user
	 */
	public Boolean postReview(final ReviewForm reviewForm, final User user) {
		LodgingReview lodgingReview = (LodgingReview) getSession().createCriteria(LodgingReview.class)
				.add(Restrictions.eq("lodgingId", reviewForm.getLodgingId()))
				.add(Restrictions.eq("userId", user.getId()))
				.uniqueResult();
		if (lodgingReview == null) {
			lodgingReview = new LodgingReview(
					reviewForm.getLodgingId(),
					user.getId(),
					reviewForm.getReviewScore(),
					reviewForm.getReviewText()
			);
		} else {
			lodgingReview.setReview(reviewForm.getReviewText());
			lodgingReview.setRating(reviewForm.getReviewScore());
		}

		getSession().save(lodgingReview);
		updateStarRating(reviewForm.getLodgingId());
		return true;
	}

	private void updateStarRating(final UUID lodgingId) {
		Lodging lodging = getLodgingWithId(lodgingId);
		int starRating;
		if(lodging.getAverageRating() >= 4.75){
			starRating = 5;
		}
		else if(lodging.getAverageRating() >= 4){
			starRating = 4;
		}
		else if(lodging.getAverageRating() >= 3){
			starRating = 3;
		}
		else if(lodging.getAverageRating() >= 2){
			starRating = 2;
		}
		else if(lodging.getAverageRating() >= 0.25){
			starRating = 1;
		}
		else{
			starRating = 0;
		}
		lodging.setStarRating(starRating);
		getSession().save(lodging);
	}


	/**
	 * Gets number of lodgings.
	 *
	 * @return the number of lodgings
	 */
	public Long getNumberOfLodgings() {
		return Long.valueOf(getSession().createCriteria(Lodging.class)
				.setProjection(Projections.rowCount())
				.uniqueResult().toString());
	}

	/**
	 * Update picture string.
	 *
	 * @param imageUploadForm the image upload form
	 * @return the string
	 * @throws Exception the exception
	 */
	public Object updatePicture(final ImageUploadForm imageUploadForm) throws Exception {
		Lodging lodging = (Lodging) getSession().createCriteria(Lodging.class)
				.add(Restrictions.eq("id", imageUploadForm.getLodgingId()))
				.uniqueResult();

		String newImagePath = imageUploadForm.getPath();
		if (imageUploadForm.getImageType().equals("profile")) {
			lodging.setProfileImagePath(newImagePath);
		} else if (imageUploadForm.getImageType().equals("cover")){
			lodging.setCoverImagePath(newImagePath);
		}
		else {
			LodgingPhoto newPhoto = new LodgingPhoto();
			newPhoto.setLodgingId(imageUploadForm.getLodgingId());
			newPhoto.setPath(newImagePath);
			getSession().persist(newPhoto);
			return newPhoto;
		}

		getSession().update(lodging);
		return "{ \"imageFor\": \"" + imageUploadForm.getImageType() + "\", \"url\": \"" + newImagePath + "\"}";
	}

    /**
     * Update pictures string.
     *
     * @param imageUploadForms the image upload forms
     * @return the string
     * @throws Exception the exception
     */
    public String updatePictures(final List<ImageUploadForm> imageUploadForms) throws Exception {
        for(ImageUploadForm imageUploadForm : imageUploadForms){
            updatePicture(imageUploadForm);
        }
        return "{}";
    }
}
