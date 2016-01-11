package by.epam.grodno.uladzimir_stsiatsko.my_service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

public interface TripListService {
	
	void addTripList(TripList tripList);
	
	void deleteTripList(int id);
	
	boolean containsTrain(int trainId);
	
	boolean containsRoute(int routeId);
	
	void incrementTicketsSold(int tripListId);

}
