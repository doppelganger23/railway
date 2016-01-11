package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteMapDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.RouteMap;

@Repository
public class RouteMapDaoImpl implements RouteMapDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insert(RouteMap element) {
		jdbcTemplate.update(
				"INSERT INTO route_map (route_id, station_to_station_block_id, block_number_in_route, block_leave_time, block_enter_time) VALUES (?,?,?,?,?) ;",
				element.getRouteId(), element.getStationToStationBlockId(), element.getBlockNumberInRoute(),
				element.getBlockLeaveTime(), element.getBlockEnterTime());
	}

}
