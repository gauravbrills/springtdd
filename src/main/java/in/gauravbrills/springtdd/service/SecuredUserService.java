package in.gauravbrills.springtdd.service;

import in.gauravbrills.springtdd.model.Person;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecuredUserService implements UserService {

	@Override
	@PreAuthorize("authenticated")
	public List<Person> getAllUsers() {
		return Arrays.asList(new Person[] { new Person("Mr", "Kevin", "Bernard") });
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void save(Person user) {
		log.info("Saving worked");
	}

	@Override
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public Person getByName(String name) {
		return new Person("Mr", name, "auto");
	}

}
