/*
 * LoginTest.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import in.gauravbrills.springtdd.controller.form.Login;
import in.gauravbrills.springtdd.model.LoginDetails;
import in.gauravbrills.springtdd.service.LoginService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * The Class LoginTest.
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class LoginTest {

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The login controller. */
	@InjectMocks
	private LoginController loginController;

	/** The mock login service. */
	@Mock
	private LoginService mockLoginService;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		// Initalizes Mockito
		MockitoAnnotations.initMocks(this);
		// Builds Mock Controllers for Spring Mvc
		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	/**
	 * Test login.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLogin() throws Exception {
		this.mockMvc.perform(get("/login.html")).andExpect(status().isOk())
				.andExpect(view().name("login/form"))
				.andExpect(model().attribute("login", any(Login.class)));
	}

	/**
	 * Test perform login wrong creds.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPerformLoginWrongCreds() throws Exception {
		String username = "rudi";
		String password = "fake";

		Mockito.when(mockLoginService.authenticate(username, password))
				.thenReturn(null);

		this.mockMvc
				.perform(
						post("/login.html").param("username", username).param(
								"password", password))
				.andExpect(status().isOk())
				.andExpect(view().name("login/failed"));
	}

	/**
	 * Test perform login.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPerformLogin() throws Exception {
		final LoginDetails mockUser = Mockito.mock(LoginDetails.class);
		Mockito.when(mockUser.getUsername()).thenReturn("rudi");
		Mockito.when(mockUser.getPassword()).thenReturn("ratlos");
		Mockito.when(mockUser.getId()).thenReturn(1l);

		Mockito.when(
				mockLoginService.authenticate(mockUser.getUsername(),
						mockUser.getPassword())).thenReturn(mockUser);

		this.mockMvc
				.perform(
						post("/login.html").param("username",
								mockUser.getUsername()).param("password",
								mockUser.getPassword()))
				.andExpect(status().isOk())
				.andExpect(view().name("login/welcome"))
				.andExpect(model().attributeExists("currentuser"))
				.andExpect(
						request().sessionAttribute("currentuser",
								hasProperty("username", is("rudi"))))
				.andExpect(
						request().sessionAttribute("currentuser",
								hasProperty("password", is("ratlos"))));

		Mockito.verify(mockLoginService, times(1)).authenticate(
				mockUser.getUsername(), mockUser.getPassword());
	}

}
