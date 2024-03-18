package protocol;

import domain.Appointment;
import domain.Patient;

import java.util.List;

public class FindPreviousAppointmentsByUserResponse implements Response{
    private final List<Appointment> patients;

    public FindPreviousAppointmentsByUserResponse(List<Appointment> patients) {
        this.patients = patients;
    }

    public List<Appointment> getPatients() {
        return patients;
    }
}
