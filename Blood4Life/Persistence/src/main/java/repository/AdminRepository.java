package repository;

import domain.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.abstractRepo.AdminRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AdminRepository implements AdminRepositoryInterface {
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AdminRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("Initializing AdminRepository");
    }

    @Override
    public Admin findOne(String s) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Admin> query = session.createQuery("from Admin where username=:usernameParam ",
                    Admin.class);
            query.setParameter("usernameParam", s);
            Admin admin = query.uniqueResult();
            session.getTransaction().commit();
            return admin;
        }
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            admins = session.createQuery("from Admin", Admin.class).list();
            session.getTransaction().commit();
        }
        return admins;
    }

    @Override
    public void save(Admin entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }

    }

    @Override
    public void delete(String s) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(s);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Admin entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }
}
