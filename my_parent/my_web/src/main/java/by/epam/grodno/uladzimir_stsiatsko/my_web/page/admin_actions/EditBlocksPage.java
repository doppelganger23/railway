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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationToStationBlockInfoService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationToStationBlockService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.metadata.ElementsOnPageMetaData;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.renderer.StationChoiceRenderer;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class EditBlocksPage extends AbstractPage {

	@Inject
	private StationService stService;

	@Inject
	private StationToStationBlockService blockService;

	@Inject
	private StationToStationBlockInfoService blockInfoService;

	// metadata for paging
	public static MetaDataKey<ElementsOnPageMetaData> ELEMENTS_ON_PAGE = new MetaDataKey<ElementsOnPageMetaData>() {
	};
	private int elementsOnPage = 5;

	public EditBlocksPage() {
		ElementsOnPageMetaData meta = getSession().getMetaData(ELEMENTS_ON_PAGE);
		if (meta != null) {
			this.elementsOnPage = meta.getElementsOnPage();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new FeedbackPanel("feedback"));

		// update route map form components:

		final StationToStationBlock newBlock = new StationToStationBlock();
		Form<TripList> form = new Form<>("form");
		add(form);

		final Model<Station> depStationModel = new Model<Station>();
		DropDownChoice<Station> depStationChoice = new DropDownChoice<Station>("departure-station-choice",
				depStationModel, stService.getStations(), new StationChoiceRenderer());
		form.add(depStationChoice);

		final Model<Station> destStationModel = new Model<Station>();
		DropDownChoice<Station> destStationChoice = new DropDownChoice<Station>("destination-station-choice",
				destStationModel, stService.getStations(), new StationChoiceRenderer());
		form.add(destStationChoice);

		NumberTextField<Double> distanceField = new NumberTextField<Double>("distance",
				new PropertyModel<Double>(newBlock, "distanceInKilometres"));
		distanceField.setRequired(true);
		form.add(distanceField);

		form.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
				//validation
				if (depStationModel.getObject() == null) {
					error(getString("error.chooseDepStation"));
				} else if (destStationModel.getObject() == null) {
					error(getString("error.chooseDestStation"));
				} else if (newBlock.getDistanceInKilometres() == 0) {
					error(getString("error.chooseDistance"));
				} else {
					newBlock.setDepartureStationId(depStationModel.getObject().getId());
					newBlock.setDestinationStationId(destStationModel.getObject().getId());

					blockService.add(newBlock);
					setResponsePage(new EditBlocksPage());
				}
			}
		});

		// list of blocks info:

		BlocksInfoDataProvider blockInfoDataProvider = new BlocksInfoDataProvider();
		DataView<StationToStationBlockInfo> dataView = new DataView<StationToStationBlockInfo>("blocks-info-list",
				blockInfoDataProvider, elementsOnPage) {
			@Override
			protected void populateItem(Item<StationToStationBlockInfo> item) {
				final StationToStationBlockInfo blockInfo = item.getModelObject();
				item.add(new Label("id"));
				item.add(new Label("departureStationName"));
				item.add(new Label("destinationStationName"));
				item.add(new Label("distanceInKilometres"));

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						blockService.deleteBlock(blockInfo.getId());
					}
				};
				item.add(deleteLink);
			}
		};
		add(dataView);

		// paging:

		add(new OrderByBorder<Object>("sortId", "id", blockInfoDataProvider));
		add(new OrderByBorder<Object>("sortDepartureStationName", "departure_station_name", blockInfoDataProvider));
		add(new OrderByBorder<Object>("sortDestinationStationName", "destination_station_name", blockInfoDataProvider));
		add(new OrderByBorder<Object>("sortDistanceInKilometres", "distance_in_kilometres", blockInfoDataProvider));

		add(new PagingNavigator("paging", dataView));

		add(new Link<Void>("5-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(5));
				setResponsePage(new EditBlocksPage());
			}
		});
		add(new Link<Void>("10-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(10));
				setResponsePage(new EditBlocksPage());
			}
		});
		add(new Link<Void>("20-elements-link") {
			@Override
			public void onClick() {
				getSession().setMetaData(ELEMENTS_ON_PAGE, new ElementsOnPageMetaData(20));
				setResponsePage(new EditBlocksPage());
			}
		});

	}

	private class BlocksInfoDataProvider extends SortableDataProvider<StationToStationBlockInfo, Object> {

		public BlocksInfoDataProvider() {
			super();
			setSort("id", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends StationToStationBlockInfo> iterator(long first, long count) {

			SortParam<Object> sort = getSort();
			ISortState<Object> sortState = getSortState();
			SortOrder currentSort = sortState.getPropertySortOrder(sort.getProperty());

			return blockInfoService.getAll(first, count, (String) sort.getProperty(), currentSort.name()).iterator();
		}

		@Override
		public long size() {
			return blockInfoService.getCount();
		}

		@Override
		public IModel<StationToStationBlockInfo> model(StationToStationBlockInfo object) {
			return new CompoundPropertyModel<>(object);
		}

	}

}