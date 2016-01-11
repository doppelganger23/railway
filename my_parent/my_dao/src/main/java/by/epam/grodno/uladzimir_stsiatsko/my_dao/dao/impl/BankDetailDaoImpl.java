package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BankDetailDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.BankDetailMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void addBankDetail(BankDetail bankDetail){
		jdbcTemplate.update(
				"INSERT INTO bank_detail (currency_of_payment, billing_number, byr_exchange_rate) VALUES (?, ?, ?) ;",
				bankDetail.getCurrencyOfPayment(), bankDetail.getBillingNumber(), bankDetail.getByrExchangeRate());
	}
	
	@Override
	public void update(BankDetail bankDetail) {
		jdbcTemplate.update(
				"UPDATE bank_detail SET billing_number=?, byr_exchange_rate=? WHERE currency_of_payment=? ;",
				bankDetail.getBillingNumber(), bankDetail.getByrExchangeRate(), bankDetail.getCurrencyOfPayment());
	}
	
	@Override
	public void deleteBankDetail(String currencyOfPayment){
		jdbcTemplate.update("DELETE FROM bank_detail WHERE currency_of_payment = ? ;", currencyOfPayment);
	}
	
	@Override
	public List<String> getAllTypes(){
		return jdbcTemplate.query("SELECT currency_of_payment FROM bank_detail;", new SingleColumnRowMapper<String>());
	}
	
	@Override
	public List<BankDetail> getAll(long first, long count, String sortBy, String sortType){
		return jdbcTemplate.query(String.format("SELECT * FROM bank_detail order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new BankDetailMapper());
	}
	
	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from bank_detail ;", Integer.class);
	}
	
	@Override
	public List<BankDetail> getAll(){
		return jdbcTemplate.query("SELECT * FROM bank_detail;", new BankDetailMapper());
	}

	@Override
	public double getByrExchangeRate(String currencyType) {
		Object[] args = { currencyType };
		try {
			return jdbcTemplate.queryForObject(
					"SELECT byr_exchange_rate FROM bank_detail WHERE currency_of_payment = ? ;", Double.class, args);
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	@Override
	public int getBillingNumber(String currencyType) {
		try {
			Object[] args = { currencyType };
			Integer billingNumber = jdbcTemplate.queryForObject(
					"SELECT billing_number FROM bank_detail WHERE currency_of_payment = ? ;", Integer.class, args);
			return billingNumber;
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
	}

}
