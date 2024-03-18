package repository.abstractRepo;

import domain.Appointment;
import domain.DonationCentre;
import domain.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface AppointmentRepositoryInterface extends RepositoryInterface<Long, Appointment> {
    List<Appointment> findAppointmentsByDateTime(Date date, Time time);
    List<Appointment> findAppointmentsByDonationCentre(DonationCentre donationCentre);
    List<Appointment> findAppointmentsByUser(User user);
    List<Appointment> findPreviousAppointmentsByUser(User user, int startPosition, int returnedRowsNo);
    List<Appointment> findFutureAppointmentsByUser(User user, int startPosition, int returnedRowsNo);
    Integer findNumberAppointmentsAtCenterDate(DonationCentre  donationCentre, Date date);
    Integer findNumberAppointmentsAtCenterDateTime(DonationCentre  donationCentre, Date date, Time time);
    int countPreviousAppointmentsByUser(User user);
    int countFutureAppointmentsByUser(User user);
}
