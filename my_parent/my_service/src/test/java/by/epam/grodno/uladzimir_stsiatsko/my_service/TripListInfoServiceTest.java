package by.epam.grodno.uladzimir_stsiatsko.my_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.TripListInfoDao;
import by.epam.grodno.uladzimir_stsiatsko.my_dao.model.TripListInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class TripListInfoServiceTest {

	@Autowired
	private TripListInfoService service;

	private TripListInfoDao tripListInfoDaoMock;

	private TripListInfo info1 = new TripListInfo();
	private TripListInfo info2 = new TripListInfo();

	@Before
	public void before() {
		info1.setId(1);
		info2.setId(2);

		tripListInfoDaoMock = Mockito.mock(TripListInfoDao.class);
		when(tripListInfoDaoMock.getCount()).thenReturn(42);
		when(tripListInfoDaoMock.getAll(1, 2, "id", "asc")).thenReturn(Arrays.asList(info1, info2));
		when(tripListInfoDaoMock.getAll(1, 2, "id", "desc")).thenReturn(Arrays.asList(info2, info1));

		ReflectionTestUtils.setField(service, "tliDao", tripListInfoDaoMock);
	}
	
	@Test
	public void getCountTest() {
		int count = service.getCount();
		assertNotNull(count);
		assertEquals(42, count);
	}
	
	@Test
	public void getAllTest() {
		List<TripListInfo> listAsc = service.getAll(1, 2, "id", "ASCENDING");
		List<TripListInfo> listDesc = service.getAll(1, 2, "id", "DESCENDING");
		assertNotNull(listDesc);
		assertEquals(listAsc.get(1), info2);
		assertEquals(listDesc.get(0), info2);
	}

}
