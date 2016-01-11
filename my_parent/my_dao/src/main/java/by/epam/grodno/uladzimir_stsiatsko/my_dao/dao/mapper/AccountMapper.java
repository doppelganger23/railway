package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;

public final class AccountMapper implements RowMapper<Account> {
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		
		String login = rs.getString("login");
		String password = rs.getString("password");
		String lastName = rs.getString("last_name");
		String firstName = rs.getString("first_name");
		String email = rs.getString("email");
		String accessLevel = rs.getString("access_level");
		
		Account acc = new Account();
		
		acc.setId(id);
		acc.setLogin(login);
		acc.setPassword(password);
		acc.setFirstName(firstName);
		acc.setLastName(lastName);
		acc.setEmail(email);
		acc.setAccessLevel(accessLevel);
		
		return acc;
	}
}