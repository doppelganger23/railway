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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update.UpdateStationPage;

@AuthorizeAction(action=Action.RENDER, roles={"admin"})
public class EditStationsPage extends AbstractPage {
	
	@Inject
	private StationService stService;
	
	//metadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;
	
	public EditStationsPage(){
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if(meta != null){
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//new station form components:

		final Station newStation = new Station();
		Form<Station> newStationForm = new Form<>("new-station-form");
		add(newStationForm);

		TextField<String> stationNameField = new TextField<String>("station-name",
				new PropertyModel<String>(newStation, "name"));
		stationNameField.setRequired(true);
		stationNameField.add(StringValidator.maximumLength(50));
		newStationForm.add(stationNameField);

		newStationForm.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
				newStation.setName(newStation.getName().toUpperCase());
				//station name must be unique
				if (stService.stationExists(newStation.getName())) {
					error(getString("error.stationExists"));
				} else {
					stService.addStation(newStation);
					setResponsePage(new EditStationsPage());
				}
			}
		});

		//list of stations:
		
		StationsDataProvider stationDataProvider = new StationsDataProvider();
		DataView<Station> dataView = new DataView<Station>("stations-list", stationDataProvider, elementsOnPage) {
			@Override
			protected void populateItem(Item<Station> item) {
				final Station station = item.getModelObject();
				item.add(new Label("id"));
				item.add(new Label("name"));

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						stService.delete(station);
					}
				};
				item.add(deleteLink);
				
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new UpdateStationPage(station));
					}
				});

			}
		};
		add(dataView);
		
		//paging:

		add(new OrderByBorder<Object>("sortId", "id", stationDataProvider));
		add(new OrderByBorder<Object>("sortName", "name", stationDataProvider));
		
		add(new PagingNavigator("paging", dataView));
		
		add(new Link<Void>("5-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditStationsPage());
			}
		});
		add(new Link<Void>("10-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditStationsPage());
			}
		});
		add(new Link<Void>("20-elements-link"){
			@Override
			public void onClick(){
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditStationsPage());
			}
		});

	}

	private class StationsDataProvider extends SortableDataProvider<Station, Object> {

		public StationsDataProvider() {
			super();
			setSort("id", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Station> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return stService.getAll(first, count, (String)sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return stService.getCount();
		}

		@Override
		public IModel<Station> model(Station object) {
			return new CompoundPropertyModel<>(object);
		}

	}

}