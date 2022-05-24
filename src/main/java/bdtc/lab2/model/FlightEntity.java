package bdtc.lab2.model;

import bdtc.lab2.controller.model.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class FlightEntity {
    private UUID id;
    private String number;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp deployTime;
    private String airportDeploy;
    private String airportArrive;

    public FlightEntity(String number, Timestamp deployTime, String airportDeploy, String airportArrive) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.deployTime = deployTime;
        this.airportDeploy = airportDeploy;
        this.airportArrive = airportArrive;
    }

    public FlightEntity(Flight flight){
        this.id = UUID.randomUUID();
        this.number = flight.getNumber();
        this.deployTime = flight.getDeployTime();
        this.airportDeploy = flight.getAirportDeploy();
        this.airportArrive = flight.getAirportArrive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(deployTime, that.deployTime) && Objects.equals(airportDeploy, that.airportDeploy) && Objects.equals(airportArrive, that.airportArrive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, deployTime, airportDeploy, airportArrive);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", deployTime=" + deployTime +
                ", airportDeploy='" + airportDeploy + '\'' +
                ", airportArrive='" + airportArrive + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Timestamp getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Timestamp deployTime) {
        this.deployTime = deployTime;
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
}
