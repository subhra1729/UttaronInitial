/**
 * 
 */
package com.edu.utt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author kabas
 *
 */
public class ValidationDao {
	
	private static final Logger LOG = Logger.getLogger(ValidationDao.class);
	
	private DataSource dataSource;
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Checks if the productSerialNumber is present in the database
	 * @param productSerialNumber
	 * @return
	 * @throws Exception 
	 */
	public boolean isProductSerialNumberPresent(String productSerialNumber) throws Exception{	
		
		LOG.info("ValidationDao.isProductSerialNumberPresent starts productSerialNumber:"+productSerialNumber);
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(*) from dev1_uttaron.PRODUCT ")
		.append("where PRODUCT_SERIAL_ID = ? ");
		String query = queryBuilder.toString();
		boolean isProductSerialNumberPresent = false;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);){
			
			conn.setAutoCommit(false);
			
			ps.setString(1, productSerialNumber);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int countOfProduct = rs.getInt(1);
				LOG.debug("countOfProduct:"+countOfProduct);
				if(countOfProduct > 0){
					isProductSerialNumberPresent = true;
				}
			}
			
		} catch (SQLException e) {			
			LOG.error("Exception in ValidationDao.isProductSerialNumberPresent ",e);
			throw e;
		}catch (Exception e) {			
			LOG.error("Exception in ValidationDao.isProductSerialNumberPresent ",e);
			throw e;
		}
		LOG.info("ValidationDao.isProductSerialNumberPresent ends");
		return isProductSerialNumberPresent;
	}
	
	/**
	 * checks if the number of devices registered for the productSerialNumber is under the prescribed limit
	 * @param productSerialNumber
	 * @return
	 * @throws Exception 
	 */
	public boolean isUnderRegistrationLimit(String productSerialNumber) throws Exception{
		
		LOG.info("ValidationDao.isUnderRegistrationLimit starts productSerialNumber:"+productSerialNumber);
		
		StringBuilder pdMapQueryBuilder = new StringBuilder();
		pdMapQueryBuilder.append("select count(*) from dev1_uttaron.PRODUCT_DEVICE_MAP ")
		.append("where PRODUCT_SERIAL_ID = ? ");
		
		String pdMapQuery = pdMapQueryBuilder.toString();
		
		StringBuilder svQueryBuilder = new StringBuilder();
		svQueryBuilder.append("select value from dev1_uttaron.STATIC_VALUES ")
		.append("where property = 'DEVICE_REGISTRATION_LIMIT' ");
		
		String svQuery = svQueryBuilder.toString();
		
		boolean isUnderRegistrationLimit = false;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(pdMapQuery);
				PreparedStatement psSv = conn.prepareStatement(svQuery);){
			
			conn.setAutoCommit(false);
			
			ps.setString(1, productSerialNumber);			
			ResultSet rs = ps.executeQuery();
			
			int countOfRegisteredDevices = 0;
			
			while(rs.next()){
				countOfRegisteredDevices = rs.getInt(1);
				LOG.debug("countOfRegisteredDevices :"+countOfRegisteredDevices );				
			}
			
			ResultSet rsSv = psSv.executeQuery();
			
			int deviceRegistrationLimit = 0;
			
			while(rsSv.next()){
				deviceRegistrationLimit = rsSv.getInt(1);
				LOG.debug("deviceRegistrationLimit :"+deviceRegistrationLimit );				
			}
			
			if(countOfRegisteredDevices <= deviceRegistrationLimit){
				isUnderRegistrationLimit = true;
			}
			
		} catch (SQLException e) {			
			LOG.error("Exception in ValidationDao.isUnderRegistrationLimit ",e);
			throw e;
		}catch (Exception e) {			
			LOG.error("Exception in ValidationDao.isUnderRegistrationLimit ",e);
			throw e;
		}
		
		LOG.info("ValidationDao.isUnderRegistrationLimit ends");
		return isUnderRegistrationLimit;
	}

}
