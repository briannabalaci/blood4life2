import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.*;
import server.Service;
import validator.AddressValidator;
import validator.DonationCentreValidator;
import validator.PatientValidator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class Blood4LifeConfig {
    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch(Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }

    @Bean
    AddressRepository addressRepository(){
        return new AddressRepository(sessionFactory());
    }

    @Bean
    AdminRepository adminRepository(){
        return new AdminRepository(sessionFactory());
    }

    @Bean
    AppointmentRepository appointmentRepository(){
        return new AppointmentRepository(sessionFactory());
    }

    @Bean
    DonationCentreRepository donationCentreRepository(){
        return new DonationCentreRepository(sessionFactory());
    }

    @Bean
    PatientRepository patientRepository(){
        return new PatientRepository(sessionFactory());
    }

    @Bean
    UserRepository userRepository(){
        return new UserRepository(sessionFactory());
    }

    @Bean
    PatientValidator patientValidator(){
        return new PatientValidator();
    }

    @Bean
    AddressValidator addressValidator(){
        return new AddressValidator();
    }

    @Bean
    DonationCentreValidator donationCentreValidator(){
        return new DonationCentreValidator(addressValidator());
    }

    @Bean
    Service service() {
        return new Service(userRepository(), appointmentRepository(), donationCentreRepository(), patientRepository(), adminRepository(), patientValidator(), donationCentreValidator(), addressRepository());
    }
}