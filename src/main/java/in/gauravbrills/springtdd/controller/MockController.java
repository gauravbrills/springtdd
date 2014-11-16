/*
 * MockController.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import in.gauravbrills.springtdd.controller.form.Hello;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * The Class MockController.
 */
@Controller
@WebAppConfiguration
public class MockController {

    /** The color. */
    @Value("${color}") private String color;
    
    /** The weather. */
    @Value("${weather}") private String weather;
    
    /**
     * Prints the hello.
     *
     * @param map the map
     * @return the string
     */
    @RequestMapping("/index.html")
    public String printHello(final ModelMap map) {
        map.put("color", color);
        map.put("weather", weather);
        return "index";
    }
    
    /**
     * Sayhello.
     *
     * @param map the map
     * @param name the name
     * @return the string
     */
    @RequestMapping("/sayhello/{name}")
    public String sayhello(final ModelMap map, @PathVariable("name") final String name) {
        map.put("name", name);
        map.put("title", "say hello Controller");
        return "sayhello/hello";
    }
    
    /**
     * Hello form.
     *
     * @return the model and view
     */
    @RequestMapping("/helloform.html")
    public ModelAndView helloForm() {
        return new ModelAndView("helloform", "hello", new Hello());
    }
    
    /**
     * Hello form submit.
     *
     * @param helloCmd the hello cmd
     * @param result the result
     * @param map the map
     * @return the model and view
     */
    @RequestMapping(value="helloform.html", method = RequestMethod.POST)
    public ModelAndView helloFormSubmit(@Valid @ModelAttribute("hello") Hello helloCmd, final BindingResult result, final ModelMap map) {
        if (result.hasErrors()) {
            return new ModelAndView("helloform");
        }

        map.put("name", helloCmd.getName());        
        
        return new ModelAndView("sayhello/hello", map);
    }    
}