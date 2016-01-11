package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;

public interface StationToStationBlockInfoService {

	List<StationToStationBlockInfo> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
}
