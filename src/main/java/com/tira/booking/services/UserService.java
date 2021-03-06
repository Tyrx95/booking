package com.tira.booking.services;


import com.tira.booking.exceptions.ServiceException;
import com.tira.booking.persistence.model.helpers.forms.LoginForm;
import com.tira.booking.persistence.model.helpers.forms.RegisterForm;
import com.tira.booking.persistence.model.tables.City;
import com.tira.booking.persistence.model.tables.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type User service.
 */
@Service
public class UserService extends BaseService {

	/**
	 * Authenticate account.
	 *
	 * @param loginForm the login form
	 * @return the account
	 * @throws ServiceException     the service exception
	 * @throws NullPointerException the null pointer exception
	 */
	public User authenticate(final LoginForm loginForm) throws ServiceException, NullPointerException {
		try {
			if (checkCredentials(
					loginForm.getEmail(),
					base64Encode(
							Passwords.hash(
									loginForm.getPassword().toCharArray(),
									getSaltForUser(loginForm.getEmail())
							)
					)
			)) {
				return get(loginForm.getEmail());
			} else {
				throw new ServiceException("Login Error", "Invalid Password");
			}
		} catch (NullPointerException e) {
			throw new ServiceException("Login Error", "Invalid Email");
		}
	}

	/**
	 * Register account.
	 *
	 * @param registerForm the register form
	 * @return the account
	 */
	public User register(final RegisterForm registerForm) {
		try {
			City city = (City) getSession().createCriteria(City.class)
					.add(Restrictions.eq("id", registerForm.getCityId()))
					.uniqueResult();
			User newUser = registerForm.createAccount(city);
			getSession().save(newUser);
			return newUser;
		} catch (Exception e) {
			throw e;
		}
	}

	private byte[] getSaltForUser(final String email) {
		String userSalt = (String) getSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.setProjection(Projections.property("salt"))
				.uniqueResult();

		if (userSalt != null) {
			return base64Decode(userSalt);
		} else {
			return null;
		}
	}

	private Boolean checkCredentials(final String email, final String hash) {
		User user = (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("hash", hash))
				.uniqueResult();

		return user != null;
	}

	/**
	 * Get account.
	 *
	 * @param email the email
	 * @return the account
	 */
	public User get(final String email) {
		return (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

	/**
	 * Gets all users.
	 *
	 * @return the all users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return (List<User>) getSession().createCriteria(User.class).list();
	}

	/**
	 * Gets user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User getUser(final UUID userId) {
		return (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("id", userId))
				.uniqueResult();
	}

	/**
	 * Edit user boolean.
	 *
	 * @param user the user
	 * @return the boolean
	 */
	public Boolean editUser(User user) {
		if (user.getId() != null) {
			User dbUser = this.getUser(user.getId());
			dbUser.setIsAdmin(user.getIsAdmin());

			getSession().update(dbUser);
			return true;
		}
		return false;
	}


	/**
	 * Delete user boolean.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	public Boolean deleteUser(final UUID id) throws Exception {
		User user = (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(user);
		return true;
	}
}
