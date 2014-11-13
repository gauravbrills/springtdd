/*
 * LoginService.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.LoginDetails;


/**
 * The Interface LoginService.
 */
public interface LoginService {
    
    /**
     * Authenticate.
     *
     * @param username the username
     * @param password the password
     * @return the login details
     */
    LoginDetails authenticate(String username, String password);
    
}
