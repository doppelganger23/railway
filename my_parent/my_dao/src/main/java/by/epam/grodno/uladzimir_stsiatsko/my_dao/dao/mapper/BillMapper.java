package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;

public class BillMapper implements RowMapper<Bill> {

	@Override
	public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		int accountId = rs.getInt("account_id");
		int tripListId = rs.getInt("trip_list_id");
		double paymentValue = rs.getDouble("payment_value");
		boolean isPaid = rs.getBoolean("is_paid");
		int billingNumber = rs.getInt("billing_number");
		int fromBlock = rs.getInt("from_block");
		int toBlock = rs.getInt("to_block");
		Date creationDate = rs.getDate("creation_date");
		String currencyOfPayment = rs.getString("currency_of_payment");
		
		Bill bill = new Bill();
		
		bill.setId(id);
		bill.setAccountId(accountId);
		bill.setTripListId(tripListId);
		bill.setPaymentValue(paymentValue);
		bill.setPaid(isPaid);
		bill.setBillingNumber(billingNumber);
		bill.setFromBlock(fromBlock);
		bill.setToBlock(toBlock);
		bill.setCreationDate(creationDate);
		bill.setCurrencyOfPayment(currencyOfPayment);
		
		return bill;
	}
	
}
