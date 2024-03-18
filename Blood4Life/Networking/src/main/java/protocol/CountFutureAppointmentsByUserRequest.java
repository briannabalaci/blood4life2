package protocol;

import domain.User;

public class CountFutureAppointmentsByUserRequest implements  Request{
    private final User user;

    public CountFutureAppointmentsByUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
