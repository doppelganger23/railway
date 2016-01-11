package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlockInfo;

public class StationToStationBlockInfoMapper implements RowMapper<StationToStationBlockInfo> {
	@Override
	public StationToStationBlockInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StationToStationBlockInfo list = new StationToStationBlockInfo();
		
		list.setId(rs.getInt("id"));
		list.setDepartureStationName(rs.getString("departure_station_name"));
		list.setDestinationStationName(rs.getString("destination_station_name"));
		list.setDistanceInKilometres(rs.getDouble("distance_in_kilometres"));
		
		return list;
	}
}