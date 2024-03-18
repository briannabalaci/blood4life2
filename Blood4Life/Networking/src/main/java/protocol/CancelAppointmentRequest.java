package protocol;

import domain.Appointment;

public class CancelAppointmentRequest implements Request{
    private final Appointment appointment;

    public CancelAppointmentRequest(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
