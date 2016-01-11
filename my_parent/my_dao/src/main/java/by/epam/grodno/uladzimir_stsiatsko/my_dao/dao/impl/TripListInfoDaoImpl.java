package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.TripListInfoMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;

@Repository
public class TripListInfoDaoImpl implements TripListInfoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TripListInfo> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(
				String.format("SELECT tl.id, t.train_number, r.route_type, r.route_name, tl.departure_date, tl.tickets_sold "
						+ "FROM trip_list tl JOIN train t ON tl.train_id = t.id JOIN route r ON  tl.route_id = r.id "
						+ "order by %s %s limit %s offset %s ;", sortBy, sortType, count, first),
				new TripListInfoMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject(
				"SELECT count(1) FROM trip_list tl JOIN train t ON tl.train_id = t.id JOIN route r ON  tl.route_id = r.id ;", Integer.class);
	}

}
