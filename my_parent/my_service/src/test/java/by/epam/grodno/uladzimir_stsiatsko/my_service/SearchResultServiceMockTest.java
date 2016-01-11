package by.epam.grodno.uladzimir_stsiatsko.my_service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import by.epam.grodno.uladzimir_stsiatsko.my_dao.dao.SearchResultDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db-context.xml")
public class SearchResultServiceMockTest {

		@Autowired
		private SearchResultService service;
		
		private SearchResultDao searchResultDaoMock;
	
//		private SearchResult searchResult = new SearchResult();
	
		@Before
		public void before() {
			searchResultDaoMock = Mockito.mock(SearchResultDao.class);
			
//			when(searchResultDaoMock.containsBill(1)).thenReturn(true);
//			when(searchResultDaoMock.containsBill(2)).thenReturn(false);
//			when(searchResultDaoMock.getPriceElements(searchResult)).thenReturn(Arrays.asList(1.1, 2.2, 3.3));
//			when(searchResultDaoMock.getBillingNumber("EUR")).thenReturn(1);
//			when(searchResultDaoMock.getBillingNumber("GBP")).thenReturn(0);

			ReflectionTestUtils.setField(service, "srDao", searchResultDaoMock);
		}
		
		
		@Test
		public void findTest() {
			
		}
		
		@Test
		public void getAllTest() {
			service.getAll(1, 2);
			searchResultDaoMock.getAll(1, 2);
		}
		
		@Test
		public void getCountTest() {
			
		}


}
