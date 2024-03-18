package service;

import domain.Appointment;
import domain.DonationCentre;
import domain.Patient;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import domain.enums.Severity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ServiceInterface {
    void loginAdmin(String username, String password);
    void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded);
    void addDonationCentre(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour);
    User loginUser(String username, String cnp);
    void addUser(String firstName, String lastName, String email, String cnp, LocalDate birthdate, Gender gender, BloodType bloodType, Rh rh, Double weight, Integer height);
    List<DonationCentre> findAllDonationCentres();
    List<Patient> findAllPatients();
    List<Patient> findAllCompatiblePatients(BloodType type, Rh rh);
    void addAppointment(User user, Patient patient, DonationCentre centre, Date date, Time time);
    List<Appointment> findAllAppointments();
    List<User> findAllUsers();
    List<Appointment> findPreviousAppointmentsByUser(User user, int startPosition, int returnedRowsNo);
    int countPreviousAppointmentsByUser(User user);
    List<Appointment> findFutureAppointmentsByUser(User loggedUser, int i, int pageSize);
    int countFutureAppointmentsByUser(User loggedUser);
    void cancelAppointment(Appointment appointment);
}
