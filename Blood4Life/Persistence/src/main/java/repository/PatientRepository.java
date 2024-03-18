package repository;

import domain.Address;
import domain.DonationCentre;
import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.abstractRepo.PatientRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientRepository implements PatientRepositoryInterface {
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public PatientRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("Initializing PatientRepository");
    }

    @Override
    public Patient findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Patient> query = session.createQuery("from Patient where id=:id",
                    Patient.class);
            query.setParameter("id", id);
            Patient patient = query.uniqueResult();
            logger.info("Reading from database in PatientRepository -> findOne");
            session.getTransaction().commit();
            return patient;
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            patients = session.createQuery("from Patient ", Patient.class).list();
            session.getTransaction().commit();
        }
        logger.info("Reading from database in PatientRepository -> findAll");
        return patients;
    }

    @Override
    public void save(Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(patient);
            session.getTransaction().commit();
        }
        logger.info("Executing query in PatientRepository -> save");
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(id);
            session.getTransaction().commit();
        }
        logger.info("Executing query in PatientRepository -> delete");
    }

    @Override
    public void update(Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(patient);
            session.getTransaction().commit();
        }
        logger.info("Executing query in PatientRepository -> update");
    }


    @Override
    public Patient findPatientsByQuantity(Integer quantity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Patient> query = session.createQuery("from Patient where bloodQuantityNeeded=:quantityParam",
                    Patient.class);
            query.setParameter("quantityParam", quantity);
            Patient patient = query.uniqueResult();
            session.getTransaction().commit();
            logger.info("Reading from database in PatientRepository -> findPatientsByQuantity");
            return patient;
        }
    }

    @Override
    public Patient findPatientsBySeverity(Severity severity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Patient> query = session.createQuery("from Patient where severity=:severityParam",
                    Patient.class);
            query.setParameter("severityParam", severity);
            Patient patient = query.uniqueResult();
            session.getTransaction().commit();
            logger.info("Reading from database in PatientRepository -> findPatientsBySeverity");
            return patient;
        }
    }

    @Override
    public List<Patient> findPatientsByBloodTypeAndRh(BloodType bloodType, Rh rh) {
        List<Patient> patients;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Patient> query = session.createQuery("from Patient where bloodType=:bloodTypeParam and rh=:rhParam",
                    Patient.class);
            query.setParameter("bloodTypeParam", bloodType);
            query.setParameter("rhParam", rh);
            patients = query.list();
            session.getTransaction().commit();
            logger.info("Reading from database in PatientRepository -> findPatientsByBloodTypeAndRh");
            return patients;
        }
    }
}
