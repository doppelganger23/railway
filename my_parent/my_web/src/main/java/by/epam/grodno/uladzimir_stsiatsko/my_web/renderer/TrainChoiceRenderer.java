package by.epam.grodno.uladzimir_stsiatsko.my_web.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

public class TrainChoiceRenderer extends ChoiceRenderer<Train>{

	@Override
	public Object getDisplayValue(Train train) {
		return train.getTrainNumber();
		
	}

	@Override
	public String getIdValue(Train train, int index) {
		return String.valueOf(train.getId());
	}
}
