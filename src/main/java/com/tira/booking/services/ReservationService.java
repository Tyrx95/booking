package com.tira.booking.services;

import com.tira.booking.persistence.model.helpers.DateSuggestion;
import com.tira.booking.persistence.model.helpers.ReservationInquiryResponse;
import com.tira.booking.persistence.model.helpers.UserReservations;
import com.tira.booking.persistence.model.helpers.forms.ReservationConfirmationForm;
import com.tira.booking.persistence.model.helpers.forms.ReservationForm;
import com.tira.booking.persistence.model.tables.Reservation;
import com.tira.booking.persistence.model.tables.LodgingRoom;
import com.tira.booking.persistence.model.tables.User;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Reservation service.
 */
@Service
public class ReservationService extends BaseService {

	private static final Long THREE_DAYS_MILLIS = TimeUnit.DAYS.toMillis(3);
	private static final String DAY_START_TIME = " 00:00:00";
	private static final String DAY_END_TIME = " 23:59:59";

	/**
	 * Gets reservation.
	 *
	 * @param id the id
	 * @return the reservation
	 * @throws Exception the exception
	 */
	public Reservation getReservation(UUID id) throws Exception {
		return (Reservation) getSession()
				.createCriteria(Reservation.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Process inquiry reservation inquiry response.
	 *
	 * @param reservationForm the reservation form
	 * @return the reservation inquiry response
	 * @throws Exception the exception
	 */
	public ReservationInquiryResponse processInquiry(ReservationForm reservationForm) throws Exception {
		ReservationInquiryResponse response = ReservationInquiryResponse.getObject()
				.setInquiry(reservationForm)
				.setNumberOfReservationsToday((Long) getSession()
						.createCriteria(Reservation.class)
						.createAlias("room", "r")
						.add(Restrictions.eq("r.lodgingId", reservationForm.getLodgingId()))
						.setProjection(Projections.rowCount())
						.uniqueResult()
				);


		List<LodgingRoom> freeRooms= this.getFreeRooms(
				reservationForm.getDate(),
				reservationForm.getEndDate(),
				reservationForm.getLodgingId(),
				reservationForm.getNumberOfPeople()
		);

		response.setNumberOfRoomsLeft((long) freeRooms.size());
		if (freeRooms.isEmpty()) {
			response.setDateSuggestions(
					IntStream.rangeClosed(-6, 6).boxed()
							.map(i -> new DateSuggestion(
									new Date(reservationForm.getDate().getTime() + (THREE_DAYS_MILLIS * i)),
									new Date(reservationForm.getEndDate().getTime()+ (THREE_DAYS_MILLIS * i))))
							.filter(dateSuggestion ->
									!getFreeRooms(
											dateSuggestion.getStartDate(),
											dateSuggestion.getEndDate(),
											reservationForm.getLodgingId(),
											reservationForm.getNumberOfPeople()
									).isEmpty())
							.collect(Collectors.toList())
			);
		} else {
			response.setDateSuggestions(new DateSuggestion(reservationForm.getDate(),reservationForm.getEndDate()));
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	private List<LodgingRoom> getFreeRooms(Date startDate, Date endDate , UUID lodgingId, Integer numberOfRooms) {
		List<UUID> potentialRoomIds = getSession().createCriteria(LodgingRoom.class)
				.add(Restrictions.eq("lodgingId", lodgingId))
				.add(Restrictions.eq("numberOfRooms", numberOfRooms))
				.setProjection(Projections.property("id"))
				.list();

		List<UUID> freeRoomIds = new ArrayList<>();

		if (potentialRoomIds.size() > 0) {
			List <Reservation> reservedRooms= getSession().createCriteria(Reservation.class)
					.add(Restrictions.disjunction()
							.add(Restrictions.between("startDate", startDate, endDate))
							.add(Restrictions.between("endDate", startDate, endDate))
							.add(Restrictions.and(
									Restrictions.le("startDate", startDate),
									Restrictions.ge("endDate", endDate)))
							.add(Restrictions.and(
									Restrictions.ge("startDate", startDate),
									Restrictions.le("endDate", endDate)))
					)
					.add(Restrictions.in("room.id", potentialRoomIds))
					.add(Restrictions.eq("isConfirmed", true))
					.list();

			freeRoomIds.addAll(potentialRoomIds.stream().filter(potentialRoomId ->
				!reservedRooms.stream()
						.map(room -> room.getRoom().getId())
						.collect(Collectors.toList())
						.contains(potentialRoomId)
			).collect(Collectors.toList()));
		}

		if (freeRoomIds.size() > 0) {
			return getSession().createCriteria(LodgingRoom.class)
					.add(Restrictions.in("id", freeRoomIds))
					.list();
		} else {
			return new ArrayList<>();
		}
	}


	/**
	 * Post reservation reservation.
	 *
	 * @param reservationForm the reservation form
	 * @return the reservation
	 * @throws Exception the exception
	 */
	public Reservation postReservation(ReservationForm reservationForm) throws Exception {
		Reservation reservation = new Reservation();
		reservation.setStartDate(reservationForm.getDate());
		reservation.setEndDate(reservationForm.getEndDate());
		reservation.setReservedOn(System.currentTimeMillis());
		reservation.setConfirmed(false);

		reservation.setRoom(
				this.getFreeRooms(
						reservation.getStartDate(),
						reservation.getEndDate(),
						reservationForm.getLodgingId(),
						reservationForm.getNumberOfPeople()
				).get(0)
		);

		getSession().save(reservation);

		return reservation;
	}

	/**
	 * Confirm reservation boolean.
	 *
	 * @param reservationConfirmationForm the reservation confirmation form
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean confirmReservation(ReservationConfirmationForm reservationConfirmationForm) throws Exception {
		getSession().saveOrUpdate(reservationConfirmationForm.getReservation());
		return true;
	}

	/**
	 * Gets my reservations.
	 *
	 * @param user the user
	 * @return the my reservations
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public UserReservations getMyReservations(User user) throws Exception {
		return new UserReservations((List<Reservation>) getSession().createCriteria(Reservation.class)
				.add(Restrictions.eq("user.id", user.getId()))
				.addOrder(Order.asc("startDate"))
				.list());
	}

	/**
	 * Gets all reservations.
	 *
	 * @param lodgingId the lodging id
	 * @param dateFilter   the date filter
	 * @return the all reservations
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservations(UUID lodgingId, String dateFilter) {
		return getSession().createCriteria(Reservation.class)
				.createAlias("room", "r")
				.add(Restrictions.eq("r.lodgingId", lodgingId))
		//TODO		.add(Restrictions.between("startDate", r1, r2))
				.add(Restrictions.eq("isConfirmed", true))
				.list();
	}
}
