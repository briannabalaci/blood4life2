package domain;

import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Patients")
public class Patient implements Serializable {
    private Long patientId;
    private String cnp;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private BloodType bloodType;
    private Rh rh;
    private Severity severity;
    private Integer bloodQuantityNeeded;

    public Patient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, Integer bloodQuantityNeeded) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.bloodType = bloodType;
        this.rh = rh;
        this.severity = severity;
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    public Patient() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Column(name = "cnp")
    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "birthday")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Enumerated(EnumType.STRING)
    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    @Enumerated(EnumType.STRING)
    public Rh getRh() {
        return rh;
    }

    public void setRh(Rh rh) {
        this.rh = rh;
    }

    @Enumerated(EnumType.STRING)
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Column(name = "blood_quantity")
    public Integer getBloodQuantityNeeded() {
        return bloodQuantityNeeded;
    }

    public void setBloodQuantityNeeded(Integer bloodQuantityNeeded) {
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + severity + " gravity, " + bloodQuantityNeeded + " ml blood needed)";
    }
}
