package protocol;

import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;

import java.time.LocalDate;

public class AddPatientRequest implements Request{
    final private String cnp;
    final private String firstName;
    final private String lastName;
    final private LocalDate birthday;
    final private BloodType bloodType;
    final private Rh rh;
    final private Severity severity;
    final private Integer bloodQuantityNeeded;

    public AddPatientRequest(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, Integer bloodQuantityNeeded) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.bloodType = bloodType;
        this.rh = rh;
        this.severity = severity;
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    public String getCnp() {
        return cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Rh getRh() {
        return rh;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Integer getBloodQuantityNeeded() {
        return bloodQuantityNeeded;
    }
}
