package by.epam.grodno.uladzimir_stsiatsko.my_web.page;

import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**Project description page
 @author Uladzimir Stsiatsko
*/
public class AboutPage extends AbstractPage {
	
	public AboutPage() {
		add(new KendoUIBehavior("#accordion", "kendoPanelBar"));
	}

	protected void OnInitialize() {
		super.onInitialize();
	}

}
