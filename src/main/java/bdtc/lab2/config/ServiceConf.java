package bdtc.lab2.config;

import bdtc.lab2.controller.FlightsController;
import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.dao.FlightRepository;
import bdtc.lab2.model.CountryAirportDictionary;
import bdtc.lab2.model.FlightEntity;
import bdtc.lab2.service.FlightService;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@Configuration
@Import(IgniteConf.class)
public class ServiceConf {
    @Bean
    FlightRepository testServiceRepository(Ignite ignite, CacheConfiguration<UUID, FlightEntity> flightCacheConf,
                                           CacheConfiguration<UUID, FlightAggregation> flightAggregationCacheConfiguration,
                                           CacheConfiguration<String, CountryAirportDictionary> dictionaryCacheConfiguration){
        return new FlightRepository(ignite, flightCacheConf, flightAggregationCacheConfiguration, dictionaryCacheConfiguration);
    }

    @Bean
    FlightService testBusinessLogicService(FlightRepository flightRepository){
        return new FlightService(flightRepository);
    }

    @Bean
    FlightsController testServiceController(FlightService flightService){
        return new FlightsController(flightService);
    }
}
