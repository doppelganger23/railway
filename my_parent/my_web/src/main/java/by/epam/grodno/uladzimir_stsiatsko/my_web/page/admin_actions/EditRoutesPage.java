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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update.UpdateRouteMapPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update.UpdateRoutePage;

@AuthorizeAction(action=Action.RENDER, roles={"admin"})
public class EditRoutesPage extends AbstractPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EditRoutesPage.class);
	
	@Inject
	private RouteService rService;
	
	@Inject
	private TripListService tlService;
	
	//metadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;
	
	public EditRoutesPage(){
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if(meta != null){
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//new route form components:
		
		final Route newRoute = new Route();
		Form<Route> newRouteForm = new Form<>("new-route-form");
		add(newRouteForm);

		TextField<String> routeNameField = new TextField<String>("route-name",
				new PropertyModel<String>(newRoute, "routeName"));
		routeNameField.setRequired(true);
		routeNameField.add(StringValidator.maximumLength(150));
		newRouteForm.add(routeNameField);
		
		TextField<String> routeTypeField = new TextField<String>("route-type",
				new PropertyModel<String>(newRoute, "routeType"));
		routeTypeField.setRequired(true);
		routeTypeField.add(StringValidator.maximumLength(10));
		newRouteForm.add(routeTypeField);
		
		NumberTextField<Double> priceForKmField = new NumberTextField<Double>("price-for-kilometer",
				new PropertyModel<Double>(newRoute, "priceForKilometer"));
		priceForKmField.setRequired(true);
		newRouteForm.add(priceForKmField);

		newRouteForm.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
					newRoute.setRouteName(newRoute.getRouteName().toUpperCase());
					newRoute.setRouteType(newRoute.getRouteType().toUpperCase());
					//getting id from database to complete model object
					int newRouteId = rService.add(newRoute);
					newRoute.setId(newRouteId);
					
					LOGGER.debug("route created with id = " + newRouteId);
					setResponsePage(new UpdateRouteMapPage(newRoute));
			}
		});
		
		//routes list:

		RoutesDataProvider routesDataProvider = new RoutesDataProvider();
		DataView<Route> dataView = new DataView<Route>("routes-list", routesDataProvider, elementsOnPage) {
			@Override
			protected void populateItem(Item<Route> item) {
				final Route route = item.getModelObject();
				item.add(new Label("id"));
				item.add(new Label("routeName"));
				item.add(new Label("routeType"));
				item.add(new Label("priceForKilometer"));

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						rService.delete(route);
					}
				};
				item.add(deleteLink);
				//visibility check for denying invalid operations
				//(used trip lists must stay in database for billing operations history)
				if(tlService.containsRoute(route.getId())){
					deleteLink.setVisible(false);
				}
				
				item.add(new Link<Void>("modify-link") {
					@Override
					public void onClick() {
						setResponsePage(new UpdateRoutePage(route));
					}
				});

			}
		};
		add(dataView);
		
		//paging:

		add(new OrderByBorder<Object>("sortId", "id", routesDataProvider));
		add(new OrderByBorder<Object>("sortRouteName", "route_name", routesDataProvider));
		add(new OrderByBorder<Object>("sortRouteType", "route_type", routesDataProvider));
		add(new OrderByBorder<Object>("sortPriceForKilometer", "price_for_kilometer", routesDataProvider));
		
		add(new PagingNavigator("paging", dataView));
		
		add(new Link<Void>("5-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditRoutesPage());
			}
		});
		add(new Link<Void>("10-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditRoutesPage());
			}
		});
		add(new Link<Void>("20-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditRoutesPage());
			}
		});

	}

	private class RoutesDataProvider extends SortableDataProvider<Route, Object> {

		public RoutesDataProvider() {
			super();
			setSort("id", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Route> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return rService.getAll(first, count, (String)sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return rService.getCount();
		}

		@Override
		public IModel<Route> model(Route object) {
			return new CompoundPropertyModel<>(object);
		}

	}

	
}
