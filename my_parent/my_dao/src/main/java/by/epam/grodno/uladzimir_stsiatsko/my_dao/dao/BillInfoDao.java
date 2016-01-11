package by.epam.grodno.uladzimir_stsiatsko.my_dao.dao;

import java.util.List;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BillInfo;

public interface BillInfoDao {
	
	List<BillInfo> getAll();
	
	//TODO
	//List<BillInfo> getUnpaid();
	
	List<BillInfo> getAll(long first, long count, String sortBy, String sortType);

	int getCount();	

}
