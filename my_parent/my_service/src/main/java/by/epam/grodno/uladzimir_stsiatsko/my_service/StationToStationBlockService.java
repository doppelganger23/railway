package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;

public interface StationToStationBlockService {
	
	List<StationToStationBlock> getBlocks();
	
	void delete(StationToStationBlock block);
	
	List<StationToStationBlock> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	void deleteBlock(int id);
	
	void add(StationToStationBlock block);

}
