/**
 * 
 */
package com.edu.utt.action;

import org.apache.log4j.Logger;

import com.edu.utt.contracts.UserActivities;
import com.edu.utt.dao.UserDataDao;
import com.edu.utt.dto.ReferralForm;
import com.edu.utt.dto.ReferralResponse;
import com.edu.utt.util.UttaronConstants;

/**
 * @author kabas
 *
 */
public class UserActivitiesImpl implements UserActivities {
	
	private static final Logger LOG = Logger.getLogger(UserActivitiesImpl.class);
	
	private UserDataDao userDataDao;	

	/**
	 * @param userDataDao the userDataDao to set
	 */
	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}
	

	/* (non-Javadoc)
	 * @see com.edu.utt.contracts.UserActivities#referSomebody(java.lang.String, java.lang.String)
	 */
	/*public ReferralResponse referSomebody(String referralUUID, String deviceId) {
		
		System.out.println("referralUUID: "+referralUUID+" deviceId: "+deviceId);

		ReferralResponse refResponse = new ReferralResponse();
		
		refResponse.setReferralSuccessFlag("SUCCESS");
		refResponse.setReferralSuccessMessage("Referral Successful");
		return refResponse;
	}*/
	
	public ReferralResponse referSomebodyPost(ReferralForm referralForm) {
		
		LOG.info("In ReferralResponse.referSomebodyPost starts referralForm: "+referralForm);
		
		int isReferralDataUpdated = 0;
		try {
			isReferralDataUpdated = userDataDao.saveReferralData(referralForm);
		} catch (Exception e) {
			LOG.error("Exception in In ReferralResponse.referSomebodyPost ",e);
		}
		
		LOG.debug(" isReferralDataUpdated: "+isReferralDataUpdated);
		
		ReferralResponse refResponse = new ReferralResponse();
		
		if(isReferralDataUpdated == 1){
			refResponse.setReferralSuccessFlag(UttaronConstants.REFERRAL_SUCCESS);
			refResponse.setReferralSuccessMessage(UttaronConstants.REFERRAL_SUCCESS_MESSAGE);
		}else{
			refResponse.setReferralSuccessFlag(UttaronConstants.REFERRAL_FAILURE);
			refResponse.setReferralSuccessMessage(UttaronConstants.REFERRAL_FAILURE_MESSAGE);
		}
		
		return refResponse;
	}


}
