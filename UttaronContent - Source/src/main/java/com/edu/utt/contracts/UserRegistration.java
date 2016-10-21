/**
 * 
 */
package com.edu.utt.contracts;

import com.edu.utt.dto.RegistrationForm;
import com.edu.utt.dto.UserRegistrationResponse;

/**
 * @author kabas
 *
 */
public interface UserRegistration {
	
	// UserRegistrationResponse registration(String deviceId,String name,String email,String phone,String address,String pinCode);
	
	/**
	 * Saves registration details into database
	 * @param registrationForm
	 * @return
	 */
	 UserRegistrationResponse registrationPost(RegistrationForm registrationForm);

}
