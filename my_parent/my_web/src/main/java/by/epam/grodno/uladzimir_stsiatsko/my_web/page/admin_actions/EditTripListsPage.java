package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.kendo.ui.form.datetime.TimePicker;
import com.googlecode.wicket.kendo.ui.resource.KendoGlobalizeResourceReference;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TrainService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListInfoService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomDatePicker;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.renderer.RouteChoiceRenderer;
import by.epam.grodno.uladzimir_stsiatsko.my_web.renderer.TrainChoiceRenderer;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class EditTripListsPage extends AbstractPage {
	
	private Locale pickerLocale = Locale.ENGLISH;

	@Inject
	private BillService bService;

	@Inject
	private TripListInfoService tlInfoService;

	@Inject
	private TripListService tlService;

	@Inject
	private TrainService trainService;

	@Inject
	private RouteService routeService;
	
	//metadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;

	public EditTripListsPage() {
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if (meta != null) {
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}
	
	//kendo locales for timepicker
	@Override
	public void renderHead(IHeaderResponse response){
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(new KendoGlobalizeResourceReference(Locale.FRANCE)));
		response.render(JavaScriptHeaderItem.forReference(new KendoGlobalizeResourceReference(Locale.ENGLISH)));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//new trip list form components:
		
		final TripList newTripList = new TripList();
		Form<TripList> form = new Form<>("add-trip-list-form");
		add(form);

		final Model<Train> trainModel = new Model<Train>();
		DropDownChoice<Train> trainChoice = new DropDownChoice<Train>("train-choice", trainModel,
				trainService.findAll(), new TrainChoiceRenderer());
		trainChoice.setRequired(true);
		form.add(trainChoice);

		final Model<Route> routeModel = new Model<Route>();
		DropDownChoice<Route> routeChoice = new DropDownChoice<Route>("route-choice", routeModel,
				routeService.findAll(), new RouteChoiceRenderer());
		routeChoice.setRequired(true);
		form.add(routeChoice);

		// date part, without time
		final DateTextField dateField = new DateTextField("departure-date",
				new PropertyModel<Date>(newTripList, "departureDate"), new StyleDateConverter("S-", true));
		dateField.add(new CustomDatePicker());
		dateField.setRequired(true);
		form.add(dateField);

		//timepicker components:
		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 0, 0);
		//french locale is used instead of russian, wich is not supported by default
		if("ru".equals(Session.get().getLocale().toString())){
			pickerLocale = Locale.FRENCH;
		}

		final TimePicker departureTimepicker = new TimePicker("departure-time-picker", Model.of(calendar.getTime()), pickerLocale);
		form.add(departureTimepicker);

		form.add(new SubmitLink("submit-button") {
			@SuppressWarnings("deprecation")
			@Override
			public void onSubmit() {
				if (departureTimepicker.getModelObject() == null) {
					error(getString("error.chooseDepTime"));
				} else {
					//uniting date and time values from different pickers
					int hours = departureTimepicker.getModelObject().getHours();
					int minutes = departureTimepicker.getModelObject().getMinutes();
					Date date = dateField.getModelObject();
					date.setHours(hours);
					date.setMinutes(minutes);
					newTripList.setDepartureDate(date);

					newTripList.setTrainId(trainModel.getObject().getId());
					newTripList.setRouteId(routeModel.getObject().getId());
					
					tlService.addTripList(newTripList);
					setResponsePage(new EditTripListsPage());
				}
			}
		});

		//"trip lists info-s" list:
		
		TripListInfoDataProvider tliDataProvider = new TripListInfoDataProvider();
		DataView<TripListInfo> dataView = new DataView<TripListInfo>("trip-list-info-list", tliDataProvider,
				elementsOnPage) {
			@Override
			protected void populateItem(Item<TripListInfo> item) {
				final TripListInfo tlInfo = item.getModelObject();
				item.add(new Label("id"));
				item.add(new Label("trainNumber"));
				item.add(new Label("routeType"));
				item.add(new Label("routeName"));
				item.add(new Label("departureDate"));
				item.add(new Label("ticketsSold"));

				Link<Void> deleteLink = new Link<Void>("delete-trip-list-link") {
					@Override
					public void onClick() {
						tlService.deleteTripList(tlInfo.getId());
					}
				};
				item.add(deleteLink);
				//visibility check for denying invalid operations
				//(preventing database constraint violations errors)
				if (bService.containsBill(tlInfo.getId())) {
					deleteLink.setVisible(false);
				}
			}
		};
		add(dataView);

		//paging:
		
		add(new OrderByBorder<Object>("sortId", "id", tliDataProvider));
		add(new OrderByBorder<Object>("sortTrainNumber", "train_number", tliDataProvider));
		add(new OrderByBorder<Object>("sortRouteType", "route_type", tliDataProvider));
		add(new OrderByBorder<Object>("sortRouteName", "route_name", tliDataProvider));
		add(new OrderByBorder<Object>("sortDepartureDate", "departure_date", tliDataProvider));
		add(new OrderByBorder<Object>("sortTicketsSold", "tickets_sold", tliDataProvider));

		add(new PagingNavigator("paging", dataView));

		add(new Link<Void>("5-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditTripListsPage());
			}
		});
		add(new Link<Void>("10-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditTripListsPage());
			}
		});
		add(new Link<Void>("20-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditTripListsPage());
			}
		});

	}

	private class TripListInfoDataProvider extends SortableDataProvider<TripListInfo, Object> {

		public TripListInfoDataProvider() {
			super();
			setSort("id", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends TripListInfo> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return tlInfoService.getAll(first, count, (String) sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return tlInfoService.getCount();
		}

		@Override
		public IModel<TripListInfo> model(TripListInfo object) {
			return new CompoundPropertyModel<>(object);
		}

	}

}
