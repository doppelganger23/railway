package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;

@Component
public final class RequestMapper implements RowMapper<Request> {
	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		Request request = new Request();
		
		request.setId(rs.getInt("id"));
		request.setDepartureStation(rs.getString("departure_station"));
		request.setDestinationStation(rs.getString("destination_station"));
		request.setDepartureDate(rs.getTimestamp("departure_date"));
		request.setArrivalDate(rs.getTimestamp("arrival_date"));
		
		return request;
	}
}