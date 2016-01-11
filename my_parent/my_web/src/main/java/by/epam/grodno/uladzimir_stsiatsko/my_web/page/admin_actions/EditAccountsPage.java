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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;
import by.epam.grodno.uladzimir_stsiatsko.my_service.AccountService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update.UpdateAccountPage;

@AuthorizeAction(action=Action.RENDER, roles={"admin"})
public class EditAccountsPage extends AbstractPage {

	@Inject
	private AccountService aService;
	
	//metadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;
	
	public EditAccountsPage(){
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if(meta != null){
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		//list of accounts:
		
		AccountsDataProvider accsDataProvider = new AccountsDataProvider();
		DataView<Account> dataView = new DataView<Account>("accounts-list", accsDataProvider, elementsOnPage) {
			@Override
			protected void populateItem(Item<Account> item) {
				final Account acc = item.getModelObject();
				item.add(new Label("id"));
				item.add(new Label("login"));
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
				item.add(new Label("email"));
				item.add(new Label("accessLevel"));

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						aService.delete(acc);
					}
				};
				item.add(deleteLink);
				//preventing admin from accidentally deliting himself
				if("admin".equals(acc.getAccessLevel())){
					deleteLink.setVisible(false);
				}
				
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new UpdateAccountPage(acc));
					}
				});

			}
		};
		add(dataView);
		
		//paging:

		add(new OrderByBorder<Object>("sortId", "id", accsDataProvider));
		add(new OrderByBorder<Object>("sortLogin", "login", accsDataProvider));
		add(new OrderByBorder<Object>("sortFirstName", "first_name", accsDataProvider));
		add(new OrderByBorder<Object>("sortlName", "last_name", accsDataProvider));
		add(new OrderByBorder<Object>("sortEmail", "email", accsDataProvider));
		add(new OrderByBorder<Object>("sortAccessLevel", "access_level", accsDataProvider));
		
		add(new PagingNavigator("paging", dataView));
		
		add(new Link<Void>("5-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditAccountsPage());
			}
		});
		add(new Link<Void>("10-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditAccountsPage());
			}
		});
		add(new Link<Void>("20-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditAccountsPage());
			}
		});

	}

	private class AccountsDataProvider extends SortableDataProvider<Account, Object> {

		public AccountsDataProvider() {
			super();
			setSort("id", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Account> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return aService.getAll(first, count, (String)sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return aService.getCount();
		}

		@Override
		public IModel<Account> model(Account object) {
			return new CompoundPropertyModel<>(object);
		}

	}

}
