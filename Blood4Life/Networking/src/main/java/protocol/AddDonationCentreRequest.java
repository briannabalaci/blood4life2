package protocol;

import domain.Address;

import java.time.LocalTime;

public class AddDonationCentreRequest implements Request {
    private String county;
    private String city;
    private String street;
    private int number;
    private String name;
    private int maximumCapacity;
    private LocalTime openHour;
    private LocalTime closeHour;

    public AddDonationCentreRequest(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
        this.county = county;
        this.city = city;
        this.street = street;
        this.number = number;
        this.name = name;
        this.maximumCapacity = maximumCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public LocalTime getOpenHour() {
        return openHour;
    }

    public LocalTime getCloseHour() {
        return closeHour;
    }
}
