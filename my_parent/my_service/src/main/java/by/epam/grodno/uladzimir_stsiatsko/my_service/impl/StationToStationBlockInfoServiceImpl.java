package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationToStationBlockInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;
import by.epam.grodno.uladzimir_stsiatsko.my_service.StationToStationBlockInfoService;

@Service
public class StationToStationBlockInfoServiceImpl implements StationToStationBlockInfoService {

	@Autowired
	private StationToStationBlockInfoDao blockInfoDao;

	@Override
	public List<StationToStationBlockInfo> getAll(long first, long count, String sortBy, String sortType) {
		if ("ASCENDING".equals(sortType)) {
			return blockInfoDao.getAll(first, count, sortBy, "asc");
		} else {
			return blockInfoDao.getAll(first, count, sortBy, "desc");
		}
	}

	@Override
	public int getCount() {
		return blockInfoDao.getCount();
	}

}
