package bdtc.lab2.service;

import bdtc.lab2.controller.model.Flight;
import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.dao.FlightRepository;
import bdtc.lab2.model.CountryAirportDictionary;
import bdtc.lab2.model.FlightEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightEntity processCreate(Flight flight){
        FlightEntity flightEntity = new FlightEntity(flight);
        flightRepository.save(flightEntity);
        return flightEntity;
    }

    public FlightEntity processGet(String id){
        return flightRepository.get(UUID.fromString(id));
    }

    public List<FlightEntity> processGetAll(){
        return flightRepository.getAll();
    }

    public void compute() {
        flightRepository.compute();
    }

    public List<FlightAggregation> getResults() {
        return flightRepository.result();
    }
    
    public List<CountryAirportDictionary> getDict() {
        return flightRepository.getDict();
    }

    public void createDict(List<CountryAirportDictionary> dict) {
        flightRepository.createDict(dict);
    }

    public int createList(List<Flight> flights) {
        return flightRepository.createList(flights.stream()
                .map(FlightEntity::new)
                .collect(Collectors.toList()));
    }
}
