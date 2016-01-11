package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

public interface BankDetailDao {
	
	void addBankDetail(BankDetail bankDetail);
	
	void update(BankDetail bankDetail);
	
	void deleteBankDetail(String currencyOfPayment);
	
	List<String> getAllTypes();
	
	List<BankDetail> getAll();
	
	List<BankDetail> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	double getByrExchangeRate(String currencyType);
	
	int getBillingNumber(String currencyType);

}
