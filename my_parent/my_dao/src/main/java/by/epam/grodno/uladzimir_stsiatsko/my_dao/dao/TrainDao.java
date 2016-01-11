package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

public interface TrainDao {
	
	List<Train> getAll();
	
	void addTrain(Train train);
	
	void update(Train train);
	
	boolean containsTrain(String trainNumber);
	
	void deleteTrain(int id);
	
	public List<Train> getAll(long first, long count, String sortBy, String sortType);

	public int getCount();

}
