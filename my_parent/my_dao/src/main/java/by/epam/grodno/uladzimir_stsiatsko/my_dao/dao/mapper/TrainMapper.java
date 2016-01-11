package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

public class TrainMapper implements RowMapper<Train>{
	
	@Override
	public Train mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		Train tr = new Train();
		
		tr.setId(rs.getInt("id"));
		tr.setTrainNumber(rs.getString("train_number"));
		tr.setPassengersCapacity(rs.getInt("passengers_capacity"));
		
		return tr;
	}

}
