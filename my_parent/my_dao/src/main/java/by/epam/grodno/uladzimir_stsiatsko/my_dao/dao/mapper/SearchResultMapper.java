package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;

@Component
public class SearchResultMapper implements RowMapper<SearchResult>{

		@Override
		public SearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			SearchResult sr = new SearchResult();
			
			sr.setTripId(rs.getInt(1));//column number, used because of 2 columns with the same name is search view
			sr.setRouteName(rs.getString("route_name"));
			sr.setRouteType(rs.getString("route_type"));
			sr.setTrain(rs.getString("train"));
			sr.setFromStation(rs.getString("from_station"));
			sr.setFromBlock(rs.getInt("from_block"));
			sr.setDepartureDate(rs.getTimestamp("departure_date"));
			sr.setToStation(rs.getString("to_station"));
			sr.setToBlock(rs.getInt("to_block"));
			sr.setArrivalDate(rs.getTimestamp("arrival_date"));
			sr.setPlaces(rs.getInt("places"));
			sr.setSold(rs.getInt("sold"));
			sr.setKmPrice(rs.getDouble("km_price"));
			sr.setKm(rs.getDouble("km"));
			
			return sr;
		}
	
}
