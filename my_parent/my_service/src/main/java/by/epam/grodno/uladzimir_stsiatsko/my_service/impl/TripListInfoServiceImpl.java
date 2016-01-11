package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListInfoService;

@Service
public class TripListInfoServiceImpl implements TripListInfoService {

	@Autowired
	private TripListInfoDao tliDao;

	@Override
	public List<TripListInfo> getAll(long first, long count, String sortBy, String sortType) {
			if ("ASCENDING".equals(sortType)) {
				return tliDao.getAll(first, count, sortBy, "asc");
			} else {
				return tliDao.getAll(first, count, sortBy, "desc");
			}
	}

	@Override
	public int getCount() {
		return tliDao.getCount();
	}

}
