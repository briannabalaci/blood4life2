package protocol;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;

import java.time.LocalDate;

public class AddUserRequest implements Request {
    private final String firstName;
    private final String lastName;
    private final BloodType bloodType;
    private final Rh rh;
    private final String email;
    private final Integer height;
    private final Double weight;
    private final LocalDate birthDate;
    private final Gender gender;
    private final String cnp;

    public AddUserRequest(String firstName, String lastName, BloodType bloodType, Rh rh, String email, Integer height, Double weight, LocalDate birthDate, Gender gender, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bloodType = bloodType;
        this.rh = rh;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Rh getRh() {
        return rh;
    }

    public String getEmail() {
        return email;
    }

    public Integer getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getCnp() {
        return cnp;
    }
}
