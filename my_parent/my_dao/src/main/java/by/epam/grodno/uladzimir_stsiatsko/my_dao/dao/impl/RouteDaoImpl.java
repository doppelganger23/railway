package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.RouteMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;

@Repository
public class RouteDaoImpl implements RouteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Route> getAll(){
		return jdbcTemplate.query("SELECT * FROM route ORDER BY route_name ;", new RouteMapper());
	}

	@Override
	public void detete(Route route) {
		jdbcTemplate.update("DELETE FROM route WHERE id = ? ;", route.getId());
	}

	@Override
	public List<Route> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("select * from route order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new RouteMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from route ;", Integer.class);
	}
	
	//returns id after DB insertion
	@Override
	public int add(Route route) {
		final Route r = route;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO route (route_name, route_type, price_for_kilometer) VALUES (?,?,?)",
								new String[] { "id" });
				ps.setString(1, r.getRouteName());
				ps.setString(2, r.getRouteType());
				ps.setDouble(3, r.getPriceForKilometer());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	@Override
	public Route getById(int id){
		Object[] args = {id};
		return jdbcTemplate.queryForObject("SELECT * FROM route WHERE id = ? ;", args, new RouteMapper());
	}
	
}
