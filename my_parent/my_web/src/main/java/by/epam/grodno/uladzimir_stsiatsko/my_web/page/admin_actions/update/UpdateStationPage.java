package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditStationsPage;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class UpdateStationPage extends AbstractPage {

	private Station station;

	@Inject
	private StationService stService;

	public UpdateStationPage(Station station) {
		this.station = station;
	}

	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//update station form components:

		Form<TripList> form = new Form<>("update-form");
		add(form);

		TextField<String> trainNameField = new TextField<String>("new-station-name",
				new PropertyModel<String>(station, "name"));
		trainNameField.setRequired(true);
		trainNameField.add(StringValidator.maximumLength(50));
		form.add(trainNameField);

		form.add(new SubmitLink("submit-update") {
			@Override
			public void onSubmit() {
				station.setName(station.getName().toUpperCase());
				//station name must be unique
				if (stService.stationExists(station.getName())) {
					error(getString("error.stationExists"));
				} else {
					stService.update(station);
					setResponsePage(new EditStationsPage());
				}
			}
		});

	}

}