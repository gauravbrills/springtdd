package in.gauravbrills.springtdd.controller;

import in.gauravbrills.springtdd.model.User;
import in.gauravbrills.springtdd.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserOpController {

	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> listUsers() {
		return userService.getAllUsers();

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void insertUser(@Valid @RequestBody User user) {
		userService.save(user);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public User getByName(@PathVariable String name) {
		return userService.getByName(name);
	}
}
