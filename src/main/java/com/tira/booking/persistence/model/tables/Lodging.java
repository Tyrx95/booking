package com.tira.booking.persistence.model.tables;


import com.tira.booking.persistence.model.BaseModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.UUID;

/**
 * The type Lodging.
 */
@Entity
@Table(name = "lodging")
public class Lodging extends BaseModel {

	private static final String SHORT_TIME_PATTERN = "HH:mm";
	private static final String LONG_TIME_PATTERN = "HH:mm:ss";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "city_id", referencedColumnName = "id")
	private City city;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "price_range")
	private Integer priceRange;

	@Column(name = "cover_image_path")
	private String coverImagePath;

	@Column(name = "profile_image_path")
	private String profileImagePath;

	@Column(name = "description")
	private String description;

	@Column(name = "latitude")
	private Float latitude;

	@Column(name = "longitude")
	private Float longitude;

	@Column(name = "starRating")
	private Integer starRating;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "lodgingId", fetch=FetchType.EAGER)
	private Set<LodgingPhoto> photos;

	@OneToOne(mappedBy = "lodging", cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private AreaInfo areaInfo;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.EAGER)
	@JoinTable(name = "lodging_facility",
			joinColumns = @JoinColumn(name="lodging_id"), inverseJoinColumns = @JoinColumn(name="facility_id"))
	private Set<Facility> facilities;

	@OneToMany(mappedBy = "lodgingId", fetch=FetchType.EAGER)
	private Set<LodgingReview> reviews;

	@OneToMany(mappedBy = "lodgingId", orphanRemoval = true, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<LodgingRoom> rooms;

	@Transient
	private Integer numberOfRatings = 0;

	@Transient
	private Double averageRating;





	/**
	 * Instantiates a new Lodging.
	 */
	public Lodging() { }

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Sets city.
	 *
	 * @param city the city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Gets address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets address.
	 *
	 * @param address the address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets phone.
	 *
	 * @param phone the phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets price range.
	 *
	 * @return the price range
	 */
	public Integer getPriceRange() {
		return priceRange;
	}

	/**
	 * Sets price range.
	 *
	 * @param priceRange the price range
	 */
	public void setPriceRange(Integer priceRange) {
		this.priceRange = priceRange;
	}

	/**
	 * Gets cover image path.
	 *
	 * @return the cover image path
	 */
	public String getCoverImagePath() {
		return coverImagePath;
	}

	/**
	 * Sets cover image path.
	 *
	 * @param coverImagePath the cover image path
	 */
	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
	}

	/**
	 * Gets profile image path.
	 *
	 * @return the profile image path
	 */
	public String getProfileImagePath() {
		return profileImagePath;
	}

	/**
	 * Sets profile image path.
	 *
	 * @param profileImagePath the profile image path
	 */
	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Gets photos.
	 *
	 * @return the photos
	 */
	public Set<LodgingPhoto> getPhotos() {
		return photos;
	}

	/**
	 * Sets photos.
	 *
	 * @param photos the photos
	 */
	public void setPhotos(Set<LodgingPhoto> photos) {
		this.photos = photos;
	}

	/**
	 * Gets facilities.
	 *
	 * @return the facilities
	 */
	public Set<Facility> getFacilities() {
		return facilities;
	}

	/**
	 * Sets facilities.
	 *
	 * @param facilities the facilities
	 */
	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}

	/**
	 * Gets reviews.
	 *
	 * @return the reviews
	 */
	public Set<LodgingReview> getReviews() {
		return this.reviews;
	}

	/**
	 * Sets reviews.
	 *
	 * @param reviews the reviews
	 */
	public void setReviews(Set<LodgingReview> reviews) {
		this.reviews = reviews;
	}

	/**
	 * Gets rooms.
	 *
	 * @return the rooms
	 */
	public Set<LodgingRoom> getRooms() {
		return rooms;
	}

	/**
	 * Sets rooms.
	 *
	 * @param rooms the rooms
	 */
	public void setRooms(Set<LodgingRoom> rooms) {
		this.rooms=rooms;
	}

	/**
	 * Gets number of ratings.
	 *
	 * @return the number of ratings
	 */
	public Integer getNumberOfRatings() {
		return this.reviews.size();
	}

	/**
	 * Gets average rating.
	 *
	 * @return the average rating
	 */
	public Double getAverageRating() {
		OptionalDouble average = this.reviews.stream().mapToInt(LodgingReview::getRating).average();
		return average.isPresent() ? average.getAsDouble() : 0D;
	}

	/**
	 * Gets latitude.
	 *
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}

	/**
	 * Sets latitude.
	 *
	 * @param latitude the latitude
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets longitude.
	 *
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}

	/**
	 * Sets longitude.
	 *
	 * @param longitude the longitude
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * Sets average rating.
	 *
	 * @param averageRating the average rating
	 */
	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	/**
	 *  set star rating
	 * @return the star rating
	 */
	public Integer getStarRating() {
		return starRating;
	}

	/**
	 *
	 * @param starRating the star rating
	 */
	public void setStarRating(Integer starRating) {
		this.starRating = starRating;
	}

	public AreaInfo getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(AreaInfo areaInfo) {
		this.areaInfo = areaInfo;
	}
}
