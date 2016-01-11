package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;

public interface RouteDao {
	
	int add(Route route);
	
	Route getById(int id);
	
	void detete(Route route);
	
	List<Route> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	List<Route> getAll();

}
