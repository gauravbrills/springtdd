/*
 * PlainLoginService.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.LoginDetails;

import org.springframework.stereotype.Service;


/**
 * The Class PlainLoginService.
 */
@Service
public class PlainLoginService implements LoginService {

    /* (non-Javadoc)
     * @see in.gauravbrills.springtdd.service.LoginService#authenticate(java.lang.String, java.lang.String)
     */
    @Override
    public LoginDetails authenticate(final String username, final String password) {
        if (username.equals("rudi") && password.equals("ratlos")) {
            final LoginDetails b = new LoginDetails();
            b.setUsername(username);
            b.setPassword(password);
            return b;
        } else {
            return null;
        }
    }
    
}
