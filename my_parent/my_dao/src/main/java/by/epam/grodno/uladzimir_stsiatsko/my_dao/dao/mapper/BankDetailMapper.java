package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

public class BankDetailMapper implements RowMapper<BankDetail> {
	@Override
	public BankDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String currencyOfPayment = rs.getString("currency_of_payment");
		int billingNumber = rs.getInt("billing_number");
		double byrExchangeRate = rs.getDouble("byr_exchange_rate");
		
		BankDetail bd = new BankDetail();
		
		bd.setCurrencyOfPayment(currencyOfPayment);
		bd.setBillingNumber(billingNumber);
		bd.setByrExchangeRate(byrExchangeRate);
		
		return bd;
	}
}
