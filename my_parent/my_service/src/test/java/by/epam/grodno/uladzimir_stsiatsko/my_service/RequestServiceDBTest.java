package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Request;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class RequestServiceDBTest {
	
	private int cnt;

	@Autowired
	private RequestService service;

	@Test
	public void setQuieryTest() {
		Request request = new Request();
		request.setDepartureStation("Grodno");
		request.setDestinationStation("Kukuevo");
		request.setDepartureDate(new Timestamp(99999));
		request.setArrivalDate(new Timestamp(99999));
		service.find(request);
	}

	@Test
	public void executeQuieryTest() {
		Request request = new Request();
		request.setDepartureStation("ГРОДНО");
		request.setDestinationStation("МИНСК");
		request.setDepartureDate(new Timestamp(99999));
		request.setArrivalDate(new Timestamp(99999));
		List<TripList> resList = service.executeQuiery(request);
		
		assertFalse(resList.isEmpty());

		resList.forEach(new Consumer<TripList>() {
			@Override
			public void accept(TripList tl) {
			assertNotNull(tl.getId());
			System.out.println("Id рейса: " + tl.getId());
			assertNotNull(tl.getRouteId());
			System.out.println("Id маршрута: " + tl.getRouteId());
			assertNotNull(tl.getTrainId());
			System.out.println("Id поезда: " + tl.getTrainId());
			System.out.println("Дата отправления: " + tl.getDepartureDate());
			System.out.println("Продано билетов: " + tl.getTicketsSold());
			System.out.println("---------------------");
			cnt++;
			}
		});
		
		System.out.println("Рейсов в выдаче: " + cnt);
	}

	@Test
	public void getRequestTest() {
		assertNotNull(service.getRequest(1));
	}

}
