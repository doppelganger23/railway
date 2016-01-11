package by.epam.grodno.uladzimir_stsiatsko.my_web.page.search;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.kendo.ui.form.button.Button;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_service.SearchResultService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomDatePicker;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;

public class SearchPage extends AbstractPage {
	
	@Inject
	private StationService stService;
	
	//choices for autocomplete field
	private final List<String> CHOICES = stService.getStationNames();

	@Inject
	private SearchResultService srService;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//request form components:
		
		final Request newRequest = new Request();
		Form<Request> form = new Form<>("request-form", new CompoundPropertyModel<>(newRequest));
		add(form);

		AutoCompleteTextField<String> departureStationField = new AutoCompleteTextField<String>("departureStation"){
			@Override
			protected Iterator<String> getChoices(String input) {
				return ListUtils.startsWith(input, CHOICES).iterator();
			}
		};
		departureStationField.setRequired(true);
		departureStationField.add(StringValidator.maximumLength(50));
		form.add(departureStationField);
		
		AutoCompleteTextField<String> destinationStationField = new AutoCompleteTextField<String>("destinationStation"){
			@Override
			protected Iterator<String> getChoices(String input) {
				return ListUtils.startsWith(input, CHOICES).iterator();
			}
		};
		destinationStationField.setRequired(true);
		destinationStationField.add(StringValidator.maximumLength(50));
		form.add(destinationStationField);
		
		form.add(new DateTextField("departureDate", new StyleDateConverter("S-", true)).add(new CustomDatePicker()));
		form.add(new DateTextField("arrivalDate", new StyleDateConverter("S-", true)).add(new CustomDatePicker()));
		form.add(new DropDownChoice<String>("depCondition", Arrays.asList(">=", "<=")));
		form.add(new DropDownChoice<String>("arrCondition", Arrays.asList("<=", ">=")));

		form.add(new Button("submit-button") {
			@Override
			public void onSubmit() {				
				setResponsePage(new SearchResultPage(srService.find(newRequest)));
			}
		});

	}

}
