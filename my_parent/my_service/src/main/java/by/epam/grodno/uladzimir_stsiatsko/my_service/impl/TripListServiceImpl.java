package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillService;
import by.epam.grodno.uladzimir_stsiatsko.my_service.TripListService;

@Service
public class TripListServiceImpl implements TripListService {
		
	@Autowired
	private TripListDao tlDao;
	
	@Autowired
	private BillService bService;
	
	@Override
	public void addTripList(TripList tripList){
		tlDao.insert(tripList);
	}

	public void deleteTripList(int id) throws IllegalArgumentException {
		if(bService.containsBill(id)){
			throw new IllegalArgumentException("This trip list can't be deleted because of structural integrity needs");
		} else {
			tlDao.deleteTripList(id);
		}
	}
	
	public boolean containsTrain(int trainId){
		return tlDao.containsTrain(trainId);
	}
	
	public boolean containsRoute(int routeId){
		return tlDao.containsRoute(routeId);
	}

	@Override
	public void incrementTicketsSold(int tripListId) {
		tlDao.incrementTicketsSold(tripListId);
	}
	
}
