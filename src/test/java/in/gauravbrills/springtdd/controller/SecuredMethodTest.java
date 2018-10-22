package in.gauravbrills.springtdd.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import in.gauravbrills.springtdd.model.Person;
import in.gauravbrills.springtdd.service.SecuredUserService;
import in.gauravbrills.springtdd.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class })
public class SecuredMethodTest {
	@Autowired
	private UserService userService;

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testListUsersWithtUser() throws Exception {
		assertThat(userService.getAllUsers(), contains(new Person("Mr", "Kevin",
				"Bernard")));
	}

	@Test
	@WithMockUser
	public void testListUsers() throws Exception {
		assertThat(userService.getAllUsers(), contains(new Person("Mr", "Kevin",
				"Bernard")));
	}

	@Test
	@WithMockUser("testuser")
	public void testSave() throws Exception {
		userService.save(new Person("Mr", "Kevin", "Bernard"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER" })
	public void testSaveByUser() throws Exception {
		userService.save(new Person("Mr", "Kevin", "Bernard"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER" })
	public void testGetByName() throws Exception {
		userService.getByName("Kevin");
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@ComponentScan(basePackageClasses = { SecuredUserService.class })
	static class Config {
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.inMemoryAuthentication().withUser("user").password("password")
					.roles("USER");
		}
	}
}
