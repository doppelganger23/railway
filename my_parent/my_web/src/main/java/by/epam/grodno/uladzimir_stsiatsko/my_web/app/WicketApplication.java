package by.epam.grodno.uladzimir_stsiatsko.my_web.app;

import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import by.epam.grodno.uladzimir_stsiatsko.my_web.page.HomePage;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.LogInPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.epam.training.webapp.StartJetty#main(String[])
 */
@Component("MyWebApplication")
public class WicketApplication extends AuthenticatedWebApplication {

	@Autowired
	private ApplicationContext context;

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		ComponentInstantiationListenerCollection componentInstantiationListeners = getComponentInstantiationListeners();
		componentInstantiationListeners.add(new SpringComponentInjector(this, context));
		
		getMarkupSettings().setStripWicketTags(true);
		// add your configuration here
	}
	
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LogInPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return CustomSession.class;
	}
	
}
