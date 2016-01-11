package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TrainService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditTrainsPage;

@AuthorizeAction(action = Action.RENDER, roles = { "admin" })
public class UpdateTrainPage extends AbstractPage {

	private Train train;

	@Inject
	private TrainService tService;

	public UpdateTrainPage(Train train) {
		this.train = train;
	}

	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//update train form components:

		Form<TripList> form = new Form<>("update-form");
		add(form);

		TextField<String> trainNumberField = new TextField<String>("new-train-number",
				new PropertyModel<String>(train, "trainNumber"));
		trainNumberField.setRequired(true);
		trainNumberField.add(StringValidator.maximumLength(10));
		form.add(trainNumberField);

		NumberTextField<Integer> passCapField = new NumberTextField<Integer>("new-passengers-capacity",
				new PropertyModel<Integer>(train, "passengersCapacity"));
		passCapField.setRequired(true);
		form.add(passCapField);

		form.add(new SubmitLink("submit-update") {
			@Override
			public void onSubmit() {
				train.setTrainNumber(train.getTrainNumber().toUpperCase());
				if (tService.containsTrain(train.getTrainNumber())) {
					error(getString("error.trainNumberTaken"));
				} else {
					tService.update(train);
					setResponsePage(new EditTrainsPage());
				}
			}
		});

	}

}
