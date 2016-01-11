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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class TripListServiceTest {

	@Autowired
	private TripListService service;

	private BillService billServiceMock;

	private TripListDao tlDaoMock;

	private TripList tList = new TripList();

	@Before
	public void before() {
		tList.setId(1);
		tlDaoMock = Mockito.mock(TripListDao.class);
		billServiceMock = Mockito.mock(BillService.class);

		when(billServiceMock.containsBill(1)).thenReturn(true);
		when(billServiceMock.containsBill(2)).thenReturn(false);

		ReflectionTestUtils.setField(service, "tlDao", tlDaoMock);
		ReflectionTestUtils.setField(service, "bService", billServiceMock);
	}

	@Test
	public void addTripListTest() {
		service.addTripList(tList);
		verify(tlDaoMock).insert(tList);
	}

	@Test
	public void deleteTripListTest() {
		String text = "";
		try {
			service.deleteTripList(1);
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("This trip list can't be deleted because of structural integrity needs", text);
		verify(tlDaoMock, never()).deleteTripList(1);
		
		service.deleteTripList(2);
		verify(tlDaoMock).deleteTripList(2);
	}

	@Test
	public void containsTrainTest() {
		service.containsTrain(1);
		verify(tlDaoMock).containsTrain(1);
	}

	@Test
	public void containsRouteTest() {
		service.containsRoute(1);
		verify(tlDaoMock).containsRoute(1);
	}

	@Test
	public void incrementTicketsSoldTest() {
		service.incrementTicketsSold(1);
		verify(tlDaoMock).incrementTicketsSold(1);
	}

}
