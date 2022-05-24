package bdtc.lab2.model;

import java.io.Serializable;

public class CountryAirportDictionary implements Serializable {
    private String airport;
    private String country;

    public CountryAirportDictionary(String airport, String country) {
        this.country = country;
        this.airport = airport;
    }

    public CountryAirportDictionary() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }
}
