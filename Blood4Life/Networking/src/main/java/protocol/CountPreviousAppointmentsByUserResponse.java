package protocol;

public class CountPreviousAppointmentsByUserResponse implements Response{
    private final int noAppointments;

    public CountPreviousAppointmentsByUserResponse(int noAppointments) {
        this.noAppointments = noAppointments;
    }

    public int getNoAppointments() {
        return noAppointments;
    }


}
