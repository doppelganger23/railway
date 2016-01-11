package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

public interface TrainService {	
	
	List<Train> findAll();
	
	void addTrain(Train train);
	
	void update(Train train);
	
	boolean containsTrain(String trainNumber);
	
	void deleteTrain(int id);
	
	List<Train> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();

}
