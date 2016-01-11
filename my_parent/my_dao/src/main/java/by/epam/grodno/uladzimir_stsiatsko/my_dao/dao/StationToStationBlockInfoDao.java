package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;

public interface StationToStationBlockInfoDao {
	
	List<StationToStationBlockInfo> getAll(long first, long count, String sortBy, String sortType);

	int getCount();	

}
