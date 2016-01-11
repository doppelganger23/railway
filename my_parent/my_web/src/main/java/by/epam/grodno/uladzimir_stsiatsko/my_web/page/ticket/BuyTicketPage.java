package by.epam.grodno.uladzimir_stsiatsko.my_web.page.ticket;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BankDetailService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomSession;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.CurrencyMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;

public class BuyTicketPage extends AbstractPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyTicketPage.class);

	//metadata for preferred currency type, to which payment value will be conversed
	public static MetaDataKey<CurrencyMetaData> CURRENCY = new MetaDataKey<CurrencyMetaData>() {
	};

	@Inject
	private BankDetailService bdService;
	@Inject
	private BillService billService;
	@Inject
	private Bill bill;

	private String displayedCurrency;
	private double displayedValue;

	private SearchResult sResult;
	private Integer currentuserid;

	public BuyTicketPage(SearchResult sResult) {
		this.sResult = sResult;// for onInitialize()

		bill.setTripListId(sResult.getTripId());
		bill.setFromBlock(sResult.getFromBlock());
		bill.setToBlock(sResult.getToBlock());
		// using 3 fields above to get 4rth value
		bill.setPaymentValue(billService.countPrice(bill));

		currentuserid = CustomSession.get().getCurrentuserid();
		//converting null Integers to 0 for using in "int" method
		if (currentuserid == null) {
			currentuserid = 0;
		}
		bill.setAccountId(currentuserid);
		LOGGER.debug("setting account id to " + currentuserid);

		bill.setCreationDate(new Date());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		//chosen trip list information
		add(new Label("chosen-route-name", sResult.getRouteName()));
		add(new Label("chosen-route-type", sResult.getRouteType()));
		add(new Label("chosen-train", sResult.getTrain()));
		add(new Label("chosen-from-station", sResult.getFromStation()));
		add(new Label("chosen-departure-date", sResult.getDepartureDate()));
		add(new Label("chosen-to-station", sResult.getToStation()));
		add(new Label("chosen-arrival-date", sResult.getArrivalDate()));
		
		//confirm form components:

		Form<Void> confirmForm = new Form<Void>("confirmation-form");

		//checking metadata and converting payment value to chosen currency
		CurrencyMetaData meta = getSession().getMetaData(CURRENCY);
		if (meta == null) {
			LOGGER.debug("metadata is null");
			displayedCurrency = "BYR";
			displayedValue = roundUpScale2(bill.getPaymentValue());
		} else {
			LOGGER.debug("metadata is not null");
			displayedCurrency = meta.getCurrency();
			displayedValue = roundUpScale2(bill.getPaymentValue() / bdService.getByrExchangeRate(displayedCurrency));
		}

		confirmForm.add(new Label("payment-value", displayedValue));
		final Model<String> currencyModel = new Model<>(displayedCurrency);

		final DropDownChoice<String> currencyChoice = new DropDownChoice<String>("currency-type", currencyModel,
				bdService.findAllTypes()) {
			//sending chosen currency type to metadata and reloading page
			@Override
			protected void onSelectionChanged(final String newCurrency) {
				LOGGER.debug("It's " + newCurrency + " time!");
				getSession().setMetaData(CURRENCY, new CurrencyMetaData(newCurrency));
				setResponsePage(new BuyTicketPage(sResult));
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				return true;
			}
		};
		;
		confirmForm.add(currencyChoice);

		SubmitLink submitLink = new SubmitLink("confirm-ticket-link") {
			@Override
			public void onSubmit() {
				bill.setBillingNumber(bdService.getBillingNumber(displayedCurrency));
				bill.setCurrencyOfPayment(displayedCurrency);
				bill.setPaymentValue(displayedValue);

				if (currentuserid != 0) {
					setResponsePage(new BankDetailsPage(bill));
				} else {
					setResponsePage(new NameInputPage(bill));
				}
			}
		};
		confirmForm.add(submitLink);
		add(confirmForm);

	}

	private double roundUpScale2(double aValue) {
		BigDecimal decimal = new BigDecimal(aValue);
		decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		double result = decimal.doubleValue();
		return result;
	}

}
