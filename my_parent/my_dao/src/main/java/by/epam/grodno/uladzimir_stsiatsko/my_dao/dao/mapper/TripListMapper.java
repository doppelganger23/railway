package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

@Component
public class TripListMapper implements RowMapper<TripList> {
	@Override
	public TripList mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TripList list = new TripList();
		
		list.setId(rs.getInt("id"));
		list.setTrainId(rs.getInt("train_id"));
		list.setRouteId(rs.getInt("route_id"));
		list.setDepartureDate(rs.getTimestamp("departure_date"));
		list.setTicketsSold(rs.getInt("tickets_sold"));
		
		return list;
	}
}
