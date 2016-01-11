package by.epam.grodno.uladzimir_stsiatsko.my_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BillDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;
import by.epam.grodno.uladzimir_stsiatsko.my_service.BillService;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillDao billDao;
	
	@Override
	public boolean containsBill(int tripListId){
		return billDao.containsBill(tripListId);
	}
	
	@Override
	public void addBill(Bill bill){
		billDao.insert(bill);
	}
	
	@Override
	public double countPrice (Bill bill){
		List<Double> priceParts = billDao.getPriceElements(bill);
		Double price = 0.0;
		for (Double part : priceParts){
			price+=part;
		}
		return price;
	}
	
	@Override
	public int getBillingNumber(String currencyType){
		int result = billDao.getBillingNumber(currencyType);
		if(result == 0){
			throw new IllegalArgumentException("Supported currency types: USD, BYR");
		}
		return result;
	}
	
	@Override
	public void setPaid(int id, boolean isPaid){
		billDao.setPaid(id, isPaid);
	}
}
