/**
 * 
 */
package com.edu.utt.dto;

import java.io.Serializable;

/**
 * @author kabas
 *
 */
public class UserRegistrationResponse implements Serializable{
	
	/**
	 * SUCCESS/FAILURE
	 */
	private String registrationSuccessFlag;
	
	private String message;
	
	private String referralUUID;

	public String getRegistrationSuccessFlag() {
		return registrationSuccessFlag;
	}

	public void setRegistrationSuccessFlag(String registrationSuccessFlag) {
		this.registrationSuccessFlag = registrationSuccessFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReferralUUID() {
		return referralUUID;
	}

	public void setReferralUUID(String referralUUID) {
		this.referralUUID = referralUUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRegistrationResponse [registrationSuccessFlag=");
		builder.append(registrationSuccessFlag);
		builder.append(", message=");
		builder.append(message);
		builder.append(", referralUUID=");
		builder.append(referralUUID);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
