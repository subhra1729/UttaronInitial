/**
 * 
 */
package com.edu.utt.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.utt.dto.ReferralForm;
import com.edu.utt.dto.ReferralResponse;
import com.edu.utt.dto.RegistrationForm;
import com.edu.utt.dto.UserRegistrationResponse;
import com.edu.utt.util.RandomUtils;
import com.edu.utt.util.UttaronConstants;



/**
 * @author kabas
 *
 */
public class UserDataDao {
	
	private static final Logger LOG = Logger.getLogger(UserDataDao.class);
		
	private DataSource dataSource;
		
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Saves Registration details in the database
	 * @param registrationForm
	 * @return
	 */
	public Map<String,String> saveRegistrationDetails(RegistrationForm registrationForm){
		
		LOG.info("UserDataDao.saveRegistrationDetails starts registrationForm:"+registrationForm);
		
		Map<String,String> registrationMap = new HashMap<String,String>();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("insert into dev1_uttaron.USER_DETAILS (device_id,name,phone,email,address,pincode,last_updated_by) ")
		.append("values (?,?,?,?,?,?,?)");
		
		String query = queryBuilder.toString();
		
		
		StringBuilder referralQueryBuilder = new StringBuilder();
		referralQueryBuilder.append(" insert into dev1_uttaron.REFERRAL_DETAILS(REFERRAL_UID,DEVICE_ID) ")
		.append("values(?,?)");
		
		String referralInsertQuery = referralQueryBuilder.toString();
						
		StringBuilder pdMapQueryBuilder = new StringBuilder();
		pdMapQueryBuilder.append(" insert into dev1_uttaron.PRODUCT_DEVICE_MAP (PRODUCT_SERIAL_ID,DEVICE_ID) ")
		.append("values (?,?) ");
		
		String pdMapQuery = pdMapQueryBuilder.toString();
		
		String referralUID = null;
		try {
			referralUID = generateReferralUID();
		} catch (Exception e1) {
			LOG.error("Error in UserDataDao.saveRegistrationDetails while generating referralUID ",e1);		
			registrationMap.put("isInserted", "");
			registrationMap.put("referralUID", "");
			return registrationMap;
		}
		
		
		int isInserted = 0;
		int isRegDataInserted = 0;
		int isReferralDataInserted = 0;
		int isPdMapDataInserted = 0;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				PreparedStatement psReferral = conn.prepareStatement(referralInsertQuery);
				PreparedStatement psPdMapReferral = conn.prepareStatement(pdMapQuery) ){
			
			conn.setAutoCommit(false);
			
			ps.setString(1, registrationForm.getDeviceId());
			ps.setString(2, registrationForm.getName());
			ps.setString(3, registrationForm.getPhone());
			ps.setString(4, registrationForm.getEmail());
			ps.setString(5, registrationForm.getAddress());
			ps.setString(6, registrationForm.getPinCode());
			ps.setString(7, UttaronConstants.APPLICATION_NAME);
			
			isRegDataInserted = ps.executeUpdate();
			
			LOG.debug("isRegDataInserted:"+isRegDataInserted);
			
			psReferral.setString(1, referralUID);
			psReferral.setString(2, registrationForm.getDeviceId());
			
			
			isReferralDataInserted = psReferral.executeUpdate();
			
			LOG.debug("isReferralDataInserted:"+isReferralDataInserted);
			
			psPdMapReferral.setString(1, registrationForm.getProductSerialNumber());
			psPdMapReferral.setString(2, registrationForm.getDeviceId());
			
			isPdMapDataInserted = psPdMapReferral.executeUpdate();
			
			LOG.debug("isPdMapDataInserted:"+isPdMapDataInserted);
			
			conn.commit();
			
			if(isRegDataInserted == 1 && isReferralDataInserted == 1 && isPdMapDataInserted == 1 ){
				isInserted = 1;
			}
			registrationMap.put("isInserted", isInserted+"");
			registrationMap.put("referralUID", referralUID);
			
		} catch (SQLException e) {			
			LOG.error("Error in UserDataDao.saveRegistrationDetails ",e);		
			registrationMap.put("isInserted", "");
			registrationMap.put("referralUID", "");
		}catch (Exception e) {			
			LOG.error("Error in UserDataDao.saveRegistrationDetails ",e);		
			registrationMap.put("isInserted", "");
			registrationMap.put("referralUID", "");
		}
		
		
		LOG.info("UserDataDao.saveRegistrationDetails ends");
		
