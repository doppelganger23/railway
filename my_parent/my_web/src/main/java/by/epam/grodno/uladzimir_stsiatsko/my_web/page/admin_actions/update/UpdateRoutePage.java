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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteService;
import by.epam.grodno.uladzimir_stsiatsko.my_web.page.AbstractPage;

@AuthorizeAction(action=Action.RENDER, roles={"admin"})
public class UpdateRoutePage extends AbstractPage {
	
	private Route route;
	
	@Inject
	private RouteService rService;
	
	public UpdateRoutePage(Route route){
		this.route = route;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new FeedbackPanel("feedback"));
		
		//update route form components:
		
		Form<Route> routeUpdateForm = new Form<>("route-update-form");
		add(routeUpdateForm);
		
		TextField<String> routeNameField = new TextField<String>("route-name",
				new PropertyModel<String>(route, "routeName"));
		routeNameField.setRequired(true);
		routeNameField.add(StringValidator.maximumLength(150));
		routeUpdateForm.add(routeNameField);
		
		TextField<String> routeTypeField = new TextField<String>("route-type",
				new PropertyModel<String>(route, "routeType"));
		routeTypeField.setRequired(true);
		routeTypeField.add(StringValidator.maximumLength(10));
		routeUpdateForm.add(routeTypeField);
		
		NumberTextField<Double> priceForKmField = new NumberTextField<Double>("price-for-kilometer",
				new PropertyModel<Double>(route, "priceForKilometer"));
		priceForKmField.setRequired(true);
		routeUpdateForm.add(priceForKmField);
		
		routeUpdateForm.add(new SubmitLink("submit-button") {
			@Override
			public void onSubmit() {
					route.setRouteName(route.getRouteName().toUpperCase());
					route.setRouteType(route.getRouteType().toUpperCase());
					//getting route id from database to complete model object
					int newRouteId = rService.add(route);
					route.setId(newRouteId);
					
					setResponsePage(new UpdateRouteMapPage(route));
			}
		});
		
	}

}
