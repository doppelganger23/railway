package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.StationToStationBlock;

public class StationToStationBlockMapper implements RowMapper<StationToStationBlock> {
	@Override
	public StationToStationBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		int departureStationId = rs.getInt("departure_station_id");
		int destinationStationId = rs.getInt("destination_station_id");
		double distanceInKilometres = rs.getDouble("distance_in_kilometres");
		
		StationToStationBlock block = new StationToStationBlock();
		
		block.setId(id);
		block.setDepartureStationId(departureStationId);
		block.setDestinationStationId(destinationStationId);
		block.setDistanceInKilometres(distanceInKilometres);
		
		return block;
	}
}