package bdtc.lab2.controller.model;

import java.sql.Timestamp;

public class Flight {
    private String number;
    private Timestamp deployTime;
    private String airportDeploy;
    private String airportArrive;

    public Flight() {
    }

    public Flight(String number, Timestamp deployTime, String airportDeploy, String airportArrive) {
        this.number = number;
        this.deployTime = deployTime;
        this.airportDeploy = airportDeploy;
        this.airportArrive = airportArrive;
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
