package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListService;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteDao rDao;
	
	@Autowired
	private TripListService tlService;

	@Override
	public List<Route> findAll() {
		return rDao.getAll();
	}

	@Override
	public void delete(Route route) {
		if(tlService.containsRoute(route.getId())){
			throw new IllegalArgumentException("This route can't be deleted because of structural integrity needs");
		} else {
			rDao.detete(route);
		}
	}

	@Override
	public List<Route> getAll(long first, long count, String sortBy, String sortType) {
		if (sortType.equals("ASCENDING")) {
			return rDao.getAll(first, count, sortBy, "asc");
		} else {
			return rDao.getAll(first, count, sortBy, "desc");
		}
	}

	@Override
	public int getCount() {
		return rDao.getCount();
	}

	@Override
	public int add(Route route) {
		return rDao.add(route);
	}

	@Override
	public Route getById(int id) {
		return rDao.getById(id);
	}

}
