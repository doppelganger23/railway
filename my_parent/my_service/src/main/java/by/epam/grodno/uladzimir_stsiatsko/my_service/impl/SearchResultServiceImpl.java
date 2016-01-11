package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.SearchResultDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;
import by.epam.grodno.uladzimir_stsiatsko.my_service.SearchResultService;

/**
 * Search request parsing service.
 * At first the 'find' method is ran,
 * then it call one of the other methods
 * depending on what user have submitted.
 @author Uladzimir Stsiatsko
*/
@Service
public class SearchResultServiceImpl implements SearchResultService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultServiceImpl.class);

	@Autowired
	private SearchResultDao srDao;

	// argument field analysis for date presence
	public List<SearchResult> find(Request request) {
		if (request.getDepartureDate() == null) {
			if (request.getArrivalDate() == null) {
				LOGGER.debug("No dates specified.");
				return findWithoutDates(request);
			}
			LOGGER.debug("No departure date specified.");
			return findWithoutDepartureDate(request);
		}
		if (request.getArrivalDate() == null) {
			LOGGER.debug("No arrival date specified.");
			return findWithoutArrivalDate(request);
		}
		LOGGER.debug("Both dates specified.");
		return findWithBothDates(request);
	}

	public List<SearchResult> findWithoutDates(Request request) {
		LOGGER.debug("Calling SearchResultDao getRusultsNoDateSpecified method.");
		return srDao.getResultsNoDateSpecified(request);
	}

	public List<SearchResult> findWithoutDepartureDate(Request request) {
		// analysis for date compare condition
		if ("<=".equals(request.getArrCondition())) {
			LOGGER.debug("Calling SearchResultDao getRusultsArrivalBefore method.");
			return srDao.getResultsArrivalBefore(request);
		} else {
			LOGGER.debug("Calling SearchResultDao getRusultsArrivalAfter method.");
			return srDao.getResultsArrivalAfter(request);
		}
	}

	public List<SearchResult> findWithoutArrivalDate(Request request) {
		// analysis for date compare condition
		if (">=".equals(request.getDepCondition())) {
			LOGGER.debug("Calling SearchResultDao getRusultsDepartureAfter method.");
			return srDao.getResultsDepartureAfter(request);
		} else {
			LOGGER.debug("Calling SearchResultDao getRusultsDepartureBefore method.");
			return srDao.getResultsDepartureBefore(request);
		}
	}

	public List<SearchResult> findWithBothDates(Request request) {
		// analysis for date compare condition
		if (">=".equals(request.getDepCondition())) {
			if ("<=".equals(request.getArrCondition())) {
				LOGGER.debug("Calling SearchResultDao getRusultsBetweenDates method.");
				return srDao.getResultsBetweenDates(request);
			}
			LOGGER.debug("Calling SearchResultDao getRusultsAfterDates method.");
			return srDao.getResultsAfterDates(request);
		}
		if ("<=".equals(request.getArrCondition())) {
			LOGGER.debug("Calling SearchResultDao getRusultsBeforeDates method.");
			return srDao.getResultsBeforeDates(request);
		}
		LOGGER.debug("Calling SearchResultDao getRusultsNotBetweenDates method.");
		return srDao.getResultsNotBetweenDates(request);
	}

	//will be used in future
	public List<SearchResult> getAll(long first, long count){
		return srDao.getAll(first, count);
	}
	
	//will be used in future
	public Integer getCount() {
		return srDao.getCount();
	}
}
