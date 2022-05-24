package bdtc.lab2.service;

import bdtc.lab2.controller.model.Flight;
import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.dao.FlightRepository;
import bdtc.lab2.model.FlightEntity;
import bdtc.lab2.utils.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestBusinessLogicServiceTest.TestBusinessLogicServiceTestConfiguration.class})
public class TestBusinessLogicServiceTest {

    @Autowired
    private FlightService testBusinessLogicService;

    @Autowired
    private FlightRepository testServiceRepository;

    @Test
    public void testCreateAndGet(){
        //create
        Flight flight = new Flight(EntityUtils.NUMBER_1, EntityUtils.TIME_1,
                EntityUtils.AIRPORT_DEPLOY_1, EntityUtils.AIRPORT_ARRIVE_1);

        FlightEntity flightEntity = testBusinessLogicService.processCreate(flight);

        Assert.assertEquals(flight.getNumber(), flightEntity.getNumber());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(flightEntity);

        //getAll
        List<FlightEntity> raceEntityList = testBusinessLogicService.processGetAll();

        Assert.assertEquals(EntityUtils.NUMBER_1, raceEntityList.get(0).getNumber());
        Assert.assertEquals(EntityUtils.NUMBER_1, raceEntityList.get(1).getNumber());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getAll();

        List<FlightAggregation> result = testServiceRepository.result();

        Assert.assertEquals(EntityUtils.TIME_RESULT, result.get(0).getTime());
        Assert.assertEquals(EntityUtils.COUNTRY_DEPLOY, result.get(0).getAirportDeploy());
        Assert.assertEquals(EntityUtils.COUNTRY_ARRIVE, result.get(0).getAirportArrive());
        Assert.assertEquals(EntityUtils.COUNT, result.get(0).getCount());

        Mockito.verify(testServiceRepository, Mockito.times(1)).result();
    }

    @Configuration
    static class TestBusinessLogicServiceTestConfiguration {

        @Bean
        FlightRepository testServiceRepository() {
            FlightRepository testServiceRepository = mock(FlightRepository.class);
            when(testServiceRepository.get(any())).thenReturn(EntityUtils.createFlightEntity());
            when(testServiceRepository.getAll())
                    .thenReturn(Arrays.asList(EntityUtils.createFlightEntity()
                            , EntityUtils.createFlightEntity_2()));
            when(testServiceRepository.result()).thenReturn(EntityUtils.getResult());
            return testServiceRepository;
        }

        @Bean
        FlightService testBusinessLogicService(FlightRepository testServiceRepository){
            return new FlightService(testServiceRepository);
        }
    }

}
