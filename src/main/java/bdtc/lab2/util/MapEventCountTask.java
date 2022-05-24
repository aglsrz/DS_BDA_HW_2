package bdtc.lab2.util;

import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.model.FlightEntity;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class MapEventCountTask extends ComputeTaskAdapter<MapComputeTaskArg, Integer> {

    private MapComputeTaskArg updateStatArg;

    @Override
    public @NotNull Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> nodes, @Nullable MapComputeTaskArg arg) throws IgniteException {
        Map<ComputeJob, ClusterNode> map = new HashMap<>();

        Iterator<ClusterNode> it = nodes.iterator();
        // аргумент для прокидывания кеша
        this.updateStatArg = new MapComputeTaskArg(arg.getMethod(), arg.getObject());
        // группировка данных статистики
        Map<String, Map<String, Map<Timestamp, List<FlightEntity>>>> groupByAll = arg.getArg().stream()
                .collect(Collectors
                        .groupingBy(FlightEntity::getAirportArrive,
                                Collectors.groupingBy(FlightEntity::getAirportDeploy,
                                        Collectors.groupingBy(FlightEntity::getDeployTime))));

        for (Map<String, Map<Timestamp, List<FlightEntity>>> groupedByTimeAndDeploy: groupByAll.values()) {
            for (Map<Timestamp, List<FlightEntity>> groupByTime : groupedByTimeAndDeploy.values()) {
                for (List<FlightEntity> flights : groupByTime.values()) {
                    // If we used all nodes, restart the iterator.
                    if (!it.hasNext())
                        it = nodes.iterator();

                    ClusterNode node = it.next();


                    map.put(new ComputeJobAdapter() {
                        @Override
                        public @NotNull Object execute() {
                            FlightEntity flight = flights.get(0);
                            // создание NewsEventStat с подсчитанным полем times
                            return new FlightAggregation(flight.getDeployTime(),
                                    flight.getAirportDeploy(), flight.getAirportArrive(),
                                    flights.size());
                        }
                    }, node);
                }
            }
        }

        return map;
    }

    @Override @Nullable public Integer reduce(List<ComputeJobResult> results) {
        int sumStat = 0;
        List<FlightAggregation> flightAggregationList = new ArrayList<>();

        for (ComputeJobResult result : results){
            FlightAggregation flightAggr = result.<FlightAggregation>getData();
//            FlightAggregation flightAggregation = new FlightAggregation(racesAggr.getTime(),
//                    racesAggr.getAirportDeploy(),
//                    racesAggr.getAirportArrive(),
//                    racesAggr.getCount());
            flightAggregationList.add(flightAggr);
            sumStat += flightAggr.getCount();
        }
        // прокидываем список Entity для сохранения в кеш
        try {
            updateStatArg.invoke(flightAggregationList);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return sumStat;
    }
}
