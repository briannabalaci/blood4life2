package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "Addresses")
public class Address implements Serializable {
    private Long addressID;
    private String city;
    private String county;
    private String street;
    private int number;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long aLong) {
        this.addressID = aLong;
    }

    public Address(String city, String county, String street, int number) {
        this.city = city;
        this.county = county;
        this.street = street;
        this.number = number;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "county")
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Address() {
    }
}
