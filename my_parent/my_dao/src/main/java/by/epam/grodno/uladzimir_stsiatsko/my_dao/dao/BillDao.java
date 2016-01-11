package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;

public interface BillDao {

	void insert(Bill bill);
	
	//counts price for each block through which passenger will travel
	List<Double> getPriceElements(Bill bill);
	
	//supported types are USD and BYR, but you can add more
	int getBillingNumber(String currencyType);
	
	boolean containsBill(int tripListId);
	
	void setPaid(int id, boolean isPaid);
	
}
