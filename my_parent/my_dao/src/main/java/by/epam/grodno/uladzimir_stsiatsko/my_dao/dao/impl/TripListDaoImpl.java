package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.TripListMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

@Repository
public class TripListDaoImpl implements TripListDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TripListMapper tlMapper;
	
	@Override
	public void insert(TripList tripList) {
		Object[] args = {tripList.getTrainId(), tripList.getRouteId(), tripList.getDepartureDate(), tripList.getTicketsSold()};
		jdbcTemplate.update("INSERT INTO trip_list (train_id, route_id, departure_date, tickets_sold) VALUES (?,?,?,?)", args);

	}
	
	@Override
	public List<TripList> findTrips(Request request) {
		return jdbcTemplate.query("SELECT * FROM trip_list", tlMapper);
	}
	
	@Override
	public void deleteTripList(int id){
		jdbcTemplate.update(String.format("DELETE FROM trip_list WHERE id = %s", id));
	}
	
	@Override
	public boolean containsTrain(int trainId){
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM trip_list WHERE train_id = ? ;", Integer.class, trainId);
		if(count.intValue() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean containsRoute(int routeId){
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM trip_list WHERE route_id = ? ;", Integer.class, routeId);
		if(count.intValue() > 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void incrementTicketsSold(int tripListId) {
		jdbcTemplate.update("UPDATE trip_list SET tickets_sold = tickets_sold + 1 WHERE id = ? ;", tripListId);
	}
}
