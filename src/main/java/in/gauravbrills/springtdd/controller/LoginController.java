/*
 * LoginController.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import in.gauravbrills.springtdd.controller.form.Login;
import in.gauravbrills.springtdd.model.LoginDetails;
import in.gauravbrills.springtdd.service.LoginService;
 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * The Class LoginController.
 */
@Controller
public class LoginController {
    
    /** The login service. */
    @Autowired private LoginService loginService;
    
    /**
     * Login form.
     *
     * @return the model and view
     */
    @RequestMapping("/login.html")
    public ModelAndView loginForm() {
        return new ModelAndView("login/form", "login", new Login());
    }
    
    /**
     * Do login.
     *
     * @param map the map
     * @param login the login
     * @param session the session
     * @return the string
     */
    @RequestMapping(value="/login.html", method = RequestMethod.POST)
    public String doLogin(final ModelMap map, @ModelAttribute Login login, final HttpSession session) {
        final LoginDetails b = loginService.authenticate(login.getUsername(), login.getPassword());
        if (b != null) {
            map.put("currentuser", b);
            session.setAttribute("currentuser", b);
            return "login/welcome";
        } else {
            return "login/failed";
        }
    }
    
}