		return registrationMap;
	}
	
	/**
	 * Gets the referral id for  particular device
	 * @param deviceId
	 * @return
	 * @throws Exception 
	 */
	private String getReferralUIdForDeviceId(String deviceId) throws Exception{
		
		LOG.info("UserDataDao.getReferralUIdForDeviceId starts deviceId:"+deviceId);
		
		String referralUid = null;
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select REFERRAL_UID from dev1_uttaron.REFERRAL_DETAILS ")
		.append("where DEVICE_ID = ? ");
		String query = queryBuilder.toString();		
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);){
			
			conn.setAutoCommit(false);
			
			ps.setString(1, deviceId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				referralUid = rs.getString(1);
				LOG.debug("getReferralUIdForDeviceId referralUid:"+referralUid);				
			}
			
		} catch (SQLException e) {			
			LOG.error("Exception in UserDataDao.getReferralUIdForDeviceId ",e);
			throw e;
		}	
		catch (Exception e) {			
			LOG.error("Exception in UserDataDao.getReferralUIdForDeviceId ",e);
			throw e;
		}	
		LOG.info("UserDataDao.getReferralUIdForDeviceId ends");
		return referralUid;
	}
	
	/**
	 * Prepares the response object for the Registration
	 * @param registrationForm
	 * @return
	 */
	public UserRegistrationResponse getRegistrationResponse(RegistrationForm registrationForm){
		
		String existingReferralUid = null;
		try {
			existingReferralUid = getReferralUIdForDeviceId(registrationForm.getDeviceId());
		} catch (Exception e) {
			LOG.error("Exception in UserDataDao.getRegistrationResponse ",e);
			UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
			userRegistrationResponse.setRegistrationSuccessFlag(UttaronConstants.REFERRAL_FAILURE);
			userRegistrationResponse.setMessage(UttaronConstants.REFERRAL_FAILURE_MESSAGE);
			userRegistrationResponse.setReferralUUID(existingReferralUid);
			return userRegistrationResponse;
		}
		if(null != existingReferralUid){
			UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
			userRegistrationResponse.setRegistrationSuccessFlag(UttaronConstants.REGISTRATION_SUCCESS);
			userRegistrationResponse.setMessage(UttaronConstants.REFERRALUID_EXISTS_MESSAGE);
			userRegistrationResponse.setReferralUUID(existingReferralUid);
			return userRegistrationResponse;
		}
		
		Map<String,String> registrationMap = saveRegistrationDetails(registrationForm);
		
		int isUserDetailsInserted = Integer.parseInt(registrationMap.get("isInserted"));
		UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
		if(isUserDetailsInserted == 1){
			String referralUID = registrationMap.get("referralUID"); 
			userRegistrationResponse.setRegistrationSuccessFlag(UttaronConstants.REGISTRATION_SUCCESS);
			userRegistrationResponse.setMessage(UttaronConstants.REGISTRATION_SUCCESS_MESSAGE);
			userRegistrationResponse.setReferralUUID(referralUID);
		}else{
			userRegistrationResponse.setRegistrationSuccessFlag(UttaronConstants.REGISTRATION_FAILURE);
			userRegistrationResponse.setMessage(UttaronConstants.REGISTRATION_FAILURE_MESSAGE);
			userRegistrationResponse.setReferralUUID("");
		}
		
		return userRegistrationResponse;
	}
	
	/*private String generateReferralUID(){
		return UUID.randomUUID().toString();
	}*/
	
	private String generateReferralUID() throws Exception{
		String randomId = RandomUtils.getRandom(8);
		if(!isReferalIdPresent(randomId)){
			return randomId;
		}else{
			return generateReferralUID();
		}
		
	}
	
	private boolean isReferalIdPresent(String randomId) throws Exception{
		
		LOG.info("UserDataDao.isReferalIdPresent starts randomId:"+randomId);
		
		int numberOfRowsFound = 0;
		boolean isReferalIdPresent = false;
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(*) from dev1_uttaron.REFERRAL_DETAILS ")
		.append("where referral_uid = ? ");
		String query = queryBuilder.toString();		
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);){
			
			conn.setAutoCommit(false);
			
			ps.setString(1, randomId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				numberOfRowsFound = rs.getInt(1);
				LOG.debug("isReferalIdPresent numberOfRowsFound:"+numberOfRowsFound);				
			}
			if(numberOfRowsFound > 0){
				isReferalIdPresent = true;
			}
			
		} catch (SQLException e) {			
			LOG.error("Exception in UserDataDao.isReferalIdPresent ",e);
			throw e;
		}	
		catch (Exception e) {			
			LOG.error("Exception in UserDataDao.isReferalIdPresent ",e);
			throw e;
		}	
		LOG.info("UserDataDao.isReferalIdPresent ends");
		return isReferalIdPresent;
	}
	
	
	/**
	 * Saves referral details to the database
	 * @param referralForm
	 * @return
	 * @throws Exception 
	 */
	public int saveReferralData(ReferralForm referralForm) throws Exception{
		
		LOG.info("UserDataDao.saveReferralData starts referralForm:"+referralForm);
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("update dev1_uttaron.REFERRAL_DETAILS ")
		.append("set name = ?, phone = ?, email = ?, address = ?, pincode = ?, last_updated_by = ?, ")
		.append("BANK_NAME = ?, ACCOUNT_HOLDER_NAME = ?, ACCOUNT_NUMBER = ?, IFSC_CODE = ? ")
		.append("where referral_uid = ?");
		
		String query = queryBuilder.toString();
		
		int isUpdated = 0;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)){
			
			conn.setAutoCommit(false);
			
			
			ps.setString(1, referralForm.getName());
			ps.setString(2, referralForm.getPhone());
			ps.setString(3, referralForm.getEmail());
			ps.setString(4, referralForm.getAddress());
			ps.setString(5, referralForm.getPinCode());
			ps.setString(6, UttaronConstants.APPLICATION_NAME);
			ps.setString(7, referralForm.getBankName());
			ps.setString(8, referralForm.getAccountHolderName());
			ps.setString(9, referralForm.getAccountNumber());
			ps.setString(10, referralForm.getIfscCode());
			
			
			
			ps.setString(11, referralForm.getReferralUID());
			
			isUpdated = ps.executeUpdate();	
			
			LOG.debug("isUpdated:"+isUpdated);
			
			conn.commit();
			
			LOG.info("UserDataDao.saveReferralData ends");
			
		} catch (SQLException e) {			
			LOG.error("Error  in UserDataDao.saveReferralData");
			throw e;
		}catch (Exception e) {			
			LOG.error("Error  in UserDataDao.saveReferralData");
			throw e;
		}		
		
		
		return isUpdated;
	}

}
