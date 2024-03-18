package protocol;

import domain.User;

public class FindFutureAppointmentsByUserRequest implements Request {
    private final User user;
    private final int startPosition;
    private final int pageSize;

    public FindFutureAppointmentsByUserRequest(User user, int startPosition, int pageSize) {
        this.user = user;
        this.startPosition = startPosition;
        this.pageSize = pageSize;
    }

    public User getUser() {
        return user;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getPageSize() {
        return pageSize;
    }
}
