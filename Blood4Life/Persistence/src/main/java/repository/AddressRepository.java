package repository;

import domain.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import repository.abstractRepo.AddressRepositoryInterface;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class AddressRepository implements AddressRepositoryInterface {
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AddressRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("Initializing AddressRepository");
    }

    @Override
    public List<Address> findAddressesByCounty(String county) {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("from Address where county =:countyParam",
                    Address.class);
            query.setParameter("countyParam", county);
            addresses = query.list();
            session.getTransaction().commit();
            return addresses;
        }
    }

    @Override
    public List<Address> findAddressesByCity(String city) {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("from Address where city =:cityParam",
                    Address.class);
            query.setParameter("cityParam", city);
            addresses = query.list();
            session.getTransaction().commit();
            return addresses;
        }
    }

    @Override
    public Address findOne(String county, String city, String street, int number) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("from Address where county=:countyParam and city=:cityParam and street=:streetParam and number=:numberParam",
                    Address.class);
            query.setParameter("countyParam", county);
            query.setParameter("cityParam", city);
            query.setParameter("streetParam", street);
            query.setParameter("numberParam", number);
            Address address = query.uniqueResult();
            session.getTransaction().commit();
            return address;
        }
    }

    @Override
    public Address findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("from Address where id=?1",
                    Address.class);
            query.setParameter(1, id);
            Address address = query.uniqueResult();
            session.getTransaction().commit();
            return address;
        }
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            addresses = session.createQuery("from Address", Address.class).list();
            session.getTransaction().commit();
        }
        return addresses;
    }

    @Override
    public void save(Address address) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(id);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Address address) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        }
    }
}
