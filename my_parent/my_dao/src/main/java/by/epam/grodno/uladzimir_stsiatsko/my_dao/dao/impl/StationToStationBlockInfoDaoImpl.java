package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationToStationBlockInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.StationToStationBlockInfoMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;

@Repository
public class StationToStationBlockInfoDaoImpl implements StationToStationBlockInfoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<StationToStationBlockInfo> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(
				String.format("SELECT sts.id, sts.distance_in_kilometres, s1.name departure_station_name, s2.name destination_station_name "
						+ "FROM station_to_station_block sts JOIN station s1 ON sts.departure_station_id = s1.id JOIN station s2 ON sts.destination_station_id = s2.id "
						+ "order by %s %s limit %s offset %s ;", sortBy, sortType, count, first),
				new StationToStationBlockInfoMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject(
				"SELECT count(1) FROM station_to_station_block ;", Integer.class);
	}

}
