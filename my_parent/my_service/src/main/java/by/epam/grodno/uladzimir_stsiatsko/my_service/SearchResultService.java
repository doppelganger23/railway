package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.SearchResult;


public interface SearchResultService {

	List<SearchResult> find(Request request);

	List<SearchResult> getAll(long first, long count);
	
	Integer getCount();
	
}
