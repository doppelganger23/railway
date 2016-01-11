package by.epam.grodno.uladzimir_stsiatsko.my_web.renderer;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;

public class StationToStationBlockChoiceRenderer extends ChoiceRenderer<StationToStationBlock>{
	
	public StationToStationBlockChoiceRenderer(){
		Injector.get().inject(this);
	}
	
	@Inject	
	private StationService stService;
	
	@Override
	public Object getDisplayValue(StationToStationBlock block) {
		String from = stService.getName(block.getDepartureStationId());
		String to = stService.getName(block.getDestinationStationId());
		return String.format("%s -> %s", from, to);
	}

	@Override
	public String getIdValue(StationToStationBlock block, int index) {
		return String.valueOf(block.getId());
	}

}
