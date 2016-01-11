package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;

public interface StationDao {

	List<String> getStationNames();
	
	List<Station> getStations();
	
	String getName(int stationId);
	
	void remove(Station station);
	
	List<Station> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	boolean stationExists(String stationName);
	
	void addStation(Station station);
	
	void update(Station station);
	
}
