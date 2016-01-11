package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationToStationBlockDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationToStationBlockService;

@Service
public class StationToStationBlockServiceImpl implements StationToStationBlockService {

	@Autowired
	private StationToStationBlockDao stsDao;
	
	public List<StationToStationBlock> getBlocks(){
		return stsDao.getBlocks();
	}
	
	@Override
	public void delete(StationToStationBlock acc) {
		stsDao.remove(acc);	
	}

	@Override
	public List<StationToStationBlock> getAll(long first, long count, String sortBy, String sortType) {
			if ("ASCENDING".equals(sortType)) {
				return stsDao.getAll(first, count, sortBy, "asc");
			} else {
				return stsDao.getAll(first, count, sortBy, "desc");
			} 
	}

	@Override
	public int getCount() {
		return stsDao.getCount();
	}

	@Override
	public void deleteBlock(int id) {
		stsDao.deleteBlock(id);
	}

	@Override
	public void add(StationToStationBlock block) {
		stsDao.add(block);
	}
	
}
