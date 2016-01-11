package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

public interface TripListDao {
	
	void insert(TripList tripList);
	
	List<TripList> findTrips(Request request);
	
	void deleteTripList(int id);
	
	boolean containsTrain(int trainId);
	
	boolean containsRoute(int routeId);
	
	void incrementTicketsSold(int tripListId);
	
}
