/**
 * 
 */
package com.edu.utt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.util.SystemPropertyUtils;

import com.edu.utt.dto.InvoiceForm;

/**
 * @author kabas
 *
 */
public class InvoiceDao {
	
	private static final Logger LOG = Logger.getLogger(InvoiceDao.class);

	private DataSource dataSource;
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Saves Invoice details in the database
	 * @param invoiceForm
	 * @throws Exception 
	 */
	public void saveInvoiceData(InvoiceForm invoiceForm) throws Exception{
		
		LOG.info("InvoiceDao.saveInvoiceData starts invoiceForm:"+invoiceForm);
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("insert into dev1_uttaron.INVOICE (INVOICE_ID,BUYER_NAME,BUYER_NUMBER,BUYER_ADDRESS,BUYER_PIN)  ")
		.append("values (?,?,?,?,?) ");
		
		String query = queryBuilder.toString();
		
		StringBuilder invProdMapQueryBuilder = new StringBuilder();
		invProdMapQueryBuilder.append("insert into dev1_uttaron.INVOICE_PRODUCT_MAP (INVOICE_ID,PRODUCT_SERIAL_ID)  ")
		.append("values (?,?) ");
		
		String invProdMapQuery = invProdMapQueryBuilder.toString();
		
		StringBuilder productQueryBuilder = new StringBuilder();
		productQueryBuilder.append("insert into dev1_uttaron.PRODUCT (PRODUCT_SERIAL_ID,PRODUCT_NAME,PRODUCT_TYPE)  ")
		.append("values (?,?,?) ");
		
		String productQuery = productQueryBuilder.toString();
		
		int isInvoiceDataInserted = 0;
		int isInvProdMapDataInserted = 0;
		int isProductDataInserted = 0;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement psInvoice = conn.prepareStatement(query);
				PreparedStatement psInvProdMap = conn.prepareStatement(invProdMapQuery);
				PreparedStatement psProduct = conn.prepareStatement(productQuery)){
			
			conn.setAutoCommit(false);
			
			psInvoice.setString(1, invoiceForm.getInvoiceId());
			psInvoice.setString(2, invoiceForm.getBuyerName());
			psInvoice.setString(3, invoiceForm.getBuyerNumber()+"");
			psInvoice.setString(4, invoiceForm.getBuyerAddress());
			psInvoice.setString(5, invoiceForm.getBuyerPin());
			
			isInvoiceDataInserted = psInvoice.executeUpdate();
		
			LOG.debug("isInvoiceDataInserted: "+isInvoiceDataInserted);
			
			psInvProdMap.setString(1, invoiceForm.getInvoiceId());
			psInvProdMap.setString(2, invoiceForm.getProductSerialNumber());
			
			isInvProdMapDataInserted = 	psInvProdMap.executeUpdate();	
			

			LOG.debug("isInvProdMapDataInserted: "+isInvProdMapDataInserted);
			
			
			psProduct.setString(1, invoiceForm.getProductSerialNumber());
			psProduct.setString(2, invoiceForm.getProductName());
			psProduct.setString(3, invoiceForm.getProductType());
			
			isProductDataInserted = psProduct.executeUpdate();
			
			LOG.debug("isProductDataInserted: "+isProductDataInserted);
			
			conn.commit();
			
			LOG.info("InvoiceDao.saveInvoiceData ends");
			
			
		} catch (SQLException e) {			
			LOG.error("Exception in InvoiceDao.saveInvoiceData ",e);
			throw e;
		}catch (Exception e) {			
			LOG.error("Exception in InvoiceDao.saveInvoiceData ",e);
			throw e;
		}
		
	}
}
