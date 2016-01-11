package by.epam.grodno.uladzimir_stsiatsko.my_web.app;

import org.apache.wicket.extensions.yui.calendar.DatePicker;

public class CustomDatePicker extends DatePicker {

	public CustomDatePicker(){
		super();
		super.setShowOnFieldClick(true);
		super.setAutoHide(true);
	}	
	
	@Override
	protected String getAdditionalJavaScript() {
		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
	}

}
