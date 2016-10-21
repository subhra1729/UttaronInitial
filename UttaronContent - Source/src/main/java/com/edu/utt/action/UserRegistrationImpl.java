/**
 * 
 */
package com.edu.utt.action;


import org.apache.log4j.Logger;

import com.edu.utt.contracts.UserRegistration;
import com.edu.utt.dao.UserDataDao;
import com.edu.utt.dao.ValidationDao;
import com.edu.utt.dto.RegistrationForm;
import com.edu.utt.dto.UserRegistrationResponse;
import com.edu.utt.util.UttaronConstants;

/**
 * @author kabas
 *
 */

public class UserRegistrationImpl implements UserRegistration {
	
	private static final Logger LOG = Logger.getLogger(UserRegistrationImpl.class);
	
	private UserDataDao userDataDao;	

	/**
	 * @param userDataDao the userDataDao to set
	 */
	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}
	
	private ValidationDao validationDao;
	
	
	/**
	 * @param validationDao the validationDao to set
	 */
	public void setValidationDao(ValidationDao validationDao) {
		this.validationDao = validationDao;
	}

	/* (non-Javadoc)
	 * @see com.edu.utt.contracts.UserRegistration#registration(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */	
	/*public UserRegistrationResponse registration(String deviceId, String name, String email, String phone,
			String address, String pinCode) {
		
		System.out.println("deviceId: "+deviceId+" name: "+name+" email: "+email+" phone: "+phone+" address: "+address+" pinCode: "+pinCode);
		
		UserRegistrationResponse registartionResponse = new UserRegistrationResponse();
		
		registartionResponse.setRegistrationSuccessFlag("SUCCESS");
		registartionResponse.setReferralUUID("ABCD12345");
		registartionResponse.setMessage("Registration is successful");
		
		return registartionResponse;
	}*/
	
	public UserRegistrationResponse registrationPost(RegistrationForm registrationForm) {
		
		LOG.info("In UserRegistrationImpl.registrationPost starts registrationForm: "+registrationForm);

		
		String productSerialNumber = registrationForm.getProductSerialNumber();
		
		boolean isProductSerialNumberPresent = false;
		try {
			isProductSerialNumberPresent = validationDao.isProductSerialNumberPresent(productSerialNumber);
		} catch (Exception e) {
			LOG.error("Exception in UserRegistrationImpl.registrationPost",e);
		}
		
		boolean isUnderRegistrationLimit = false;;
		try {
			isUnderRegistrationLimit = validationDao.isUnderRegistrationLimit(productSerialNumber);
		} catch (Exception e) {
			LOG.error("Exception in UserRegistrationImpl.registrationPost",e);
		}
		
		LOG.debug("isProductSerialNumberPresent: "+isProductSerialNumberPresent+" isUnderRegistrationLimit: "+isUnderRegistrationLimit);
		
				
		if(!(isProductSerialNumberPresent&isUnderRegistrationLimit)){
			UserRegistrationResponse registartionResponse = new UserRegistrationResponse();
			registartionResponse.setRegistrationSuccessFlag(UttaronConstants.REGISTRATION_FAILURE);
			if(!isProductSerialNumberPresent){
				registartionResponse.setMessage(UttaronConstants.PRODUCT_DIALLOWED_FAILURE_MESSAGE);
				return registartionResponse;
			}
			
			if(!isUnderRegistrationLimit){
				registartionResponse.setMessage(UttaronConstants.REGISTRATION_LIMIT_CROSSED_FAILURE_MESSAGE);
				return registartionResponse;
			}
		}
		
		UserRegistrationResponse registartionResponse =  userDataDao.getRegistrationResponse(registrationForm);
		
		return registartionResponse;
	}
	
	

}
