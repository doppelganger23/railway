package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;

public interface SearchResultDao {
	
	List<SearchResult> getResultsNoDateSpecified(Request request);

	List<SearchResult> getResultsArrivalBefore(Request request);

	List<SearchResult> getResultsArrivalAfter(Request request);
	
	List<SearchResult> getResultsDepartureBefore(Request request);
	
	List<SearchResult> getResultsDepartureAfter(Request request);

	List<SearchResult> getResultsBetweenDates(Request request);
	
	List<SearchResult> getResultsBeforeDates(Request request);
	
	List<SearchResult> getResultsAfterDates(Request request);
	
	List<SearchResult> getResultsNotBetweenDates(Request request);
	
	List<SearchResult> getAll(long first, long count);

	Integer getCount();	

}
