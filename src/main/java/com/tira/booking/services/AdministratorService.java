package com.tira.booking.services;


import com.tira.booking.persistence.model.helpers.AdministratorStatistics;
import com.tira.booking.persistence.model.tables.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


/**
 * The type Administrator service.
 */
@Service
public class AdministratorService extends BaseService {

	/**
	 * Delete picture
	 *
	 * @param id the id
	 *
	 */
	public boolean deletePicture(final UUID id) throws Exception {
		LodgingPhoto lodgingPhoto = (LodgingPhoto) getSession().createCriteria(LodgingPhoto.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		String[] path = lodgingPhoto.getPath().split("/");
		UUID imageId = UUID.fromString(path[path.length-1]);
		getSession().delete(lodgingPhoto);
		Image image = (Image) getSession().createCriteria(Image.class)
				.add(Restrictions.eq("id",imageId))
				.uniqueResult();
		getSession().delete(image);
		return true;
	}



	public AdministratorStatistics getAdministratorStatistics(){
      Long numberOfLodgings = Long.valueOf(getSession().createCriteria(Lodging.class)
                .setProjection(Projections.rowCount())
                .uniqueResult().toString());
      Long numberOfLocations = Long.valueOf(getSession().createCriteria(City.class)
              .setProjection(Projections.rowCount())
              .uniqueResult().toString());
      Long numberOfUsers = Long.valueOf(getSession().createCriteria(User.class)
              .setProjection(Projections.rowCount())
              .uniqueResult().toString());
      Long numberOfFacilities = Long.valueOf(getSession().createCriteria(Facility.class)
              .setProjection(Projections.rowCount())
              .uniqueResult().toString());

      return AdministratorStatistics.createAdminStatistics()
              .setNumberOfLodgings(numberOfLodgings)
              .setNumberOfLocations(numberOfLocations)
              .setNumberOfUsers(numberOfUsers)
              .setNumberOfFacilities(numberOfFacilities);
	}

	public boolean addImage(Image image) {
		getSession().save(image);
		return true;
	}

	public Image getImage(UUID uuid) {
		return (Image) getSession().createCriteria(Image.class)
				.add(Restrictions.eq("id", uuid))
				.uniqueResult();
	}
}
