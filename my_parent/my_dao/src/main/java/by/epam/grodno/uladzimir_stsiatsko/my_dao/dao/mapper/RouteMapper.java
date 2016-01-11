package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;

public class RouteMapper implements RowMapper<Route>{
	
	@Override
	public Route mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		Route r = new Route();
		
		r.setId(rs.getInt("id"));
		r.setRouteName(rs.getString("route_name"));
		r.setRouteType(rs.getString("route_type"));
		r.setPriceForKilometer(rs.getDouble("price_for_kilometer"));
		
		return r;
	}

}
