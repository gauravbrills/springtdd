/*
 * LoginTestIntegration.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import in.gauravbrills.springtdd.controller.form.Login;
import in.gauravbrills.springtdd.spring.SpringConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * The Class LoginTestIntegration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringConfig.class })
public class LoginTestIntegration {

	/** The wac. */
	@Autowired
	private WebApplicationContext wac;

	/** The mock mvc. */
	private MockMvc mockMvc;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * Test login.
	 * 
	 * @throws Exception
	 *             the exception
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
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPerformLoginWrongCreds() throws Exception {
		String username = "rudi";
		String password = "fake";

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
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPerformLogin() throws Exception {
		this.mockMvc
				.perform(
						post("/login.html").param("username", "rudi").param(
								"password", "ratlos"))
				.andExpect(status().isOk())
				.andExpect(view().name("login/welcome"))
				.andExpect(model().attributeExists("currentuser"))
				.andExpect(
						request().sessionAttribute("currentuser",
								hasProperty("username", is("rudi"))))
				.andExpect(
						request().sessionAttribute("currentuser",
								hasProperty("password", is("ratlos"))));
	}

}
