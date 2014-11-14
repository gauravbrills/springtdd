package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.User;

import java.util.List;

public interface UserService {

	List<User> getAllUsers();

	void save(User user);

	User getByName(String name);

}
