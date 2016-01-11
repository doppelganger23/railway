package by.epam.grodno.uladzimir_stsiatsko.my_web.page;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import by.epam.grodno.uladzimir_stsiatsko.my_web.app.CustomSession;
import by.epam.grodno.uladzimir_stsiatsko.my_web.app.LangSelectionLink;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditAccountsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditBankDetailsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditBillsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditBlocksPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditRoutesPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditStationsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditTrainsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.admin_actions.EditTripListsPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.registration.RegistrationPage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.search.SearchPage;


/**
 * Parent of all project pages
 @author Uladzimir Stsiatsko
*/
public class AbstractPage extends WebPage {

	protected void onInitialize() {
		super.onInitialize();
		
		//localization
		add(new LangSelectionLink("ru"));
		add(new LangSelectionLink("en"));
		
		//main menu:
		
		add(new Link<Void>("home-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
		add(new Link<Void>("search-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new SearchPage());
			}
		});
		add(new Link<Void>("about-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new AboutPage());
			}
		});
		add(new Link<Void>("contact-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new ContactPage());
			}
		});
		
		//user side panel:
		
		Link<Void> regLink = new Link<Void>("registration-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationPage());
			}
		};
		add(regLink);
		Link<Void> logInLink = new Link<Void>("log-in-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new LogInPage());
			}
		};
		add(logInLink);
		Link<Void> logOutLink = new Link<Void>("log-out-link") {
			@Override
			public void onClick() {
				CustomSession.get().signOut();
				setResponsePage(new LogInPage());
			}
		};
		add(logOutLink);

		//admin side panel:
		
		WebMarkupContainer container = new WebMarkupContainer("container");
		add(container);
		
		//visibility settings for admin panel
		if (CustomSession.get().getRoles() == null) {
			logOutLink.setVisible(false);
			container.setVisible(false);
		} else {
			logInLink.setVisible(false);
			regLink.setVisible(false);
			if (!CustomSession.get().getRoles().hasRole("admin")) {
				container.setVisible(false);
			}
		}
		
		container.add(new Link<Void>("edit-accounts-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditAccountsPage());
			}
		});
		container.add(new Link<Void>("edit-trip-lists-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditTripListsPage());
			}
		});
		container.add(new Link<Void>("edit-trains-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditTrainsPage());
			}
		});
		container.add(new Link<Void>("edit-routes-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditRoutesPage());
			}
		});
		container.add(new Link<Void>("edit-bills-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditBillsPage());
			}
		});
		container.add(new Link<Void>("edit-bank-details-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditBankDetailsPage());
			}
		});
		container.add(new Link<Void>("edit-blocks-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditBlocksPage());
			}
		});
		container.add(new Link<Void>("edit-stations-page-link") {
			@Override
			public void onClick() {
				setResponsePage(new EditStationsPage());
			}
		});
		
	}
}
