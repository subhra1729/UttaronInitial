/**
 * 
 */
package com.edu.utt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kabas
 *
 */
@Controller
public class WelcomeController {
		
	@RequestMapping(value="/welcome",method = RequestMethod.GET)
	public ModelAndView helloWorld(){
		
		System.out.println("In helloWorld");
 
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("msg", "hello world");
 
		return model;
	}

}
