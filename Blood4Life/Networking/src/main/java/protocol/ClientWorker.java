package protocol;

import domain.Appointment;
import domain.DonationCentre;
import domain.Patient;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import domain.enums.Severity;
import exception.ServerException;
import exception.ValidationException;
import service.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ClientWorker implements Runnable {
    private final ServiceInterface server;
    private final Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private final Logger logger = Logger.getLogger("logging.txt");

    public ClientWorker(ServiceInterface server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
            logger.info("Initializing ClientWorker");
        } catch (IOException ioException) {
            logger.severe("Exiting ClientWorker with IOException");
            System.exit(1);
        }
    }

    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request)request);
                logger.info("Handling request " + request + " in ClientWorker -> run");
                if (response != null) {
                   sendResponse(response);
                   logger.info("Sending response " + response + " in ClientWorker -> run");
                }
            }
//            catch(ValidationException e){
//                throw new ValidationException(e.getMessage());
//            }
            catch (IOException | ClassNotFoundException exception) {
                logger.severe("Exiting ClientWorker -> run with IOException or ClassNotFoundException");
                System.exit(1);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                logger.severe("Exiting ClientWorker -> run with InterruptedException");
                System.exit(1);
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
            logger.info("Closing connection in ClientWorker -> run");
        } catch (IOException ioException) {
            logger.severe("Exiting ClientWorker -> run with IOException");
            System.exit(1);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if (request instanceof LoginUserRequest) {
            logger.info("Receiving LoginUserRequest in ClientWorker -> handleRequest");
            LoginUserRequest loginUserRequest = (LoginUserRequest)request;
            List<String> userInfo = loginUserRequest.getUserLoginInfo();
            String email = userInfo.get(0);
            String cnp = userInfo.get(1);
            try {
                User connectedUser = server.loginUser(email, cnp);
                logger.info("Sending LoginUserOkResponse in ClientWorker -> handleRequest");
                return new LoginUserOkResponse(connectedUser);
            } catch (ServerException serverException) {
                connected = false;
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof FindAllPatientsRequest) {
            logger.info("Receiving FindAllPatientsRequest in ClientWorker -> handleRequest");
            FindAllPatientsRequest findAllPatientsRequest = (FindAllPatientsRequest) request;
            try {
                List<Patient> patients = server.findAllPatients();
                logger.info("Sending FindAllPatientsRequest in ClientWorker -> handleRequest");
                return new FindAllPatientsResponse(patients);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof FindCompatiblePatientsRequest) {
            logger.info("Receiving FindCompatiblePatientsRequest in ClientWorker -> handleRequest");
            FindCompatiblePatientsRequest findCompatiblePatientsRequest = (FindCompatiblePatientsRequest) request;
            BloodType bloodType = findCompatiblePatientsRequest.getBloodType();
            Rh rh = findCompatiblePatientsRequest.getRh();
            try {
                List<Patient> patients = server.findAllCompatiblePatients(bloodType, rh);
                logger.info("Sending FindCompatiblePatientsResponse in ClientWorker -> handleRequest");
                return new FindCompatiblePatientsResponse(patients);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof FindPreviousAppointmentsByUserRequest) {
            logger.info("Receiving FindPreviousAppointmentsByUserRequest in ClientWorker -> handleRequest");
            FindPreviousAppointmentsByUserRequest findPreviousAppointmentsByUserRequest = (FindPreviousAppointmentsByUserRequest) request;
            User user = findPreviousAppointmentsByUserRequest.getUser();
            int startPosition = findPreviousAppointmentsByUserRequest.getStartPosition();
            int pageSize = findPreviousAppointmentsByUserRequest.getPageSize();
            try {
                List<Appointment> appointments = server.findPreviousAppointmentsByUser(user, startPosition, pageSize);
                logger.info("Sending FindPreviousAppointmentsByUserRequest in ClientWorker -> handleRequest");
                return new FindPreviousAppointmentsByUserResponse(appointments);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof FindFutureAppointmentsByUserRequest) {
            logger.info("Receiving FindPreviousAppointmentsByUserRequest in ClientWorker -> handleRequest");
            FindFutureAppointmentsByUserRequest findFutureAppointmentsByUserRequest = (FindFutureAppointmentsByUserRequest) request;
            User user = findFutureAppointmentsByUserRequest.getUser();
            int startPosition = findFutureAppointmentsByUserRequest.getStartPosition();
            int pageSize = findFutureAppointmentsByUserRequest.getPageSize();
            try {
                List<Appointment> appointments = server.findFutureAppointmentsByUser(user, startPosition, pageSize);
                logger.info("Sending FindFutureAppointmentsByUserRequest in ClientWorker -> handleRequest");
                return new FindFutureAppointmentsByUserResponse(appointments);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof AddUserRequest) {
            logger.info("Receiving AddUserRequest in ClientWorker -> handleRequest");
            AddUserRequest addUserRequest = (AddUserRequest) request;
            String firstName = addUserRequest.getFirstName();
            String lastName = addUserRequest.getLastName();
            String cnp = addUserRequest.getCnp();
            String email = addUserRequest.getEmail();
            LocalDate birthday = addUserRequest.getBirthDate();
            int height = addUserRequest.getHeight();
            double weight = addUserRequest.getWeight();
            BloodType bloodType = addUserRequest.getBloodType();
            Rh rh = addUserRequest.getRh();
            Gender gender = addUserRequest.getGender();
            try {
                server.addUser(firstName, lastName, email, cnp, birthday, gender, bloodType, rh, weight, height);
                logger.info("Sending AddUserResponse in ClientWorker -> handleRequest");
                return new AddUserResponse();
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof AddAppointmentRequest) {
            logger.info("Receiving AddAppointmentRequest in ClientWorker -> handleRequest");
            AddAppointmentRequest addAppointmentRequest = (AddAppointmentRequest) request;
            User user = addAppointmentRequest.getUser();
            Patient patient = addAppointmentRequest.getPatient();
            DonationCentre donationCentre = addAppointmentRequest.getDonationCentre();
            Date date = addAppointmentRequest.getDate();
            Time time = addAppointmentRequest.getTime();
            try {
                server.addAppointment(user, patient, donationCentre, date, time);
                logger.info("Sending AddUserResponse in ClientWorker -> handleRequest");
                return new AddAppointmentResponse();
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof AddPatientRequest) {
            logger.info("Receiving AddPatientRequest in ClientWorker -> handleRequest");
            AddPatientRequest addPatientRequest = (AddPatientRequest) request;
            String cnp = addPatientRequest.getCnp();
            String firstName = addPatientRequest.getFirstName();
            String lastName = addPatientRequest.getLastName();
            LocalDate birthday = addPatientRequest.getBirthday();
            BloodType bloodType = addPatientRequest.getBloodType();
            Rh rh = addPatientRequest.getRh();
            Severity severity = addPatientRequest.getSeverity();
            Integer bloodQuantityNeeded = addPatientRequest.getBloodQuantityNeeded();
            try {
                server.addPatient(cnp, firstName, lastName, birthday, bloodType, rh, severity, bloodQuantityNeeded);
                logger.info("Sending AddPatientResponse in ClientWorker -> handleRequest");
                return new AddUserResponse();
            } catch (ServerException | ValidationException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof FindAllAppointmentsRequest) {
            logger.info("Receiving FindAllAppointmentsRequest in ClientWorker -> handleRequest");
            FindAllAppointmentsRequest findAllAppointmentsRequest = (FindAllAppointmentsRequest) request;
            try {
                List<Appointment> appointments = server.findAllAppointments();
                logger.info("Sending FindAllAppointmentsRequest in ClientWorker -> handleRequest");
                return new FindAllAppointmentsResponse(appointments);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof FindAllUsersRequest) {
            logger.info("Receiving FindAllUsersRequest in ClientWorker -> handleRequest");
            FindAllUsersRequest findAllUsersRequest = (FindAllUsersRequest) request;
            try {
                List<User> users = server.findAllUsers();
                logger.info("Sending FindAllUsersRequest in ClientWorker -> handleRequest");
                return new FindAllUsersResponse(users);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof CountPreviousAppointmentsByUserRequest){
            logger.info("Receiving CountPreviousAppointmentsByUserRequest in ClientWorker -> handleRequest");
            CountPreviousAppointmentsByUserRequest countPreviousAppointmentsByUserRequest = (CountPreviousAppointmentsByUserRequest) request;
            User user = countPreviousAppointmentsByUserRequest.getUser();
            try {
                int number = server.countPreviousAppointmentsByUser(user);
                logger.info("Sending CountPreviousAppointmentsByUserResponse in ClientWorker -> handleRequest");
                return new CountPreviousAppointmentsByUserResponse(number);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof CountFutureAppointmentsByUserRequest){
            logger.info("Receiving CountFutureAppointmentsByUserRequest in ClientWorker -> handleRequest");
            CountFutureAppointmentsByUserRequest countFutureAppointmentsByUserRequest = (CountFutureAppointmentsByUserRequest) request;
            User user = countFutureAppointmentsByUserRequest.getUser();
            try {
                int number = server.countFutureAppointmentsByUser(user);
                logger.info("Sending CountFutureAppointmentsByUserResponse in ClientWorker -> handleRequest");
                return new CountFutureAppointmentsByUserResponse(number);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof LoginAdminRequest) {
            logger.info("Receiving LoginAdminRequest in ClientWorker -> handleRequest");
            LoginAdminRequest loginAdminRequest = (LoginAdminRequest) request;
            String username = loginAdminRequest.username;
            String password = loginAdminRequest.password;
            try {
                server.loginAdmin(username,password);
                logger.info("Sending LoginUserOkResponse in ClientWorker -> handleRequest");
                return new LoginAdminOkResponse();
            } catch (ServerException e) {
                connected = false;
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof FindDonationCentersRequest) {
            logger.info("Receiving FindDonationCentersRequest in ClientWorker -> handleRequest");
            FindDonationCentersRequest findDonationCentersRequest = (FindDonationCentersRequest) request;
            try {
                List<DonationCentre> patients = server.findAllDonationCentres();
                logger.info("Sending FindCompatiblePatientsResponse in ClientWorker -> handleRequest");
                return new FindDonationCenterResponse(patients);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if(request instanceof AddDonationCentreRequest addDonationCentreRequest){
            String county = addDonationCentreRequest.getCounty();
            String city = addDonationCentreRequest.getCity();
            String street = addDonationCentreRequest.getStreet();
            int number = addDonationCentreRequest.getNumber();
            String name = addDonationCentreRequest.getName();
            int maximumCapacity = addDonationCentreRequest.getMaximumCapacity();
            LocalTime openHour = addDonationCentreRequest.getOpenHour();
            LocalTime closeHour = addDonationCentreRequest.getCloseHour();
            try{
                server.addDonationCentre(county, city, street, number, name, maximumCapacity, openHour, closeHour);
                return new OkResponse();
            } catch (ServerException | ValidationException ex) {
                return new ErrorResponse(ex.getMessage());
            }
        }

        if(request instanceof CancelAppointmentRequest cancelAppointmentRequest){
            Appointment appointment = cancelAppointmentRequest.getAppointment();
            try{
                server.cancelAppointment(appointment);
                return new OkResponse();
            } catch (ServerException | ValidationException ex) {
                return new ErrorResponse(ex.getMessage());
            }

        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
