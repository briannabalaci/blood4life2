package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "DonationCentres")
public class DonationCentre implements Serializable {
    private Long centreID;
    private Address address;
    private String name;
    private int maximumCapacity;
    private LocalTime openHour;
    private LocalTime closeHour;


    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    public Long getCentreID() {
        return centreID;
    }

    public void setCentreID(Long aLong) {
        this.centreID = aLong;
    }

    public DonationCentre(Address address, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
        this.address = address;
        this.name = name;
        this.maximumCapacity = maximumCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    @ManyToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "capacity")
    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Column(name = "openHour")
    public LocalTime getOpenHour() {
        return openHour;
    }

    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }

    @Column(name = "closeHour")
    public LocalTime getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(LocalTime closeHour) {
        this.closeHour = closeHour;
    }

    @Override
    public String toString() {
        return name + " (" + address.getCounty() + ", " + address.getCity() + ", " + address.getStreet() + ", " + address.getNumber() + ")";
    }

    public DonationCentre() {
    }
}
