package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Bill implements Serializable {
	
	private int id;
	private int accountId;
	private int tripListId;
	private double paymentValue;
	private boolean isPaid;
	private int billingNumber;
	private int fromBlock;
	private int toBlock;
	private Date creationDate;
	private String currencyOfPayment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getTripListId() {
		return tripListId;
	}
	public void setTripListId(int tripListId) {
		this.tripListId = tripListId;
	}
	public double getPaymentValue() {
		return paymentValue;
	}
	public void setPaymentValue(double paymentValue) {
		this.paymentValue = paymentValue;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public int getBillingNumber() {
		return billingNumber;
	}
	public void setBillingNumber(int billingNumber) {
		this.billingNumber = billingNumber;
	}
	public int getFromBlock() {
		return fromBlock;
	}
	public void setFromBlock(int fromBlock) {
		this.fromBlock = fromBlock;
	}
	public int getToBlock() {
		return toBlock;
	}
	public void setToBlock(int toBlock) {
		this.toBlock = toBlock;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCurrencyOfPayment() {
		return currencyOfPayment;
	}
	public void setCurrencyOfPayment(String currencyOfPayment) {
		this.currencyOfPayment = currencyOfPayment;
	}
	
	
}
