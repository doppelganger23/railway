package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BillInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BillInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class BillInfoServiceTest {
	
	@Autowired
	private BillInfoService service;
	
	private BillInfoDao billInfoDaoMock;
	
	private BillInfo info1 = new BillInfo();
	private BillInfo info2 = new BillInfo();
	
	@Before
    public void before() {
		info1.setBillId(1);
		info2.setBillId(2);
		
		billInfoDaoMock = Mockito.mock(BillInfoDao.class);
		when(billInfoDaoMock.getCount()).thenReturn(42);
		when(billInfoDaoMock.getAll(1, 2, "id", "asc")).thenReturn(Arrays.asList(info1, info2));
		when(billInfoDaoMock.getAll(1, 2, "id", "desc")).thenReturn(Arrays.asList(info2, info1));
		
		ReflectionTestUtils.setField(service, "biDao", billInfoDaoMock);
	}
	
	@Test
	public void getCountTest() {
		int count = service.getCount();
		assertNotNull(count);
		assertEquals(42, count);
	}
	
	@Test
	public void getAllTest() {
		List<BillInfo> listAsc = service.getAll(1, 2, "id", "ASCENDING");
		List<BillInfo> listDesc = service.getAll(1, 2, "id", "DESCENDING");
		assertNotNull(listDesc);
		assertEquals(listAsc.get(1), info2);
		assertEquals(listDesc.get(0), info2);
	}

}
