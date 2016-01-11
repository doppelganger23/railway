package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteMapDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.RouteMap;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RouteMapService;

@Service
public class RouteMapServiceImpl implements RouteMapService {
	
	@Autowired
	private RouteMapDao rmDao;
	
	public void addRouteMap(List<RouteMap> routeMap){
		for(RouteMap element : routeMap){
			rmDao.insert(element);
		}
	}

}
