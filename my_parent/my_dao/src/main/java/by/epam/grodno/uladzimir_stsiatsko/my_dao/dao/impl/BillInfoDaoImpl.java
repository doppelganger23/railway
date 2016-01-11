package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BillInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper.BillInfoMapper;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BillInfo;

@Repository
public class BillInfoDaoImpl implements BillInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<BillInfo> getAll() {
		return jdbcTemplate.query(
				"SELECT b.id bill_id, b.payment_value, b.currency_of_payment, b.billing_number, b.creation_date, b.is_paid, "
				+ "a.first_name, a.last_name, a.email "
				+ "FROM bill b JOIN account a ON b.account_id = a.id ;",
				new BillInfoMapper());
	}

	@Override
	public List<BillInfo> getAll(long first, long count, String sortBy, String sortType) {
		return jdbcTemplate.query(String.format("SELECT b.id bill_id, b.payment_value, b.currency_of_payment, b.billing_number, b.creation_date, b.is_paid, "
				+ "a.first_name, a.last_name, a.email "
				+ "FROM bill b JOIN account a ON b.account_id = a.id order by %s %s limit %s offset %s ;", sortBy,
				sortType, count, first), new BillInfoMapper());
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("SELECT count(1) FROM bill b JOIN account a ON b.account_id = a.id ;", Integer.class);
	}

}
