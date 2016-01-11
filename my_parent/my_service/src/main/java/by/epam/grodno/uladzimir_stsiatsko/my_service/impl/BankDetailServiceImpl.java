package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BankDetailDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BankDetailService;

@Service
public class BankDetailServiceImpl implements BankDetailService {

	@Autowired
	private BankDetailDao bdDao;

	@Override
	public void addBankDetail(BankDetail bankDetail) {
		bdDao.addBankDetail(bankDetail);
	}

	@Override
	public void updateBankDetail(BankDetail bankDetail) {
		bdDao.update(bankDetail);
	}

	@Override
	public void deleteBankDetail(String currencyOfPayment) {
		if ("BYR".equals(currencyOfPayment)) {
			throw new IllegalArgumentException(
					"BYR row from this table is integral part of the logic and cannot be deleted");
		} else {
			bdDao.deleteBankDetail(currencyOfPayment);
		}
	}

	@Override
	public List<BankDetail> getAll(long first, long count, String sortBy, String sortType) {
		if ("ASCENDING".equals(sortType)) {
			return bdDao.getAll(first, count, sortBy, "asc");
		} else {
			return bdDao.getAll(first, count, sortBy, "desc");
		}
	}

	@Override
	public int getCount() {
		return bdDao.getCount();
	}

	@Override
	public List<BankDetail> findAll() {
		return bdDao.getAll();
	}

	@Override
	public List<String> findAllTypes() {
		return bdDao.getAllTypes();
	}

	@Override
	public double getByrExchangeRate(String currencyType) {
		double result = bdDao.getByrExchangeRate(currencyType);
		if (result == 0) {
			throw new IllegalArgumentException("currency type not supported");
		} else {
			return result;
		}
	}

	@Override
	public int getBillingNumber(String currencyType) {
		int result = bdDao.getBillingNumber(currencyType);
		if (result == 0) {
			throw new IllegalArgumentException("currency type not supported");
		} else {
			return result;
		}
	}

}
