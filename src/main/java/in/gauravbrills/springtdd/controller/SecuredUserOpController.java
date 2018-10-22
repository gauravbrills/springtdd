package in.gauravbrills.springtdd.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.gauravbrills.springtdd.model.Person;
import in.gauravbrills.springtdd.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sec/users")
public class SecuredUserOpController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Person> listUsers(Principal userPr) {
		log.info("Principal : {}", userPr != null ? userPr.getName() : "No Principal");
		return userService.getAllUsers();

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void insertUser(@Valid @RequestBody Person user, Principal principal) {
		userService.save(user);
		log.info("Principal : {}", principal.getName());

	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Person getByName(@PathVariable String name, Principal principal) {
		// public void info(String format, Object... arguments);
		log.info("User Searched {} ,Principal : {}", name, principal.getName());
		// Using Formatter
		Logger formatterLogger = LogManager.getFormatterLogger("in.gauravbrills.springtdd");
		formatterLogger.info("Logged in User %s", principal.getName());
		return userService.getByName(name);

	}
}
