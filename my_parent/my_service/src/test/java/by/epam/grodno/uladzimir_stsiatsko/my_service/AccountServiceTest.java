package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.*;
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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.AccountDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class AccountServiceTest {
	
	@Autowired
	private AccountService service;

	private AccountDao accDaoMock;

	private Account acc = new Account();

	@Before
	public void before() {
		acc.setId(1);
		acc.setPassword("password");
		acc.setAccessLevel("admin");
		accDaoMock = Mockito.mock(AccountDao.class);
		
		when(accDaoMock.getByLogin("login")).thenReturn(acc);
		when(accDaoMock.getById(1)).thenReturn(acc);
		when(accDaoMock.getCount()).thenReturn(42);

		ReflectionTestUtils.setField(service, "accDao", accDaoMock);
	}
	
	@Test
	public void registerTest() {
		service.register(acc);
		verify(accDaoMock).insert(acc);
	}
	
	@Test
	public void getByLoginTest() {
		service.getByLogin("login");
		verify(accDaoMock).getByLogin("login");
	}
	
	@Test
	public void getByEmailTest() {
		service.getByEmail("email");
		verify(accDaoMock).getByEmail("email");
	}
	
	@Test
	public void authenticateTest(){
		Integer id = service.authenticate("login", "password");
		verify(accDaoMock).getByLogin("login");
		assertEquals(1, id.intValue());
		
		assertNull(service.authenticate("l", "password"));
		assertNull(service.authenticate("login", "p"));
	}
	
	@Test
	public void getAccessLevelTest(){
		assertNull(service.getAccessLevel(2));
		assertEquals("admin", service.getAccessLevel(1));
	}
	
	@Test
	public void deleteTest(){
		service.delete(acc);
		verify(accDaoMock).remove(acc);
	}
	
	@Test
	public void getAllTest(){
		service.getAll(1, 2, "id", "ASCENDING");
		verify(accDaoMock).getAll(1, 2, "id", "asc");
		service.getAll(1, 2, "id", "DESCENDING");
		verify(accDaoMock).getAll(1, 2, "id", "desc");
	}
	
	@Test
	public void getCountTest(){
		int i = service.getCount();
		verify(accDaoMock).getCount();
		assertEquals(42, i);
	}
	
	@Test
	public void updateTest(){
		service.update(acc);
		verify(accDaoMock).update(acc);
	}
		
}
