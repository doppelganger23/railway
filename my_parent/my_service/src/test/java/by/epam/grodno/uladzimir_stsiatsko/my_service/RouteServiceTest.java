package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class RouteServiceTest {
	
	@Autowired
	private RouteService service;
	
	private TripListService tlServiceMock;
	private RouteDao routeDaoMock;

	private Route route1 = new Route();
	private Route route2 = new Route();

	@Before
	public void before() {
		route1.setId(1);
		route2.setId(2);
		routeDaoMock = Mockito.mock(RouteDao.class);
		tlServiceMock = Mockito.mock(TripListService.class);
		
		when(tlServiceMock.containsRoute(1)).thenReturn(true);
		when(tlServiceMock.containsRoute(2)).thenReturn(false);
		
		ReflectionTestUtils.setField(service, "rDao", routeDaoMock);
		ReflectionTestUtils.setField(service, "tlService", tlServiceMock);
	}
	
	@Test
	public void findAllTest() {
		service.findAll();
		verify(routeDaoMock).getAll();
	}
	
	@Test
	public void deleteTest() {
		String text = "";
		try{
			service.delete(route1);
		} catch (IllegalArgumentException e){
			text = e.getMessage();
		}
		assertEquals("This route can't be deleted because of structural integrity needs", text);
		verify(routeDaoMock, never()).detete(route1);
		
		service.delete(route2);
		verify(routeDaoMock).detete(route2);
	}
	
	@Test
	public void getAllTest() {
		service.getAll(1, 2, "id", "ASCENDING");
		verify(routeDaoMock).getAll(1, 2, "id", "asc");
		
		service.getAll(1, 2, "id", "DESCENDING");
		verify(routeDaoMock).getAll(1, 2, "id", "desc");
	}
	
	@Test
	public void getCountTest() {
		service.getCount();
		verify(routeDaoMock).getCount();
	}
	
	@Test
	public void addTest() {
		service.add(route1);
		verify(routeDaoMock).add(route1);
	}
	
	@Test
	public void getByIdTest() {
		service.getById(1);
		verify(routeDaoMock).getById(1);
	}

}
