package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationToStationBlockDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.StationToStationBlockMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;

@Repository
public class StationToStationBlockDaoImpl implements StationToStationBlockDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Override
	public List<StationToStationBlock> getBlocks(){
		return jdbcTemplate.query("SELECT * FROM station_to_station_block", new StationToStationBlockMapper());
	}
	
	@Override
	public void remove(StationToStationBlock block) {
		Object[] args = { block.getId() };
		jdbcTemplate.update("DELETE FROM station_to_station_block WHERE id = ? ;", args);
	}

	@Override
	public List<StationToStationBlock> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("select * from station_to_station_block order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new StationToStationBlockMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from station_to_station_block ;", Integer.class);
	}
	
	@Override
	public void deleteBlock(int id){
		jdbcTemplate.update(String.format("DELETE FROM station_to_station_block WHERE id = %s", id));
	}

	@Override
	public void add(StationToStationBlock block) {
		Object[] args = {block.getDepartureStationId(), block.getDestinationStationId(), block.getDistanceInKilometres()};
		jdbcTemplate.update("INSERT INTO station_to_station_block (departure_station_id, destination_station_id, distance_in_kilometres) VALUES (?,?,?)", args);
	}

}
