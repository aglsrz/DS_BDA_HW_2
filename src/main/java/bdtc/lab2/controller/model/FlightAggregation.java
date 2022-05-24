package bdtc.lab2.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class FlightAggregation {
    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time;
    private String airportDeploy;
    private String airportArrive;
    private int count;

    public FlightAggregation(Timestamp time, String airportDeploy, String airportArrive, int count) {
        this.id = UUID.randomUUID();
        this.time = time;
        this.airportDeploy = airportDeploy;
        this.airportArrive = airportArrive;
        this.count = count;
    }

    public FlightAggregation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAirportDeploy() {
        return airportDeploy;
    }

    public void setAirportDeploy(String airportDeploy) {
        this.airportDeploy = airportDeploy;
    }

    public String getAirportArrive() {
        return airportArrive;
    }

    public void setAirportArrive(String airportArrive) {
        this.airportArrive = airportArrive;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightAggregation that = (FlightAggregation) o;
        return count == that.count &&
                Objects.equals(id, that.id) &&
                Objects.equals(time, that.time) &&
                Objects.equals(airportDeploy, that.airportDeploy) &&
                Objects.equals(airportArrive, that.airportArrive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, airportDeploy, airportArrive, count);
    }
}
