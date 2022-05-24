package bdtc.lab2.utils;

import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.dao.FlightRepository;
import bdtc.lab2.model.FlightEntity;
import org.apache.ignite.Ignite;

import java.sql.Timestamp;
import java.util.*;

public class EntityUtils {
    private FlightRepository testServiceRepository;
    private Ignite ignite;

    public static final String NUMBER_1 = "Y134";
    public static final Timestamp TIME_1 = Timestamp.valueOf("2022-05-01 09:10:50");
    public static final String AIRPORT_ARRIVE_1 = "SVO";
    public static final String AIRPORT_DEPLOY_1 = "UFA";

    public static final String NUMBER_2 = "Y134";
    public static final Timestamp TIME_2 = Timestamp.valueOf("2022-05-01 09:15:13");
    public static final String AIRPORT_ARRIVE_2 = "UFA";
    public static final String AIRPORT_DEPLOY_2 = "SVO";

    public static final String COUNTRY_DEPLOY = "Russia";
    public static final String COUNTRY_ARRIVE = "Russia";
    public static final Timestamp TIME_RESULT = Timestamp.valueOf("2022-05-01 09:00:00");
    public static final int COUNT = 2;

    public EntityUtils(FlightRepository testServiceRepository, Ignite ignite) {
        this.testServiceRepository = testServiceRepository;
        this.ignite = ignite;
    }

    public FlightEntity createAndSaveFlightEntity() {
        FlightEntity flightEntity = createFlightEntity();
        testServiceRepository.save(flightEntity);

        return flightEntity;
    }

    public void clearPersonEntitiesCache() {
        ignite.getOrCreateCache("flight").clear();
    }

    public static FlightEntity createFlightEntity() {
        return new FlightEntity(NUMBER_1, TIME_1, AIRPORT_DEPLOY_1, AIRPORT_ARRIVE_1);
    }

    public static FlightEntity createFlightEntity_2() {
        return new FlightEntity(NUMBER_1, TIME_1, AIRPORT_DEPLOY_1, AIRPORT_ARRIVE_1);
    }

    public static List<FlightAggregation> getResult() {
        return Collections.singletonList(new FlightAggregation(TIME_RESULT, COUNTRY_DEPLOY, COUNTRY_ARRIVE, COUNT));
    }
}
