/**
 * 
 */
package com.edu.utt.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edu.utt.action.InvoiceAction;
import com.edu.utt.dao.ValidationDao;
import com.edu.utt.dto.InvoiceForm;

/**
 * @author kabas
 *
 */
@Controller
public class InvoiceController {
	
	private static final Logger LOG = Logger.getLogger(InvoiceController.class);
	
	@Autowired
	private InvoiceAction invoiceAction;
	
	
	/**
	 * @param invoiceAction the invoiceAction to set
	 */
	public void setInvoiceAction(InvoiceAction invoiceAction) {
		this.invoiceAction = invoiceAction;
	}

	@RequestMapping(value="/invoice", method=RequestMethod.GET)
    public String invoiceForm(Model model) {
		LOG.info("In InvoiceController.invoiceForm starts :");
        model.addAttribute("invoiceForm", new InvoiceForm());
        return "invoice";
    }

    @RequestMapping(value="/invoice", method=RequestMethod.POST)
    public String saveInvoice(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm, Model model) {
    	LOG.info("In InvoiceController.saveInvoice starts invoiceForm:"+invoiceForm);
    	
    	try {
			invoiceAction.saveInvoiceData(invoiceForm);
		} catch (Exception e) {
			// TODO Use Error Page
			LOG.error("Exception in InvoiceController.saveInvoice ",e);			
		}
    	
        model.addAttribute("invoiceForm", new InvoiceForm());
        return "invoice";
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex){
    	LOG.error("Request "+req.getRequestURL() +"raised: "+ex);
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("exception",ex);
    	modelAndView.addObject("url",req.getRequestURL());
    	modelAndView.setViewName("error");
    	
    	return modelAndView;
    }
}
