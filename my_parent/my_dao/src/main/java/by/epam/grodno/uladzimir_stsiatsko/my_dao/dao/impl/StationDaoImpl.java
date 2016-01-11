package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.StationMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;

@Repository
public class StationDaoImpl implements StationDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<String> getStationNames(){
		return jdbcTemplate.query("SELECT name FROM station;", new SingleColumnRowMapper<String>());
	}

	@Override
	public List<Station> getStations(){
		return jdbcTemplate.query("SELECT * FROM station;", new StationMapper());
	}
	
	@Override
	public String getName(int stationId){
		Object[] args = {stationId};
		return jdbcTemplate.queryForObject("SELECT name FROM station WHERE id = ? ;", String.class, args);
	}
	
	@Override
	public void remove(Station station) {
		Object[] args = { station.getId() };
		jdbcTemplate.update("DELETE FROM station WHERE id = ? ;", args);
	}

	@Override
	public List<Station> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("select * from station order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new StationMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from station ;", Integer.class);
	}
	
	@Override
	public boolean stationExists(String stationName) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM station WHERE name = ? ;",
				Integer.class, stationName);
		if (count.intValue() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public void addStation(Station station) {
			jdbcTemplate.update("INSERT INTO station (name) VALUES (?) ;",
					station.getName());
	}
	
	@Override
	public void update(Station station) {
			jdbcTemplate.update("UPDATE station SET name = ? WHERE id = ? ;",
					station.getName(), station.getId());
	}
	
}
