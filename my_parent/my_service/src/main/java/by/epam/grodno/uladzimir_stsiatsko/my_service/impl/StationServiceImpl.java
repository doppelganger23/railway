package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationService;

@Service
public class StationServiceImpl implements StationService {
	
	@Autowired
	private StationDao sDao;
	
	public List<Station> getStations(){
		return sDao.getStations();
	}
	
	public List<String> getStationNames(){
		return sDao.getStationNames();
	}

	public String getName(int stationId){
		return sDao.getName(stationId);
	}
	
	@Override
	public void delete(Station station) {
		sDao.remove(station);	
	}

	@Override
	public List<Station> getAll(long first, long count, String sortBy, String sortType) {
			if ("ASCENDING".equals(sortType)) {
				return sDao.getAll(first, count, sortBy, "asc");
			} else {
				return sDao.getAll(first, count, sortBy, "desc");
			} 
	}

	@Override
	public int getCount() {
		return sDao.getCount();
	}

	@Override
	public boolean stationExists(String name) {
		return sDao.stationExists(name);
	}

	@Override
	public void addStation(Station station) {
		sDao.addStation(station);
	}

	@Override
	public void update(Station station) {
		sDao.update(station);
	}
}
