package by.epam.grodno.uladzimir_stsiatsko.my_web.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;

public class RouteChoiceRenderer extends ChoiceRenderer<Route>{

		@Override
		public Object getDisplayValue(Route route) {
			return String.format("%s (%s), id = %s", route.getRouteName(), route.getRouteType(), route.getId());
			
		}

		@Override
		public String getIdValue(Route route, int index) {
			return String.valueOf(route.getId());
		}
	
}
