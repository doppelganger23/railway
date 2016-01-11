package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TrainDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.TrainMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

@Repository
public class TrainDaoImpl implements TrainDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Train> getAll() {
		return jdbcTemplate.query("SELECT * FROM train ORDER BY train_number ;", new TrainMapper());
	}

	@Override
	public void addTrain(Train train) {
			jdbcTemplate.update("INSERT INTO train (train_number, passengers_capacity) VALUES (?, ?) ;",
					train.getTrainNumber(), train.getPassengersCapacity());
	}

	@Override
	public void update(Train train) {
			jdbcTemplate.update("UPDATE train SET train_number = ?, passengers_capacity = ? WHERE id = ?",
					train.getTrainNumber(), train.getPassengersCapacity(), train.getId());
	}

	@Override
	public void deleteTrain(int id) {
		jdbcTemplate.update("DELETE FROM train WHERE id = ? ;", id);
	}

	@Override
	public List<Train> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("SELECT * FROM train ORDER BY %s %s LIMIT %s OFFSET %s ;", sortBy,
				sortType, count, first), new TrainMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM train ;", Integer.class);
	}
	
	@Override
	public boolean containsTrain(String trainNumber) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM train WHERE train_number = ? ;",
				Integer.class, trainNumber);
		if (count.intValue() > 0) {
			return true;
		}
		return false;
	}
}
