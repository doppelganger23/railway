package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.BankDetailDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.BankDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class BankDetailServiceTest {

	@Autowired
	private BankDetailService service;

	private BankDetailDao bdDaoMock;

	private BankDetail bankDetail = new BankDetail();

	@Before
	public void before() {
		bdDaoMock = Mockito.mock(BankDetailDao.class);

		when(bdDaoMock.getByrExchangeRate("USD")).thenReturn(1.0);
		when(bdDaoMock.getBillingNumber("USD")).thenReturn(1);
		when(bdDaoMock.getByrExchangeRate("NFD")).thenReturn(0.0);
		when(bdDaoMock.getBillingNumber("NFD")).thenReturn(0);

		ReflectionTestUtils.setField(service, "bdDao", bdDaoMock);
	}

	@Test
	public void addBankDetailTest() {
		service.addBankDetail(bankDetail);
		verify(bdDaoMock).addBankDetail(bankDetail);
	}

	@Test
	public void updateBankDetailTest() {
		service.updateBankDetail(bankDetail);
		verify(bdDaoMock).update(bankDetail);
	}

	@Test
	public void deleteBankDetailTest() {
		String text = "";
		try {
			service.deleteBankDetail("BYR");
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("BYR row from this table is integral part of the logic and cannot be deleted", text);

		service.deleteBankDetail("RUB");
		verify(bdDaoMock).deleteBankDetail("RUB");
	}

	@Test
	public void getAllTest() {
		service.getAll(1, 2, "id", "ASCENDING");
		verify(bdDaoMock).getAll(1, 2, "id", "asc");

		service.getAll(1, 2, "id", "DESCENDING");
		verify(bdDaoMock).getAll(1, 2, "id", "desc");
	}

	@Test
	public void getCountTest() {
		service.getCount();
		verify(bdDaoMock).getCount();
	}

	@Test
	public void findAllTest() {
		service.findAll();
		verify(bdDaoMock).getAll();
	}

	@Test
	public void findAllTypesTest() {
		service.findAllTypes();
		verify(bdDaoMock).getAllTypes();
	}

	@Test
	public void getByrExchangeRateTest() {
		assertTrue(1 == service.getByrExchangeRate("USD"));

		String text = "";
		try {
			service.getByrExchangeRate("NFD");
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("currency type not supported", text);
	}

	@Test
	public void getBillingNumberTest() {
		assertTrue(1 == service.getBillingNumber("USD"));

		String text = "";
		try {
			service.getBillingNumber("NFD");
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("currency type not supported", text);
	}

}
