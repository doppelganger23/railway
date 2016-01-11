package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class BankDetail implements Serializable {
	
	private String currencyOfPayment;
	private int billingNumber;
	private double byrExchangeRate;
	
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
	public double getByrExchangeRate() {
		return byrExchangeRate;
	}
	public void setByrExchangeRate(double byrExchangerRate) {
		this.byrExchangeRate = byrExchangerRate;
	}

}
