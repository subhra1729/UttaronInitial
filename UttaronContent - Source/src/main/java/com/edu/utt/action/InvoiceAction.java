package com.edu.utt.action;

import org.apache.log4j.Logger;

import com.edu.utt.controller.RegistrationController;
import com.edu.utt.dao.InvoiceDao;
import com.edu.utt.dto.InvoiceForm;

public class InvoiceAction {
	
	private static final Logger LOG = Logger.getLogger(InvoiceAction.class);
	
	private InvoiceDao invoiceDao;

	/**
	 * @param invoiceDao the invoiceDao to set
	 */
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
	
	public void saveInvoiceData(InvoiceForm invoiceForm) throws Exception{
		
		invoiceDao.saveInvoiceData(invoiceForm);
		
	}

}
