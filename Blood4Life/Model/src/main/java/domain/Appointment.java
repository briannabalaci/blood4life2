package domain;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Appointments")
public class Appointment implements Serializable {
    private Long appointmentId;
    private User user;
    private Patient patient;
    private DonationCentre donationCentre;
    private Date date;
    private Time time;

    public Appointment(User user, Patient patient, DonationCentre donationCentre, Date date, Time time) {
        this.user = user;
        this.patient = patient;
        this.donationCentre = donationCentre;
        this.date = date;
        this.time = time;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne
    public DonationCentre getDonationCentre() {
        return donationCentre;
    }

    public void setDonationCentre(DonationCentre donationCentre) {
        this.donationCentre = donationCentre;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "time")
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Appointment() {
    }
}
