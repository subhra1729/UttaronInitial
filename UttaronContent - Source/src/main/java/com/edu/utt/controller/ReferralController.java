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

import com.edu.utt.action.UserActivitiesImpl;
import com.edu.utt.contracts.UserActivities;
import com.edu.utt.contracts.UserRegistration;
import com.edu.utt.dto.ReferralForm;
import com.edu.utt.dto.ReferralResponse;
import com.edu.utt.dto.RegistrationForm;
import com.edu.utt.dto.UserRegistrationResponse;

/**
 * @author kabas
 *
 */
@RestController
@RequestMapping("/profile")
public class ReferralController {
	
	private static final Logger LOG = Logger.getLogger(ReferralController.class);
	
	@Autowired
	private UserActivities activities;
		
	/**
	 * @param activities the activities to set
	 */
	public void setActivities(UserActivities activities) {
		this.activities = activities;
	}

	/*@RequestMapping(value="/refer",method=RequestMethod.GET)
	public @ResponseBody ReferralResponse referSomebody(@RequestParam(value="referralUUID", defaultValue="test123") String referralUUID, 
			@RequestParam(value="deviceId", defaultValue="dev111") String deviceId){
		
		UserActivities activities = new UserActivitiesImpl();
		
		ReferralResponse refResponse = activities.referSomebody(referralUUID, deviceId);
		
		return refResponse;
	}*/
	
	@RequestMapping(method=RequestMethod.POST)	
	public @ResponseBody ReferralResponse referSomebodyPost(@RequestBody ReferralForm referralForm) {
	
		LOG.info("In ReferralController.referSomebodyPost");
		
		ReferralResponse referralResponse = activities.referSomebodyPost(referralForm);
		
		return referralResponse;
	}

}
