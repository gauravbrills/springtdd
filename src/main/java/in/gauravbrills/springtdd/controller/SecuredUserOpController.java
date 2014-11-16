package in.gauravbrills.springtdd.controller;

import in.gauravbrills.springtdd.model.User;
import in.gauravbrills.springtdd.service.UserService;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sec/users")
public class SecuredUserOpController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> listUsers(
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User userPr) {
		log.info("Principal : {}", userPr != null ? userPr.getUsername()
				: "No Principal");
		return userService.getAllUsers();

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void insertUser(
			@Valid @RequestBody User user,
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User userPr) {
		userService.save(user);
		log.info("Principal : {}", userPr.getUsername());

	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public User getByName(
			@PathVariable String name,
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User userPr) {
		return userService.getByName(name);

	}
}
