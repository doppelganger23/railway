package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;

public interface AccountDao {

	void insert(Account acc);

	void update(Account acc);

	void remove(Account acc);

	Account getById(int id);

	Account getByLogin(String login);
	
	Account getByEmail(String email);

	List<Account> getAll(long first, long count, String sortBy, String sortType);

	int getCount();

}
