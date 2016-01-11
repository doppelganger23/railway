package by.epam.grodno.uladzimir_stsiatsko.my_service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BillDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Bill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class BillServiceTest {

	@Autowired
	private BillService service;

	private BillDao billDaoMock;

	private Bill bill = new Bill();

	@Before
	public void before() {
		bill.setId(1);
		billDaoMock = Mockito.mock(BillDao.class);
		
		when(billDaoMock.containsBill(1)).thenReturn(true);
		when(billDaoMock.containsBill(2)).thenReturn(false);
		when(billDaoMock.getPriceElements(bill)).thenReturn(Arrays.asList(1.1, 2.2, 3.3));
		when(billDaoMock.getBillingNumber("EUR")).thenReturn(1);
		when(billDaoMock.getBillingNumber("GBP")).thenReturn(0);

		ReflectionTestUtils.setField(service, "billDao", billDaoMock);
	}
	
	@Test
	public void addBillTest() {
		service.addBill(bill);
		verify(billDaoMock).insert(bill);
	}
	
	@Test
	public void containsBillTest() {
		assertTrue(service.containsBill(1));
		assertFalse(service.containsBill(2));
	}
	
	@Test
	public void countPriceTest() {
		assertTrue(service.countPrice(bill) == 6.6);
	}

	@Test
	public void getBillingNumberTest() {
		String exceptionText = "";
		try{
			service.getBillingNumber("GBP");
		} catch (IllegalArgumentException e){
			exceptionText = e.getMessage();
		}
		assertEquals("Supported currency types: USD, BYR", exceptionText);
		
		assertEquals(service.getBillingNumber("EUR"), 1);
	}
	
	@Test
	public void setPaidTest() {
		service.setPaid(1, true);
		verify(billDaoMock).setPaid(1, true);
	}
	
}
