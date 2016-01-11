package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;

public interface StationService {

	List<String> getStationNames();
	
	List<Station> getStations();
	
	String getName(int stationId);
	
	void delete(Station station);
	
	List<Station> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	boolean stationExists(String name);
	
	void addStation(Station station);
	
	void update(Station station);
}
