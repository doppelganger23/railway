package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.SearchResultDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class SearchResultServiceMockTest {

	@Autowired
	private SearchResultService service;

	private SearchResultDao searchResultDaoMock;

	// Twelve Little Niggers:
	// (numbers from 1 to 9 is the order of supposed DAO methods invocation in
	// SRService)
	private Request r0 = new Request();// empty
	private Request r1 = new Request();// both stations, no dates
	private Request r2 = new Request();// both stations, arrival date (<=)
	private Request r3 = new Request();// both stations, arrival date (>=)
	private Request r4 = new Request();// both stations, departure date (>=)
	private Request r5 = new Request();// both stations, departure date (<=)
	private Request r6 = new Request();// both stations,both dates,D(>=),A(<=)
	private Request r7 = new Request();// both stations,both dates,D(>=),A(>=)
	private Request r8 = new Request();// both stations,both dates,D(<=),A(<=)
	private Request r9 = new Request();// both stations,both dates,D(<=),A(>=)
	List<Request> r1_9 = Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8, r9);
	private Request r10 = new Request();// both stations, both dates, D("X")
	private Request r11 = new Request();// equal stations

	private Date date = new Date();
	private String station1 = "Grodno";
	private String station2 = "Minsk";
	private String l = "<=";// less
	private String g = ">=";// greater

	@Before
	public void before() {
		// setting stations:
		for (Request r : r1_9) {
			r.setDepartureStation(station1);
			r.setDestinationStation(station2);
		}
		r10.setDepartureStation(station1);
		r10.setDestinationStation(station2);
		r11.setDepartureStation(station1);
		r11.setDestinationStation(station1);

		// setting dates:
		r2.setArrivalDate(date);
		r3.setArrivalDate(date);
		r4.setDepartureDate(date);
		r5.setDepartureDate(date);
		for (Request r : Arrays.asList(r6, r7, r8, r9, r10)) {
			r.setDepartureDate(date);
			r.setArrivalDate(date);
		}

		// setting compare conditions:
		r2.setArrCondition(l);
		r3.setArrCondition(g);
		r4.setDepCondition(g);
		r5.setDepCondition(l);
		r6.setDepCondition(g);
		r6.setArrCondition(l);
		r7.setDepCondition(g);
		r7.setArrCondition(g);
		r8.setDepCondition(l);
		r8.setArrCondition(l);
		r9.setDepCondition(l);
		r9.setArrCondition(g);
		r10.setDepCondition("X");

		searchResultDaoMock = Mockito.mock(SearchResultDao.class);

		ReflectionTestUtils.setField(service, "srDao", searchResultDaoMock);
	}

	@Test
	public void findTest() {

		for (Request r : r1_9) {
			service.find(r);
		}
		verify(searchResultDaoMock).getResultsNoDateSpecified(r1);
		verify(searchResultDaoMock).getResultsArrivalBefore(r2);
		verify(searchResultDaoMock).getResultsArrivalAfter(r3);
		verify(searchResultDaoMock).getResultsDepartureAfter(r4);
		verify(searchResultDaoMock).getResultsDepartureBefore(r5);
		verify(searchResultDaoMock).getResultsBetweenDates(r6);
		verify(searchResultDaoMock).getResultsAfterDates(r7);
		verify(searchResultDaoMock).getResultsBeforeDates(r8);
		verify(searchResultDaoMock).getResultsNotBetweenDates(r9);

		String text0 = "";
		try {
			service.find(r0);
		} catch (IllegalArgumentException e) {
			text0 = e.getMessage();
		}
		assertEquals("Station fields must not be null!", text0);
		
		String text10 = "";
		try {
			service.find(r10);
		} catch (IllegalArgumentException e) {
			text10 = e.getMessage();
		}
		assertEquals("Illegal comparing condition state!", text10);
		
		String text11 = "";
		try {
			service.find(r11);
		} catch (IllegalArgumentException e) {
			text11 = e.getMessage();
		}
		assertEquals("Stations must be different!", text11);

	}

	@Test
	public void getAllTest() {
		service.getAll(1, 2);
		verify(searchResultDaoMock).getAll(1, 2);
	}

	@Test
	public void getCountTest() {
		service.getCount();
		verify(searchResultDaoMock).getCount();
	}

}
