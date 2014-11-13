/*
 * LoginDetails.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.Objects;


/**
 * The Class LoginDetails.
 */
@Entity
public class LoginDetails implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	private Long id;

	/** The username. */
	private String username;
	
	/** The password. */
	private String password;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(username);
	}

}
