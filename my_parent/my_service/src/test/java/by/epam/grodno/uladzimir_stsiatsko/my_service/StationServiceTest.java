package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class StationServiceTest {
	
	@Autowired
	private StationService service;

	private StationDao stationDaoMock;

	@Before
	public void before() {
		stationDaoMock = Mockito.mock(StationDao.class);
		
		ReflectionTestUtils.setField(service, "sDao", stationDaoMock);
	}
	
	@Test
	public void getStationNamesTest() {
		service.getStationNames();
		verify(stationDaoMock).getStationNames();
	}
	
	@Test
	public void getStationsTest() {
		service.getStations();
		verify(stationDaoMock).getStations();
	}
	
	@Test
	public void getNameTest() {
		service.getName(1);
		verify(stationDaoMock).getName(1);
	}

}
