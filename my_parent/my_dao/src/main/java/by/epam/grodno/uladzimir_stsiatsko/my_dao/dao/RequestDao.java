package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;

public interface RequestDao {
	
	void setQuiery(Request request);
	
	public Request getById(int id);

}
