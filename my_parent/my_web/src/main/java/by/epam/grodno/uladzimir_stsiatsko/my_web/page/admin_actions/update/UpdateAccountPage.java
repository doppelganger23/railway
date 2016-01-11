package by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.update;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.AccountService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditAccountsPage;

@AuthorizeAction(action=Action.RENDER, roles={"admin"})
public class UpdateAccountPage extends AbstractPage {
	
	private Account acc;
	
	@Inject
	private AccountService aService;
	
	public UpdateAccountPage(Account acc){
		this.acc = acc;
	}
	
	protected void onInitialize(){
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//update account form components:
		
		Form<TripList> form = new Form<>("update-form");
		add(form);
		
		TextField<String> loginField = new TextField<String>("new-login", new PropertyModel<String>(acc, "login"));
		loginField.setRequired(true);
		loginField.add(StringValidator.maximumLength(50));
		form.add(loginField);

		TextField<String> firstNameField = new TextField<String>("new-first-name",
				new PropertyModel<String>(acc, "firstName"));
		firstNameField.setRequired(true);
		firstNameField.add(StringValidator.maximumLength(50));
		form.add(firstNameField);

		TextField<String> lastNameField = new TextField<String>("new-last-name",
				new PropertyModel<String>(acc, "lastName"));
		lastNameField.setRequired(true);
		lastNameField.add(StringValidator.maximumLength(50));
		form.add(lastNameField);

		TextField<String> emailField = new TextField<String>("new-email", new PropertyModel<String>(acc, "email"));
		emailField.setRequired(true);
		emailField.add(StringValidator.maximumLength(50));
		form.add(emailField);
		
		form.add(new DropDownChoice<String>("new-access-level", new PropertyModel<String>(acc, "accessLevel"), Arrays.asList("passenger", "admin")));
		
		form.add(new SubmitLink("submit-update") {
			@Override
			public void onSubmit() {
				aService.update(acc);
				setResponsePage(new EditAccountsPage());
			}
		});		
		
	}

}
