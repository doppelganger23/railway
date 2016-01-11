package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.AccountDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;
import by.epam.grodno.uladzimir_stsiatsko.my_service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountDao accDao;	

	@Override
	public void register(String login, String password, String firstName, String lastName, String email,
			String accessLevel) {
		Account acc = new Account();
		acc.setLogin(login);
		acc.setPassword(password);
		acc.setFirstName(firstName);
		acc.setLastName(lastName);
		acc.setEmail(email);
		acc.setAccessLevel(accessLevel);
		accDao.insert(acc);
	}
	
	@Override
	public Account getByLogin(String login){
		return accDao.getByLogin(login);
	}
	
	@Override
	public Account getByEmail(String email){
		return accDao.getByEmail(email);		
	}

	@Override
	public Integer authenticate(String login, String password) {
		Account acc = accDao.getByLogin(login);
		if (acc != null && acc.getPassword().equals(password)) {
			return acc.getId();
		}
		return null;
	}
	
	@Override
	public String getAccessLevel(int id){
		if (accDao.getById(id) == null){
			LOGGER.debug("returning null");
			return null;
		}
		LOGGER.debug("returning " + accDao.getById(id).getAccessLevel());
		return accDao.getById(id).getAccessLevel();
	}

	@Override
	public void register(Account acc) {
		accDao.insert(acc);
	}

	@Override
	public void delete(Account acc) {
		accDao.remove(acc);	
	}

	@Override
	public List<Account> getAll(long first, long count, String sortBy, String sortType) {
			if ("ASCENDING".equals(sortType)) {
				return accDao.getAll(first, count, sortBy, "asc");
			} else {
				return accDao.getAll(first, count, sortBy, "desc");
			} 
	}

	@Override
	public int getCount() {
		return accDao.getCount();
	}

	@Override
	public void update(Account acc) {
		accDao.update(acc);
	}

}
