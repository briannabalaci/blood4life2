package protocol;

import domain.Appointment;

import java.util.List;

public class FindFutureAppointmentsByUserResponse implements Response{

    private final List<Appointment> patients;

    public FindFutureAppointmentsByUserResponse(List<Appointment> patients) {
        this.patients = patients;
    }

    public List<Appointment> getPatients() {
        return patients;
    }
}
