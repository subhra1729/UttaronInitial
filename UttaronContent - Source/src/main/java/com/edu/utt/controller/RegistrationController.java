/**
 * 
 */
package com.edu.utt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.utt.action.UserRegistrationImpl;
import com.edu.utt.contracts.UserRegistration;
import com.edu.utt.dto.RegistrationForm;
import com.edu.utt.dto.UserRegistrationResponse;

/**
 * @author kabas
 *
 */
@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	private static final Logger LOG = Logger.getLogger(RegistrationController.class);
	
	@Autowired
	private UserRegistration registration;
	
	/**
	 * @param registration the registration to set
	 */
	public void setRegistration(UserRegistration registration) {
		this.registration = registration;
	}

	@RequestMapping(method=RequestMethod.POST)	
	public @ResponseBody UserRegistrationResponse registrationPost(@RequestBody RegistrationForm registrationForm) {
		
		LOG.info("In RegistrationController.registrationPost starts");
		
		UserRegistrationResponse registartionResponse = registration.registrationPost(registrationForm);
		
		return registartionResponse;
	}

}
