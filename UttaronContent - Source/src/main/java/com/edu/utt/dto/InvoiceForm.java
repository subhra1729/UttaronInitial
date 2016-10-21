/**
 * 
 */
package com.edu.utt.dto;

/**
 * @author kabas
 *
 */
public class InvoiceForm {
	
	private static final long serialVersionUID = 7526471155622776147L;
	
	private String invoiceId;
	private String productSerialNumber;

	private String buyerName;
	private Double buyerNumber;
	private String buyerAddress;
	private String buyerPin;
	
	private String productName;
	private String productType;
	
	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the productSerialNumber
	 */
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	/**
	 * @param productSerialNumber the productSerialNumber to set
	 */
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	/**
	 * @return the buyerName
	 */
	public String getBuyerName() {
		return buyerName;
	}
	/**
	 * @param buyerName the buyerName to set
	 */
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	/**
	 * @return the buyerNumber
	 */
	public Double getBuyerNumber() {
		return buyerNumber;
	}
	/**
	 * @param buyerNumber the buyerNumber to set
	 */
	public void setBuyerNumber(Double buyerNumber) {
		this.buyerNumber = buyerNumber;
	}
	/**
	 * @return the buyerAddress
	 */
	public String getBuyerAddress() {
		return buyerAddress;
	}
	/**
	 * @param buyerAddress the buyerAddress to set
	 */
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	/**
	 * @return the buyerPin
	 */
	public String getBuyerPin() {
		return buyerPin;
	}
	/**
	 * @param buyerPin the buyerPin to set
	 */
	public void setBuyerPin(String buyerPin) {
		this.buyerPin = buyerPin;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceForm [invoiceId=");
		builder.append(invoiceId);
		builder.append(", productSerialNumber=");
		builder.append(productSerialNumber);
		builder.append(", buyerName=");
		builder.append(buyerName);
		builder.append(", buyerNumber=");
		builder.append(buyerNumber);
		builder.append(", buyerAddress=");
		builder.append(buyerAddress);
		builder.append(", buyerPin=");
		builder.append(buyerPin);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", productType=");
		builder.append(productType);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
