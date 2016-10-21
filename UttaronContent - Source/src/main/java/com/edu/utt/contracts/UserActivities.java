/**
 * 
 */
package com.edu.utt.contracts;

import com.edu.utt.dto.ReferralForm;
import com.edu.utt.dto.ReferralResponse;

/**
 * @author kabas
 *
 */
public interface UserActivities {
	
//	ReferralResponse referSomebody(String referralUUID,String deviceId);
	
	/**
	 * Saves profile data into database
	 * @param referralForm
	 * @return
	 */
	public ReferralResponse referSomebodyPost(ReferralForm referralForm);

}
