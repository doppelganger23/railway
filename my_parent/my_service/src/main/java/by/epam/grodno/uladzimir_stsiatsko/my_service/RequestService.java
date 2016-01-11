package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

public interface RequestService {

	void find(Request request);
	
	List<TripList> executeQuiery(Request request);
	
	Request getRequest(int id);
	
}
