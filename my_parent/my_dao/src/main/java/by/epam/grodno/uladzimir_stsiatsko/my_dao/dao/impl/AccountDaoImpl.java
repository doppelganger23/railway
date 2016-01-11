package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.AccountDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.AccountMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;

@Repository
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Account getById(int id) {
		try {
			return jdbcTemplate.queryForObject("select * from account where id = ?", new Object[] { id },
					new AccountMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Account getByLogin(String login) {
		try {
			return jdbcTemplate.queryForObject("select * from account where login = ?", new Object[] { login },
					new AccountMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Account getByEmail(String email) {
		try {
			return jdbcTemplate.queryForObject("select * from account where email = ?", new Object[] { email },
					new AccountMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void insert(Account acc) {
		jdbcTemplate.update(
				"INSERT INTO account (login, password, last_name, first_name, email, access_level) VALUES (?,?,?,?,?,?) ;",
				acc.getLogin(), acc.getPassword(), acc.getLastName(), acc.getFirstName(), acc.getEmail(),
				acc.getAccessLevel());
	}

	@Override
	public void update(Account acc) {
		jdbcTemplate.update(
				"UPDATE account SET login=?, password=?, last_name=?, first_name=?, email=?, access_level=? WHERE id=?",
				acc.getLogin(), acc.getPassword(), acc.getLastName(), acc.getFirstName(), acc.getEmail(),
				acc.getAccessLevel(), acc.getId());
	}

	@Override
	public void remove(Account acc) {
		Object[] args = { acc.getId() };
		jdbcTemplate.update("DELETE FROM account WHERE id = ? ;", args);
	}

	@Override
	public List<Account> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("select * from account order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new AccountMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from account ;", Integer.class);
	}

}
