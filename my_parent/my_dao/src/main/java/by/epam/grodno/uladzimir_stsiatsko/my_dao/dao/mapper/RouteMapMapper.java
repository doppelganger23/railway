package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.RouteMap;

public class RouteMapMapper implements RowMapper<RouteMap>{
	
	@Override
	public RouteMap mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		RouteMap rm = new RouteMap();
		
		rm.setId(rs.getInt("id"));
		rm.setRouteId(rs.getInt("route_id"));
		rm.setStationToStationBlockId(rs.getInt("station_to_station_block_id"));
		rm.setBlockNumberInRoute(rs.getInt("block_number_in_route"));
		rm.setBlockLeaveTime(rs.getTime("block_leave_time"));
		rm.setBlockEnterTime(rs.getTime("block_enter_time"));
		
		return rm;
	}

}

