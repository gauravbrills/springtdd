/*
 * Hello.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class Hello.
 */
public class Hello {

	/** The name. */
	@NotBlank
	@Size(max = 100)
	private String name;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
