package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.SearchResultDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.SearchResultMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;

@Repository
public class SearchResultDaoImpl implements SearchResultDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SearchResultMapper srMapper;
	
	private final String BASE_SCRIPT = ""
			+ "SELECT * FROM "
			+ "(SELECT trip_id, route_name, route_type, train, from_station, block from_block, (departure_date + enter_at) departure_date "
			+ "FROM search_view v1 WHERE v1.from_station = ?) s1 "
			+ "JOIN "
			+ "(SELECT to_station, block to_block, (departure_date + leave_at) arrival_date, places, sold, km_price, km, trip_id "
			+ "FROM search_view v2 WHERE v2.to_station = ?) s2 "
			+ "ON s1.trip_id = s2.trip_id "
			+ "WHERE from_block <= to_block "
			+ "AND sold <= places ";
	
	@Override
	public List<SearchResult> getResultsNoDateSpecified(Request request) {
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase()};
		LOGGER.debug("2 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT + "; ", args, srMapper);	
	}
	
	@Override
	public List<SearchResult> getResultsArrivalBefore(Request request){
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getArrivalDate()};
		LOGGER.debug("3 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT + "AND date_trunc('day', arrival_date) <= ? ;", args, srMapper);
	}
	
	@Override
	public List<SearchResult> getResultsArrivalAfter(Request request){
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getArrivalDate()};
		LOGGER.debug("3 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT + "AND date_trunc('day', arrival_date) >= ? ;", args, srMapper);
	}
	
	@Override
	public List<SearchResult> getResultsDepartureBefore(Request request){
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate()};
		LOGGER.debug("3 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT + "AND date_trunc('day', departure_date) <= ? ;", args, srMapper);
	}
	
	@Override
	public List<SearchResult> getResultsDepartureAfter(Request request){
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate()};
		LOGGER.debug("3 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT + "AND date_trunc('day', departure_date) >= ? ;", args, srMapper);
	}
		
	@Override
	public List<SearchResult> getResultsBetweenDates(Request request) {
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate(), request.getArrivalDate()};
		LOGGER.debug("4 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT 
				+ "AND date_trunc('day', departure_date) >= ? "
				+ "AND date_trunc('day', arrival_date) <= ? "
				+ "; ", args, srMapper);	
	}
	
	@Override
	public List<SearchResult> getResultsBeforeDates(Request request) {
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate(), request.getArrivalDate()};
		LOGGER.debug("4 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT
				+ "AND date_trunc('day', departure_date) <= ? "
				+ "AND date_trunc('day', arrival_date) <= ? "
				+ "; ", args, srMapper);	
	}
	
	@Override
	public List<SearchResult> getResultsAfterDates(Request request) {
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate(), request.getArrivalDate()};
		LOGGER.debug("4 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT
				+ "AND date_trunc('day', departure_date) >= ? "
				+ "AND date_trunc('day', arrival_date) >= ? "
				+ "; ", args, srMapper);	
	}
	
	@Override
	public List<SearchResult> getResultsNotBetweenDates(Request request) {
		Object[] args = {request.getDepartureStation().toUpperCase(), request.getDestinationStation().toUpperCase(), request.getDepartureDate(), request.getArrivalDate()};
		LOGGER.debug("4 request properties extracted. Sending request to database via Spring jdbcTemplate.");
		return jdbcTemplate.query(BASE_SCRIPT
				+ "AND date_trunc('day', departure_date) <= ? "
				+ "AND date_trunc('day', arrival_date) >= ? "
				+ "; ", args, srMapper);	
	}
	
	@Override
	public Integer getCount(){
		return jdbcTemplate.queryForObject("select count(1) from search_view ;",
				Integer.class);
	}
	
	@Override
	public List<SearchResult> getAll(long first, long count){
			return jdbcTemplate.query(String.format(
					"select * from search_view limit %s offset %s ;", count,
					first), new SearchResultMapper());		
	}

}
