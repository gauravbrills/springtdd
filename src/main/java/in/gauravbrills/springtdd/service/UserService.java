/*
 * UserService.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.User;

import java.util.List;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();

	/**
	 * Save.
	 *
	 * @param user the user
	 */
	void save(User user);

	/**
	 * Gets the by name.
	 *
	 * @param name the name
	 * @return the by name
	 */
	User getByName(String name);

}
