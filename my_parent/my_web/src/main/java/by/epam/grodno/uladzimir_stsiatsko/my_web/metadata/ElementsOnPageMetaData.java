package by.epam.grodno.uladzimir_stsiatsko.my_web.metadata;

import java.io.Serializable;

public class ElementsOnPageMetaData implements Serializable {
	
	private int elementsOnPage;
	
	public ElementsOnPageMetaData(int elementsOnPage){
		this.setElementsOnPage(elementsOnPage);
	}

	public int getElementsOnPage() {
		return elementsOnPage;
	}

	public void setElementsOnPage(int elementsOnPage) {
		this.elementsOnPage = elementsOnPage;
	}

}
