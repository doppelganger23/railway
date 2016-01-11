package by.epam.grodno.uladzimir_stsiatsko.my_web.metadata;

import java.io.Serializable;

public class CurrencyMetaData implements Serializable {
	
	private String currency;
	
	public CurrencyMetaData(String currency){
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
