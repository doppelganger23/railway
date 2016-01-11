package by.epam.grodno.uladzimir_stsiatsko.my_web.page.ticket;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;
import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomSession;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.registration.RegistrationPage;


/**
 * Same as LogInPage, but redirects to BankDetailPage after login
 * (used in process of selling tickets to unlogged user)
 @author Uladzimir Stsiatsko
*/
public class NameInputPage extends AbstractPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NameInputPage.class);
	
	private Bill bill;

	public NameInputPage(Bill bill) {
		this.bill = bill;
	}

	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));

		//name input page form components:
		
		Form<Void> form = new Form<Void>("login-form");

		final Model<String> loginModel = new Model<>(null);
		TextField<String> loginField = new TextField<String>("login", loginModel);
		form.add(loginField);
		
		final Model<String> passwordModel = new Model<>(null);
		TextField<String> passwordField = new PasswordTextField("password", passwordModel);
		form.add(passwordField);
		
		add(form);

		form.add(new SubmitLink("submit") {
			@Override
			public void onSubmit() {
				//checking if user logged in
				boolean isSuccess = CustomSession.get().signIn(loginModel.getObject(), passwordModel.getObject());

				if (isSuccess) {
					bill.setAccountId(CustomSession.get().getCurrentuserid());
					LOGGER.debug("bill account_id is set to " + CustomSession.get().getCurrentuserid());
					setResponsePage(new BankDetailsPage(bill));
				} else {
					error("login error");
					setResponsePage(getPage());
				}
			}
		});

		add(new Link<Void>("register") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationPage());
			}
		});
	}

}
