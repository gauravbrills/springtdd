package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.User;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecuredUserService implements UserService {

	@Override
	@PreAuthorize("authenticated")
	public List<User> getAllUsers() {
		return Arrays.asList(new User[] { new User("Mr", "Kevin", "Bernard") });
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void save(User user) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		log.info("Saved {}", authentication);
	}

	@Override
	@Secured("ROLE_USER")
	public User getByName(String name) {
		return new User("Mr", name, "auto");
	}

}
