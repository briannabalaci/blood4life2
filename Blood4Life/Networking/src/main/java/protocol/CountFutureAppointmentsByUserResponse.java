package protocol;

public class CountFutureAppointmentsByUserResponse implements Response{
    private final int noAppointments;

    public CountFutureAppointmentsByUserResponse(int noAppointments) {
        this.noAppointments = noAppointments;
    }

    public int getNoAppointments() {
        return noAppointments;
    }
}
