package by.epam.grodno.uladzimir_stsiatsko.my_web.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomSession;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.registration.RegistrationPage;

public class LogInPage extends AbstractPage {

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new FeedbackPanel("feedback"));
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
				boolean isSuccess = CustomSession.get().signIn(loginModel.getObject(),
						passwordModel.getObject());

				if (isSuccess) {
					setResponsePage(new HomePage());
				} else {
					error("login error");
					setResponsePage(getPage());
				}
			}
		});
		
		add(new Link<Void>("register"){
			@Override
			public void onClick(){
				setResponsePage(new RegistrationPage());
			}
			
		});
	}
}
