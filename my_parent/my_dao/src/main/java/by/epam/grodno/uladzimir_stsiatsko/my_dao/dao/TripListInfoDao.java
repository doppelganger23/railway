package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;

public interface TripListInfoDao {
	
	List<TripListInfo> getAll(long first, long count, String sortBy, String sortType);

	int getCount();	

}
