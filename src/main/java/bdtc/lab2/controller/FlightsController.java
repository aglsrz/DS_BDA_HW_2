package bdtc.lab2.controller;

import bdtc.lab2.controller.model.Flight;
import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.model.CountryAirportDictionary;
import bdtc.lab2.model.FlightEntity;
import bdtc.lab2.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flight")
public class FlightsController {

    private FlightService flightService;

    public FlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(path = {"/add"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightEntity> createRace(@RequestBody Flight flight) {
        FlightEntity flightEntity = flightService.processCreate(flight);
        return new ResponseEntity<>(flightEntity, HttpStatus.OK);
    }

    @PostMapping(path = {"/add/list"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createRacesList(@RequestBody List<Flight> flights) {
        int number = flightService.createList(flights);
        return new ResponseEntity<>(number, HttpStatus.OK);
    }

    @PostMapping(path = {"/add/dict"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createPerson(@RequestBody List<CountryAirportDictionary> dict) {
        flightService.createDict(dict);
    }

    @GetMapping(path = {"/get/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightEntity> getFlight(@PathVariable String id) {
        FlightEntity flightEntity = flightService.processGet(id);
        return new ResponseEntity<>(flightEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlightEntity>> getAll() {
        List<FlightEntity> flightEntity = flightService.processGetAll();
        System.out.println("TIME:" + flightEntity.get(0).getDeployTime());
        return new ResponseEntity<>(flightEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/dict"})
    public ResponseEntity<List<CountryAirportDictionary>> getDict() {
        return new ResponseEntity<>(flightService.getDict(), HttpStatus.OK);
    }

    @GetMapping(path = {"/compute"})
    public void compute() {
        flightService.compute();
    }

    @GetMapping(path = {"/result"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlightAggregation>> getResults() {
        List<FlightAggregation> raceResults = flightService.getResults();
        return new ResponseEntity<>(raceResults, HttpStatus.OK);
    }
}
