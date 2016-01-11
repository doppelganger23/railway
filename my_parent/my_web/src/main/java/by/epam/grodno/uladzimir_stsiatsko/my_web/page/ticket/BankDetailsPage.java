package by.epam.grodno.uladzimir_stsiatsko.my_web.page.ticket;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;

@AuthorizeInstantiation(value = { "admin", "passenger" })
public class BankDetailsPage extends AbstractPage {

	@Inject
	private TripListService tlService;

	@Inject
	private BillService billService;

	private Bill bill;

	public BankDetailsPage(Bill bill) {
		this.bill = bill;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Label("billing-number", bill.getBillingNumber()));
		

		String currencyOfPayment = bill.getCurrencyOfPayment();
		double paymentValue = bill.getPaymentValue();
		//rounding displayed value to integer for BYR
		if ("BYR".equals(currencyOfPayment)) {
			paymentValue = roundUpScale0(paymentValue);
			add(new Label("payment-value", (int)paymentValue + " " + currencyOfPayment));
		} else {
			add(new Label("payment-value", paymentValue + " " + currencyOfPayment));
		}

		billService.addBill(bill);
		tlService.incrementTicketsSold(bill.getTripListId());
	}

	private double roundUpScale0(double aValue) {
		BigDecimal decimal = new BigDecimal(aValue);
		decimal = decimal.setScale(0, BigDecimal.ROUND_HALF_UP);
		double result = decimal.doubleValue();
		return result;
	}

}
