package protocol;

import domain.User;

public class CountPreviousAppointmentsByUserRequest implements Request{
    private final User user;

    public CountPreviousAppointmentsByUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
