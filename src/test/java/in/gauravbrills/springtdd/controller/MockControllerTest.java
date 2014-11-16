package in.gauravbrills.springtdd.controller;

import in.gauravbrills.springtdd.spring.SpringConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=SpringConfig.class)
public class MockControllerTest {
    
    @Autowired
    private WebApplicationContext wac;    
    
    private MockMvc mockMvc;    
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }    
    
    @Test
    public void testHello() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                                   .andExpect(view().name("index"))
                .andExpect(model().attribute("weather", "sunny"))
                .andExpect(model().attribute("color", "green"));
    }
    
    @Test
    public void testHelloSayer() throws Exception {
        this.mockMvc.perform(get("/sayhello/gaurav"))
                .andExpect(view().name("sayhello/hello"))
                .andExpect(model().attribute("name", "gaurav"));
    }
    
    @Test
    public void testHelloForm() throws Exception {
        this.mockMvc.perform(get("/helloform.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("helloform"));
    }

    @Test
    public void testHelloFormSend() throws Exception {
        this.mockMvc.perform(post("/helloform.html").param("name", "saurabh"))
                .andExpect(status().isOk())
                .andExpect(view().name("sayhello/hello"))
                .andExpect(model().attribute("name", "saurabh"));        
    }
    
}
