package by.epam.grodno.uladzimir_stsiatsko.my_web.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

public class BankDetailRenderer extends ChoiceRenderer<BankDetail>{
	
	@Override
	public Object getDisplayValue(BankDetail currencyInfo) {
		return currencyInfo.getCurrencyOfPayment();
		
	}

	@Override
	public String getIdValue(BankDetail currencyInfo, int index) {
		return currencyInfo.getCurrencyOfPayment();
	}

}
