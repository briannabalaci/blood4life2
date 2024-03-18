package repository;

import domain.Appointment;
import domain.DonationCentre;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.abstractRepo.AppointmentRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;
import repository.abstractRepo.PatientRepositoryInterface;
import repository.abstractRepo.UserRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class AppointmentRepository implements AppointmentRepositoryInterface {
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AppointmentRepository( SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("Initializing AppointmentRepository");
    }

    @Override
    public List<Appointment> findAppointmentsByDateTime(Date date, Time time) {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.date=:date and donationCentre.time=:time",
                    Appointment.class);
            query.setParameter("date", date);
            query.setParameter("time", time);
            appointments = query.list();
            session.getTransaction().commit();
            return appointments;
        }
    }

    @Override
    public List<Appointment> findAppointmentsByDonationCentre(DonationCentre donationCentre) {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.donationCentre=:donationCentre ",
                    Appointment.class);
            query.setParameter("donationCentre", donationCentre);
            appointments = query.list();
            session.getTransaction().commit();
            return appointments;
        }
    }

    @Override
    public List<Appointment> findAppointmentsByUser(User user) {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.user=:user ",
                    Appointment.class);
            query.setParameter("user", user);
            appointments = query.list();
            session.getTransaction().commit();
            return appointments;
        }
    }

    @Override
    public List<Appointment> findPreviousAppointmentsByUser(User user, int startPosition, int returnedRowsNo) {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.user=:user and appointment.date<:date ",
                    Appointment.class).setFirstResult(startPosition).setMaxResults(returnedRowsNo);
            Date localDate = Calendar. getInstance(). getTime();
            query.setParameter("user", user);
            query.setParameter("date", localDate);
            appointments = query.list();
            session.getTransaction().commit();
            return appointments;
        }
    }

    @Override
    public int countPreviousAppointmentsByUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select count(*) from Appointment appointment where appointment.user=:user  and appointment.date<:date");
            query.setParameter("user", user);
            Date localDate = Calendar. getInstance(). getTime();
            query.setParameter("date", localDate);
            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        }

    }

    @Override
    public int countFutureAppointmentsByUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select count(*) from Appointment appointment where appointment.user=:user  and appointment.date>:date");
            query.setParameter("user", user);
            Date localDate = Calendar. getInstance(). getTime();
            query.setParameter("date", localDate);
            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        }

    }

    @Override
    public List<Appointment> findFutureAppointmentsByUser(User user, int startPosition, int returnedRowsNo) {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.user=:user and appointment.date >:date ",
                    Appointment.class).setFirstResult(startPosition).setMaxResults(returnedRowsNo);
            Date localDate = Calendar. getInstance(). getTime();
            query.setParameter("user", user);
            query.setParameter("date", localDate);
            appointments = query.list();
            session.getTransaction().commit();
            return appointments;
        }
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDate(DonationCentre donationCentre, Date date) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select count(*) from Appointment appointment where appointment.donationCentre=:donationCentre  and appointment.date=:date");
            query.setParameter("donationCentre", donationCentre);
            query.setParameter("date", date);
            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        }
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDateTime(DonationCentre donationCentre, Date date, Time time) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select count(*) from Appointment appointment where appointment.donationCentre=:donationCentre and appointment.date=:date and appointment.time=:time");
            query.setParameter("donationCentre", donationCentre);
            query.setParameter("date", date);
            query.setParameter("time", time);
            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        }
    }

    @Override
    public Appointment findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Appointment> query = session.createQuery("select appointment from Appointment as appointment where appointment.id=:id",
                    Appointment.class);
            query.setParameter("id", id);
            Appointment appointment = query.uniqueResult();
            session.getTransaction().commit();
            return appointment;
        }
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            appointments = session.createQuery("from Appointment", Appointment.class).list();
            session.getTransaction().commit();
        }
        return appointments;
    }

    @Override
    public void save(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(appointment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        System.out.println(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(findOne(id));
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(appointment);
            session.getTransaction().commit();
        }
    }

}
