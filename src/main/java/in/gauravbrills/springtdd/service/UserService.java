/*
 * UserService.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.Person;

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
	List<Person> getAllUsers();

	/**
	 * Save.
	 *
	 * @param user the user
	 */
	void save(Person user);

	/**
	 * Gets the by name.
	 *
	 * @param name the name
	 * @return the by name
	 */
	Person getByName(String name);

}
