package repository;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.abstractRepo.UserRepositoryInterface;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Table(name="Patients")
public class UserRepository implements UserRepositoryInterface {
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("Initializing UserRepository");
    }

    @Override
    public User findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where id=:id",
                    User.class);
            query.setParameter("id", id);
            User user = query.uniqueResult();
            logger.info("Reading from database in UserRepository -> findOne");
            session.getTransaction().commit();
            return user;
        }
    }


    @Override
    public List<User> findAll() {
        List<User> users;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        }
        logger.info("Reading from database in UserRepository -> findAll");
        return users;
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        logger.info("Executing query in UserRepository -> save");
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(id);
            session.getTransaction().commit();
        }
        logger.info("Executing query in UserRepository -> delete");
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        logger.info("Executing query in UserRepository -> update");
    }

    @Override
    public User findUserByCNP(String cnp) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where cnp=:cnpParam",
                    User.class);
            query.setParameter("cnpParam", cnp);
            User user = query.uniqueResult();
            session.getTransaction().commit();
            logger.info("Reading from database in UserRepository -> findUserByCNP");
            return user;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where email=:emailParam",
                    User.class);
            query.setParameter("emailParam", email);
            User user = query.uniqueResult();
            session.getTransaction().commit();
            logger.info("Reading from database in UserRepository -> findUserByEmail");
            return user;
        }
    }

    @Override
    public List<User> findUsersByBloodTypeAndRh(BloodType bloodType, Rh rh) {
        List<User> users;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where bloodType=:bloodTypeParam and rh=:rhParam",
                    User.class);
            query.setParameter("bloodTypeParam", bloodType);
            query.setParameter("rhParam", rh);
            users = query.list();
            session.getTransaction().commit();
            logger.info("Reading from database in UserRepository -> findUsersByBloodTypeAndRh");
            return users;
        }
    }
}
