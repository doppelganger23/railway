package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BillInfo;

public class BillInfoMapper implements RowMapper<BillInfo> {
	
	@Override
	public BillInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BillInfo bi = new BillInfo();
		
		bi.setBillId(rs.getInt("bill_id"));
		bi.setPaymentValue(rs.getDouble("payment_value"));
		bi.setCurrencyOfPayment(rs.getString("currency_of_payment"));
		bi.setBillingNumber(rs.getInt("billing_number"));
		bi.setCreationDate(rs.getDate("creation_date"));
		bi.setPaid(rs.getBoolean("is_paid"));
		bi.setFirstName(rs.getString("first_name"));
		bi.setLastName(rs.getString("last_name"));
		bi.setEmail(rs.getString("email"));
		
		return bi;
	}

}
