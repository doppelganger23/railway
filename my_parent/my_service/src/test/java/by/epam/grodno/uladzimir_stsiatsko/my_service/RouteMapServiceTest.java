package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.RouteMapDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.RouteMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class RouteMapServiceTest {

	@Autowired
	private RouteMapService service;
	
	private RouteMapDao rmDaoMock;

	private RouteMap rMap = new RouteMap();
	private List<RouteMap> list = new ArrayList<>();
	
	@Before
	public void before() {
		for(int i = 0; i < 7; i++){
			list.add(rMap);
		}
		
		rmDaoMock = Mockito.mock(RouteMapDao.class);

		ReflectionTestUtils.setField(service, "rmDao", rmDaoMock);
	}
	
	@Test
	public void addRouteMapTest() {
		service.addRouteMap(list);
		verify(rmDaoMock, times(7)).insert(rMap);
	}
	
}
