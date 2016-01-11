package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Station;

public class StationMapper implements RowMapper<Station> {
	@Override
	public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		String name = rs.getString("name");
		Station st = new Station();
		st.setId(id);
		st.setName(name);
		
		return st;
	}
}
