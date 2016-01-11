package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BankDetailService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update.UpdateBankDetailPage;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class EditBankDetailsPage extends AbstractPage {

	@Inject
	private BankDetailService bdService;
	
	//matadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;
	
	public EditBankDetailsPage(){
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if(meta != null){
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new FeedbackPanel("feedback"));
		
		//new bank detail form components:

		final BankDetail newBankDetail = new BankDetail();
		Form<BankDetail> addBDForm = new Form<BankDetail>("add-bank-detail-form");
		add(addBDForm);

		TextField<String> currencyField = new TextField<String>("currency-of-payment",
				new PropertyModel<String>(newBankDetail, "currencyOfPayment"));
		currencyField.setRequired(true);
		currencyField.add(StringValidator.exactLength(3));
		addBDForm.add(currencyField);

		NumberTextField<Integer> billingNumberField = new NumberTextField<Integer>("billing-number",
				new PropertyModel<Integer>(newBankDetail, "billingNumber"));
		billingNumberField.setRequired(true);
		addBDForm.add(billingNumberField);

		NumberTextField<Double> byrExchangeRateField = new NumberTextField<Double>("byr-exchange-rate",
				new PropertyModel<Double>(newBankDetail, "byrExchangeRate"));
		byrExchangeRateField.setRequired(true);
		addBDForm.add(byrExchangeRateField);

		addBDForm.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
				String upperCaseCurrencyType = newBankDetail.getCurrencyOfPayment().toUpperCase();
				newBankDetail.setCurrencyOfPayment(upperCaseCurrencyType);
				
				if(bdService.findAllTypes().contains(upperCaseCurrencyType)){
					error(getString("error.currencyExists"));
				} else {
					bdService.addBankDetail(newBankDetail);
					setResponsePage(new EditBankDetailsPage());
				}
			}
		});
		
		//bank details list:

		BankDetailsDataProvider bdDataProvider = new BankDetailsDataProvider();
		DataView<BankDetail> dataView = new DataView<BankDetail>("bill-info-list", bdDataProvider, elementsOnPage) {
			@Override
			protected void populateItem(Item<BankDetail> item) {
				final BankDetail bDetail = item.getModelObject();
				item.add(new Label("currencyOfPayment"));
				item.add(new Label("billingNumber"));
				item.add(new Label("byrExchangeRate"));

				Link<Void> deleteLink = new Link<Void>("delete-bank-detail-link") {
					@Override
					public void onClick() {
						bdService.deleteBankDetail(bDetail.getCurrencyOfPayment());
						setResponsePage(new EditBankDetailsPage());
					}
				};
				item.add(deleteLink);
				//visibility check for denying invalid operations
				//("BYR" database field is part of program logic and can not be removed!)
				if ("BYR".equals(bDetail.getCurrencyOfPayment())) {
					deleteLink.setVisible(false);
				}
				
				item.add(new Link<Void>("edit-bank-detail-link") {
					@Override
					public void onClick() {
						setResponsePage(new UpdateBankDetailPage(bDetail));
					}
				});
			}

		};
		add(dataView);
		
		//paging:

		add(new OrderByBorder<Object>("sortCurrencyOfPayment", "currency_of_payment", bdDataProvider));
		add(new OrderByBorder<Object>("sortBillingNumber", "billing_number", bdDataProvider));
		add(new OrderByBorder<Object>("sortByrExchangeRate", "byr_exchange_rate", bdDataProvider));

		add(new PagingNavigator("paging", dataView));
		
		add(new Link<Void>("5-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditBankDetailsPage());
			}
		});
		add(new Link<Void>("10-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditBankDetailsPage());
			}
		});
		add(new Link<Void>("20-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditBankDetailsPage());
			}
		});

	}

	private class BankDetailsDataProvider extends SortableDataProvider<BankDetail, Object> {

		public BankDetailsDataProvider() {
			super();
			setSort("currency_of_payment", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends BankDetail> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return bdService.getAll(first, count, (String) sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return bdService.getCount();
		}

		@Override
		public IModel<BankDetail> model(BankDetail object) {
			return new CompoundPropertyModel<>(object);
		}

	}

}
