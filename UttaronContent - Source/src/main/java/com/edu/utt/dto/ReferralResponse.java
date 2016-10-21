/**
 * 
 */
package com.edu.utt.dto;

import java.io.Serializable;

/**
 * @author kabas
 *
 */
public class ReferralResponse implements Serializable{
	
	/**
	 * SUCCESS/FAILURE
	 */
	private String referralSuccessFlag;
	
	private String referralSuccessMessage;

	
	/**
	 * @return the referralSuccessFlag
	 */
	public String getReferralSuccessFlag() {
		return referralSuccessFlag;
	}

	/**
	 * @param referralSuccessFlag the referralSuccessFlag to set
	 */
	public void setReferralSuccessFlag(String referralSuccessFlag) {
		this.referralSuccessFlag = referralSuccessFlag;
	}

	/**
	 * @return the referralSuccessMessage
	 */
	public String getReferralSuccessMessage() {
		return referralSuccessMessage;
	}

	/**
	 * @param referralSuccessMessage the referralSuccessMessage to set
	 */
	public void setReferralSuccessMessage(String referralSuccessMessage) {
		this.referralSuccessMessage = referralSuccessMessage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReferralResponse [referralSuccessFlag=");
		builder.append(referralSuccessFlag);
		builder.append(", referralSuccessMessage=");
		builder.append(referralSuccessMessage);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
