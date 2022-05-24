package bdtc.lab2.controller.model;

import java.sql.Timestamp;

public class FlightAggrDto {
    private Timestamp time;
    private String airportDeploy;
    private String airportArrive;
    private int count;

    public FlightAggrDto() {
    }

    public FlightAggrDto(Timestamp time, String airportDeploy, String airportArrive) {
        this.time = time;
        this.airportDeploy = airportDeploy;
        this.count = 1;
    }

    public FlightAggrDto(Timestamp time, String airportDeploy, String airportArrive, int count) {
        this.time = time;
        this.airportDeploy = airportDeploy;
        this.airportArrive = airportArrive;
        this.count = count;
    }

    @Override
    public String toString() {
        return "FlightAggrDto{" +
                "time=" + time +
                ", airportDeploy='" + airportDeploy + '\'' +
                ", airportArrive='" + airportArrive + '\'' +
                ", count=" + count +
                '}';
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
}


