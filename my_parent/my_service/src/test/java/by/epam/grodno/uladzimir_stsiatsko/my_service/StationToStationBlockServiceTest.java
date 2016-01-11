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

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.StationToStationBlockDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class StationToStationBlockServiceTest {
	
	@Autowired
	private StationToStationBlockService service;
	
	private StationToStationBlockDao blockDaoMock;

	@Before
	public void before() {
		blockDaoMock = Mockito.mock(StationToStationBlockDao.class);

		ReflectionTestUtils.setField(service, "stsDao", blockDaoMock);
	}
	
	@Test
	public void getBlocksTest() {
		service.getBlocks();
		verify(blockDaoMock).getBlocks();
	}

}
