package by.epam.grodno.uladzimir_stsiatsko.my_web.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;

public class StationChoiceRenderer extends ChoiceRenderer<Station>{

	@Override
	public Object getDisplayValue(Station station) {
		return station.getName();
	}

	@Override
	public String getIdValue(Station station, int index) {
		return String.valueOf(station.getId());
	}

}