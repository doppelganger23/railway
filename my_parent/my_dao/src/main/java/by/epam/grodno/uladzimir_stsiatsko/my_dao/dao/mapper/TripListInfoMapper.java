package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;

public class TripListInfoMapper implements RowMapper<TripListInfo> {
	@Override
	public TripListInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TripListInfo list = new TripListInfo();
		
		list.setId(rs.getInt("id"));
		list.setTrainNumber(rs.getString("train_number"));
		list.setRouteType(rs.getString("route_type"));
		list.setRouteName(rs.getString("route_name"));
		list.setDepartureDate(rs.getTimestamp("departure_date"));
		list.setTicketsSold(rs.getInt("tickets_sold"));
		
		return list;
	}
}
