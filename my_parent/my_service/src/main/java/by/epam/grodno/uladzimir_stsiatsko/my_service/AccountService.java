package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;

public interface AccountService {
	
	Integer authenticate(String login, String password);
	
	String getAccessLevel(int id);
	
	void register(String login, String password, String firstName, String lastName, String email, String accessLevel);
	
	void register(Account acc);
	
	void delete (Account acc);
	
	void update (Account acc);
	
	Account getByLogin(String login);
	
	Account getByEmail(String email);
	
	List<Account> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
}
