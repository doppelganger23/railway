package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.util.Date;

public class BillInfo implements Serializable {
	
	private int billId;
	private double paymentValue;
	private String currencyOfPayment;
	private int billingNumber;
	private Date creationDate;
	private boolean isPaid;
	private String firstName;
	private String lastName;
	private String email;
	
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public double getPaymentValue() {
		return paymentValue;
	}
	public void setPaymentValue(double paymentValue) {
		this.paymentValue = paymentValue;
	}
	public String getCurrencyOfPayment() {
		return currencyOfPayment;
	}
	public void setCurrencyOfPayment(String currencyOfPayment) {
		this.currencyOfPayment = currencyOfPayment;
	}
	public int getBillingNumber() {
		return billingNumber;
	}
	public void setBillingNumber(int billingNumber) {
		this.billingNumber = billingNumber;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
