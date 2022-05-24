package bdtc.lab2.dao;

import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.model.CountryAirportDictionary;
import bdtc.lab2.model.FlightEntity;
import bdtc.lab2.util.MapComputeTaskArg;
import bdtc.lab2.util.MapEventCountTask;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.Cache;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FlightRepository {
    private final Ignite ignite;
    CacheConfiguration<UUID, FlightEntity> flightCacheConfiguration;
    CacheConfiguration<UUID, FlightAggregation> flightAggregationCacheConfiguration;
    CacheConfiguration<String, CountryAirportDictionary> dictionaryCacheConfiguration;


    public FlightRepository(Ignite ignite, CacheConfiguration<UUID, FlightEntity> flightCacheConfiguration,
                            CacheConfiguration<UUID, FlightAggregation> flightAggregationCacheConfiguration,
                            CacheConfiguration<String, CountryAirportDictionary> dictionaryCacheConfiguration) {
        this.ignite = ignite;
        this.flightCacheConfiguration = flightCacheConfiguration;
        this.flightAggregationCacheConfiguration = flightAggregationCacheConfiguration;
        this.dictionaryCacheConfiguration = dictionaryCacheConfiguration;
    }

    public void clearFlightsAggregation(List<FlightAggregation> aggr) {
        ignite.getOrCreateCache(flightAggregationCacheConfiguration).clear();
        for (FlightAggregation flight : aggr) {
            ignite.getOrCreateCache(flightAggregationCacheConfiguration).put(flight.getId(), flight);
        }
    }

    public int createList(List<FlightEntity> races) {
        races.forEach(r -> ignite.getOrCreateCache(flightCacheConfiguration).put(r.getId(), r));
        return races.size();
    }

    public FlightEntity get(UUID id) {
        return ignite.getOrCreateCache(flightCacheConfiguration).get(id);
    }

    public void save(FlightEntity flightEntity) {
        ignite.getOrCreateCache(flightCacheConfiguration).put(flightEntity.getId(), flightEntity);
    }

    public List<FlightEntity> getAll() {
        Iterable<Cache.Entry<UUID, FlightEntity>> iterable = () -> ignite.getOrCreateCache(flightCacheConfiguration).iterator();

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<CountryAirportDictionary> getDict() {
        Iterable<Cache.Entry<String, CountryAirportDictionary>> iterable = () -> ignite.getOrCreateCache(dictionaryCacheConfiguration).iterator();

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<FlightAggregation> result() {
        Iterable<Cache.Entry<UUID, FlightAggregation>> iterable = () -> ignite.getOrCreateCache(flightAggregationCacheConfiguration).iterator();

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());
    }

    public void compute() {
        List<FlightEntity> flights = getAll();
        flights.forEach(this::aggregateByHour);
        List<CountryAirportDictionary> dictionaries = getDict();

        for(FlightEntity flight : flights) {
            for(CountryAirportDictionary dict: dictionaries) {
                if (Objects.equals(flight.getAirportArrive(), dict.getAirport()))
                    flight.setAirportArrive(dict.getCountry());
                else if (Objects.equals(flight.getAirportDeploy(), dict.getAirport()))
                    flight.setAirportDeploy(dict.getCountry());
            }
        }

        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = List.class;

        int count = 0;
        try {
            MapComputeTaskArg arg = new MapComputeTaskArg(flights,
                    FlightRepository.class.getMethod("clearFlightsAggregation", parameterTypes),
                    this);

            count = ignite.compute().execute(MapEventCountTask.class, arg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("RESUUUUULT:" + count);
    }

    private void aggregateByHour(FlightEntity race) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(race.getDeployTime());
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        race.setDeployTime(new Timestamp(cal.getTimeInMillis()));
    }

    public void createDict(List<CountryAirportDictionary> dict) {
        dict.forEach(d -> ignite.getOrCreateCache(dictionaryCacheConfiguration).put(d.getAirport(), d));
    }
}
