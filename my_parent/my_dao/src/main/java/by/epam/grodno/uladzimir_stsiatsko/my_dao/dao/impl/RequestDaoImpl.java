package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RequestDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.RequestMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;

@Repository
public class RequestDaoImpl implements RequestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//currently not used (for now requests isn't stored in database)
	@Override
	public void setQuiery(Request request) {
		jdbcTemplate.update(
				"INSERT INTO request (departure_station, destination_station, departure_date, arrival_date) VALUES (?,?,?,?)",
				request.getDepartureStation(), request.getDestinationStation(), request.getDepartureDate(),
				request.getArrivalDate());
	}

	//currently not used (for now requests isn't stored in database)
	@Override
	public Request getById(int id) {
		return jdbcTemplate.queryForObject("select * from request where id = ?", new Object[] { id },
				new RequestMapper());
	}

}
