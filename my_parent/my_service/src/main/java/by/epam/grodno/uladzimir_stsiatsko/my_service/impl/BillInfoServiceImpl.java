package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BillInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BillInfo;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillInfoService;

@Service
public class BillInfoServiceImpl implements BillInfoService {

	@Autowired
	private BillInfoDao biDao;

	public List<BillInfo> getAll(long first, long count, String sortBy, String sortType) {
		if ("ASCENDING".equals(sortType)) {
			return biDao.getAll(first, count, sortBy, "asc");
		} else {
			return biDao.getAll(first, count, sortBy, "desc");
		}
	}

	public int getCount() {
		return biDao.getCount();
	}

}
