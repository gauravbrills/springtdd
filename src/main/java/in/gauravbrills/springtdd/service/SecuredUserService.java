package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.User;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecuredUserService implements UserService {

	@Override
	@Secured("ROLE_USER")
	public List<User> getAllUsers() {
		return Arrays.asList(new User[] { new User("Mr", "Kevin", "Bernard") });
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void save(User user) {
		log.info("Saving worked");
	}

	@Override
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public User getByName(String name) {
		return new User("Mr", name, "auto");
	}

}
