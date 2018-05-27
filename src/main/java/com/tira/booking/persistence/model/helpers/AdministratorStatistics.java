package com.tira.booking.persistence.model.helpers;

/**
 * The type Administrator statistics.
 */
public class AdministratorStatistics {


	private Long numberOfLodgings;
	private Long numberOfUsers;
	private Long numberOfLocations;
	private Long numberOfFacilities;

	private AdministratorStatistics() {}

	public static AdministratorStatistics createAdminStatistics()  { return  new AdministratorStatistics(); }

	/**
	 *
	 * @return the number of lodgings
	 */
	public Long getNumberOfLodgings() {
		return numberOfLodgings;
	}

	/**
	 *
	 * @param numberOfLodgings the number of Lodgings
	 * @return The number of lodgings
	 */
	public AdministratorStatistics setNumberOfLodgings(Long numberOfLodgings) {
		this.numberOfLodgings = numberOfLodgings;
		return this;
	}

	/**
	 *
	 * @return the number of Users
	 */
	public Long getNumberOfUsers() {
		return numberOfUsers;
	}

	/**
	 *
	 * @param numberOfUsers the number of Users
	 * @return the number of users
	 */
	public AdministratorStatistics setNumberOfUsers(Long numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
		return this;
	}

	/**
	 *
	 * @return the number of locations
	 */
	public Long getNumberOfLocations() {
		return numberOfLocations;
	}

	/**
	 *
	 * @param numberOfLocations
	 * @return the number of locations
	 */
	public AdministratorStatistics setNumberOfLocations(Long numberOfLocations) {
		this.numberOfLocations = numberOfLocations;
		return this;
	}

	/**
	 *
	 * @return the number of Facilities
	 */
	public Long getNumberOfFacilities() {
		return numberOfFacilities;
	}

	/**
	 * @param numberOfFacilities
	 * @return the numberOfFacilities
	 */
	public AdministratorStatistics setNumberOfFacilities(Long numberOfFacilities) {
		this.numberOfFacilities = numberOfFacilities;
		return this;
	}
}
