package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.kendo.ui.form.datetime.TimePicker;
import com.googlecode.wicket.kendo.ui.resource.KendoGlobalizeResourceReference;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.RouteMap;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteMapService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationToStationBlockService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditRoutesPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.renderer.StationToStationBlockChoiceRenderer;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class UpdateRouteMapPage extends AbstractPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EditRoutesPage.class);
	private StringBuilder builder = new StringBuilder("");
	
	private Route route;
	
	//current routeMap element to be added to list in index position
	private RouteMap rmElement = new RouteMap();
	private int elementIndex;
	private List<RouteMap> routeMap = new ArrayList<>();
	
	@Inject
	private RouteMapService mapService;
	@Inject
	private StationToStationBlockService blockService;
	@Inject
	private StationService stService;

	//fist visit constructor
	public UpdateRouteMapPage(Route route) {
		this.route = route;
		LOGGER.debug("received route " + route.getRouteName());
		rmElement.setRouteId(route.getId());
	}

	//recursive visits constructor
	public UpdateRouteMapPage(int nextElementIndex, List<RouteMap> routeMap, Route route, StringBuilder builder) {
		this(route);

		this.elementIndex = nextElementIndex;
		this.routeMap = routeMap;
		this.builder = builder;

	}
	
	//kendo locales for timepicker
	@Override
	public void renderHead(IHeaderResponse response){
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(new KendoGlobalizeResourceReference(Locale.FRANCE)));
	}

	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//update route map form components:

		Form<TripList> form = new Form<>("form");
		add(form);

		final Model<StationToStationBlock> blockModel = new Model<StationToStationBlock>();
		DropDownChoice<StationToStationBlock> blockChoice = new DropDownChoice<StationToStationBlock>(
				"block-choice", blockModel, blockService.getBlocks(), new StationToStationBlockChoiceRenderer());
		form.add(blockChoice);

		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 0, 0);

		final TimePicker blockEnterTimepicker = new TimePicker("block-enter-timepicker", Model.of(calendar.getTime()), Locale.FRENCH);
		form.add(blockEnterTimepicker);

		final TimePicker blockLeaveTimepicker = new TimePicker("block-leave-timepicker", Model.of(calendar.getTime()), Locale.FRENCH);
		form.add(blockLeaveTimepicker);

		form.add(new SubmitLink("next-iteration-button") {
			@SuppressWarnings("deprecation")
			@Override
			public void onSubmit() {
				//processing unsupported user behavior
				if (blockModel.getObject() == null) {
					error(getString("error.chooseBlock"));
				} else if (blockEnterTimepicker.getModelObject() == null) {
					error(getString("error.chooseBlockEnterTime"));
				} else if (blockLeaveTimepicker.getModelObject() == null) {
					error(getString("error.chooseBlockLeaveTime"));
				} else {				
					StationToStationBlock block = blockModel.getObject();
					//setting block properties:
					
					rmElement.setStationToStationBlockId(block.getId());
					rmElement.setBlockNumberInRoute(elementIndex + 1);

					//converting java.util.date from timepicker to java.sql.time
					//for block enter time
					int hour = blockEnterTimepicker.getModelObject().getHours();
					int minute = blockEnterTimepicker.getModelObject().getMinutes();
					LocalTime localTime = LocalTime.of(hour, minute);
					Time time = Time.valueOf(localTime);
					
					rmElement.setBlockEnterTime(time);

					//and for block leave time
					hour = blockLeaveTimepicker.getModelObject().getHours();
					minute = blockLeaveTimepicker.getModelObject().getMinutes();
					localTime = LocalTime.of(hour, minute);
					time = Time.valueOf(localTime);
					rmElement.setBlockLeaveTime(time);

					routeMap.add(elementIndex, rmElement);
					
					//updating current map status message
					builder.append(stService.getName(block.getDepartureStationId()) + " - ");
					builder.append(stService.getName(block.getDestinationStationId()) + ", ");
					
					setResponsePage(new UpdateRouteMapPage(++elementIndex, routeMap, route, builder));
				}
			}
		});

		//current map status message
		add(new Label("builder", builder));

		form.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
				mapService.addRouteMap(routeMap);
				setResponsePage(new EditRoutesPage());
			}
		});

	}

}
