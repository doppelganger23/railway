package by.epam.grodno.uladzimir_stsiatsko.my_web.app;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.Link;

public class LangSelectionLink extends Link<Void> {
	
	public LangSelectionLink(String id) {
		super(id);
	}

	@Override
	public void onClick() {
		Session.get().setLocale(new Locale(getId()));
	}
}