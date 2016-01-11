package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RequestDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	private RequestDao rDao;
	
	@Autowired
	private TripListDao tlDao;
	
	@Override
	public void find(Request request){
		rDao.setQuiery(request);
	}
	
	@Override
	public List<TripList> executeQuiery(Request request){
		return tlDao.findTrips(request);
	}
	
	@Override
	public Request getRequest(int id){
		 return rDao.getById(id);
	}
}
