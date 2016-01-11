package by.epam.grodno.uladzimir_stsiatsko.my_service;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

public interface BankDetailService {

	void addBankDetail(BankDetail bankDetail);
	
	void updateBankDetail(BankDetail bankDetail);
	
	void deleteBankDetail(String currencyOfPayment);
	
	List<String> findAllTypes();
	
	List<BankDetail> getAll(long first, long count, String sortBy, String sortType);
	
	int getCount();
	
	List<BankDetail> findAll();
	
	double getByrExchangeRate(String currencyType);
	
	int getBillingNumber(String currencyType);
	
}
