package protocol;

import domain.DonationCentre;
import domain.Patient;
import domain.User;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class AddAppointmentRequest implements Request {
    private final User user;
    private final Patient patient;
    private final DonationCentre donationCentre;
    private final Date date;
    private final Time time;

    public AddAppointmentRequest(User user, Patient patient, DonationCentre donationCentre, Date date, Time time) {
        this.user = user;
        this.patient = patient;
        this.donationCentre = donationCentre;
        this.date = date;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Patient getPatient() {
        return patient;
    }

    public DonationCentre getDonationCentre() {
        return donationCentre;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
