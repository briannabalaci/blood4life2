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
import service.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ServiceProxy implements ServiceInterface {
    private final String host;
    private final int port;
    private ClientWorker client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private final BlockingQueue<Response> responses;
    private volatile boolean finished;
    private final Logger logger = Logger.getLogger("logging.txt");

    public ServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
        logger.info("Initializing ServiceProxy");
    }

    private void closeConnection() {
        finished = true;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            client = null;
            logger.info("Closing connection in ServiceProxy -> closeConnection");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            logger.severe("Exiting ServiceProxy -> closeConnection with IOException");
            System.exit(1);
        }
    }

    private void sendRequest(Request request) throws ServerException {
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException ioException) {
            logger.severe("Exiting ServiceProxy -> sendRequest with IOException");
            throw new ServerException("Error while sending object " + ioException.getMessage());
        }
    }

    private Response readResponse() throws ServerException {
        Response response;
        try {
            response = responses.take();
            logger.info("Reading response in ServiceProxy -> readResponse");
        } catch (InterruptedException interruptedException) {
            logger.severe("Exiting ServiceProxy -> sendRequest with InterruptedException");
            throw new ServerException("Error while reading response " + interruptedException.getMessage());
        }
        return response;
    }

    private void initializeConnection() throws ServerException {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
            finished = false;
            logger.info("Initializing connection in ServiceProxy -> initializeConnection");
            startReader();
        } catch (IOException ioException) {
            logger.severe("Exiting ServiceProxy -> initializeConnection with IOException");
            System.exit(1);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) {

    }

    @Override
    public void loginAdmin(String username, String password) {
        logger.info("Logging in admin in ServiceProxy -> loginAdmin");
        initializeConnection();
        sendRequest(new LoginAdminRequest(username, password));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            closeConnection();
            throw new ServerException(errorResponse.getMessage());
        }
        logger.info("Logging in user in ServiceProxy -> loginUser");

    }

    @Override
    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {
        logger.info("Adding patient in ServiceProxy -> addPatient");
        sendRequest(new AddPatientRequest(cnp, firstName, lastName, birthday, bloodType, rh, severity, bloodQuantityNeeded));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
    }

    @Override
    public void addDonationCentre(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
        logger.info("Adding donation centre in ServiceProxy -> addDonationCentre");
        sendRequest(new AddDonationCentreRequest(county, city, street, number, name, maximumCapacity, openHour, closeHour));
        Response response = readResponse();
        if (response instanceof ErrorResponse errorResponse) {
            throw new ServerException(errorResponse.getMessage());
        }
    }

    @Override
    public User loginUser(String username, String cnp) {
        initializeConnection();
        User connectedUser = null;
        List<String> info = new ArrayList<>();
        info.add(username);
        info.add(cnp);
        sendRequest(new LoginUserRequest(info));
        Response response = readResponse();
        if (response instanceof LoginUserOkResponse)
            connectedUser = ((LoginUserOkResponse) response).getUser();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            closeConnection();
            throw new ServerException(errorResponse.getMessage());
        }
        logger.info("Logging in user in ServiceProxy -> loginUser");
        return connectedUser;
    }

    @Override
    public void addUser(String firstName, String lastName, String email, String cnp, LocalDate birthdate, Gender gender, BloodType bloodType, Rh rh, Double weight, Integer height) {
        initializeConnection();
        sendRequest(new AddUserRequest(firstName, lastName, bloodType, rh, email, height, weight, birthdate, gender, cnp));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        logger.info("Adding user in ServiceProxy -> addUser");
    }

    @Override
    public List<DonationCentre> findAllDonationCentres() {
        logger.info("Finding donation centres in ServiceProxy -> findAllDonationCentres");
        sendRequest(new FindDonationCentersRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindDonationCenterResponse findDonationCenterResponse = (FindDonationCenterResponse) response;
        logger.info("Finding compatible patients in ServiceProxy -> findAllCompatiblePatients");
        return findDonationCenterResponse.getDonationCentres();
    }

    @Override
    public List<Patient> findAllPatients() {
        logger.info("Finding patients in ServiceProxy -> findAllPatients");
        sendRequest(new FindAllPatientsRequest());
        Response response = readResponse();
        List<Patient> allPatients = new ArrayList<>();
        if (response instanceof ErrorResponse errorResponse) {
            throw new ServerException(errorResponse.getMessage());
        }
        FindAllPatientsResponse findAllPatientsResponse = (FindAllPatientsResponse) response;
        return findAllPatientsResponse.getPatients();
    }

    @Override
    public List<Patient> findAllCompatiblePatients(BloodType bloodType, Rh rh) {
        sendRequest(new FindCompatiblePatientsRequest(bloodType, rh));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindCompatiblePatientsResponse findCompatiblePatientsResponse = (FindCompatiblePatientsResponse) response;
        logger.info("Finding compatible patients in ServiceProxy -> findAllCompatiblePatients");
        return findCompatiblePatientsResponse.getPatients();
    }

    @Override
    public void addAppointment(User user, Patient patient, DonationCentre centre, Date date, Time time) {
        sendRequest(new AddAppointmentRequest(user, patient, centre, date, time));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            System.out.println(errorResponse.getMessage());
            throw new ServerException(errorResponse.getMessage());
        }
        logger.info("Adding appointment in ServiceProxy -> addAppointment");
    }

    @Override
    public List<Appointment> findAllAppointments() {
        sendRequest(new FindAllAppointmentsRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindAllAppointmentsResponse findAllAppointmentsResponse = (FindAllAppointmentsResponse) response;
        logger.info("Finding appointments in ServiceProxy -> findAllAppointments");
        return findAllAppointmentsResponse.getAppointments();
    }

    @Override
    public List<User> findAllUsers() {
        logger.info("Finding users in ServiceProxy -> findAllUsers");
        sendRequest(new FindAllUsersRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindAllUsersResponse findAllUsersResponse = (FindAllUsersResponse) response;
        return findAllUsersResponse.getUsers();
    }

    @Override
    public List<Appointment> findPreviousAppointmentsByUser(User user, int startPosition, int pageSize) {
        sendRequest(new FindPreviousAppointmentsByUserRequest(user, startPosition, pageSize));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindPreviousAppointmentsByUserResponse findPreviousAppointmentsByUserResponse = (FindPreviousAppointmentsByUserResponse) response;
        logger.info("Finding previous appointments of a user in ServiceProxy -> findPreviousAppointmentsByUser");
        return findPreviousAppointmentsByUserResponse.getPatients();
    }

   @Override
    public int countPreviousAppointmentsByUser(User user) {
        sendRequest(new CountPreviousAppointmentsByUserRequest(user));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        CountPreviousAppointmentsByUserResponse countPreviousAppointmentsByUserResponse = (CountPreviousAppointmentsByUserResponse) response;
        logger.info("Return the number of previous appointments of a user in ServiceProxy -> countPreviousAppointmentsByUser");
        return countPreviousAppointmentsByUserResponse.getNoAppointments();
    }

    @Override
    public List<Appointment> findFutureAppointmentsByUser(User loggedUser, int i, int pageSize) {
        sendRequest(new FindFutureAppointmentsByUserRequest(loggedUser, i, pageSize));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindFutureAppointmentsByUserResponse findFutureAppointmentsByUserResponse = (FindFutureAppointmentsByUserResponse) response;
        logger.info("Finding future appointments of a user in ServiceProxy -> findFutureAppointmentsByUser");
        return findFutureAppointmentsByUserResponse.getPatients();
    }

    @Override
    public int countFutureAppointmentsByUser(User loggedUser) {
        sendRequest(new CountFutureAppointmentsByUserRequest(loggedUser));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        CountFutureAppointmentsByUserResponse countFutureAppointmentsByUserResponse = (CountFutureAppointmentsByUserResponse) response;
        logger.info("Return the number of Future appointments of a user in ServiceProxy -> countPreviousAppointmentsByUser");
        return countFutureAppointmentsByUserResponse.getNoAppointments();
    }

    @Override
    public void cancelAppointment(Appointment appointment) {
        sendRequest(new CancelAppointmentRequest(appointment));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = inputStream.readObject();
                    logger.info("Receiving response " + response + " in ServiceProxy -> run");
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                        logger.info("Handling update in ServiceProxy -> run");
                    } else {
                        try {
                            responses.put((Response) response);
                            logger.info("Adding new response " + response);
                        } catch (InterruptedException interruptedException) {
                            logger.severe("Exiting ServiceProxy with InterruptedException");
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    logger.severe("Exiting ServiceProxy with IOException or ClassNotFoundException");
                }
            }
        }
    }
}
