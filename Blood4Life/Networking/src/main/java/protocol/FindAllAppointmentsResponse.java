package protocol;

import domain.Appointment;

import java.util.List;

public class FindAllAppointmentsResponse implements Response{
    private final List<Appointment> appointments;

    public FindAllAppointmentsResponse(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
