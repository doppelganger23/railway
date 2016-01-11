package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TrainDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.Train;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class TrainServiceTest {

	@Autowired
	private TrainService service;

	private TripListService tlServiceMock;
	private TrainDao trainDaoMock;

	private Train train1 = new Train();
	private Train train2 = new Train();

	@Before
	public void before() {
		train1.setId(1);
		train1.setTrainNumber("T");
		train2.setTrainNumber("F");
		trainDaoMock = Mockito.mock(TrainDao.class);
		tlServiceMock = mock(TripListService.class);

		when(trainDaoMock.containsTrain("T")).thenReturn(true);
		when(trainDaoMock.containsTrain("F")).thenReturn(false);

		when(tlServiceMock.containsTrain(1)).thenReturn(true);
		when(tlServiceMock.containsTrain(2)).thenReturn(false);
		
		ReflectionTestUtils.setField(service, "tDao", trainDaoMock);
		ReflectionTestUtils.setField(service, "tlService", tlServiceMock);
	}

	@Test
	public void addTrainTest() {
		String text = "";
		try {
			service.addTrain(train1);
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("Train number must be unique", text);
		verify(trainDaoMock, never()).addTrain(train1);
		
		service.addTrain(train2);
		verify(trainDaoMock).addTrain(train2);
	}

	@Test
	public void updateTest() {
		service.update(train1);
		verify(trainDaoMock).update(train1);
		
		String text = "";
		try {
			service.update(train2);
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("Train not found", text);
		verify(trainDaoMock, never()).update(train2);
	}

	@Test
	public void containsTrainTest() {
		assertTrue(service.containsTrain("T"));
	}

	@Test
	public void deleteTrainTest() {
		String text = "";
		try{
			service.deleteTrain(1);
		} catch (IllegalArgumentException e) {
			text = e.getMessage();
		}
		assertEquals("This train can't be deleted because of structural integrity needs", text);
		verify(trainDaoMock, never()).deleteTrain(1);
		
		service.deleteTrain(2);
		verify(trainDaoMock).deleteTrain(2);
	}

	@Test
	public void getAllTest() {
		service.getAll(1, 2, "id", "ASCENDING");
		verify(trainDaoMock).getAll(1, 2, "id", "asc");
		
		service.getAll(1, 2, "id", "DESCENDING");
		verify(trainDaoMock).getAll(1, 2, "id", "desc");
	}

	@Test
	public void getCountTest() {
		service.getCount();
		verify(trainDaoMock).getCount();
	}

	@Test
	public void findAllTest() {
		service.findAll();
		verify(trainDaoMock).getAll();
	}

}
