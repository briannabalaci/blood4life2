package protocol;

import domain.User;

public class FindPreviousAppointmentsByUserRequest implements Request{
    private final User user;
    private final int startPosition;
    private final int pageSize;

    public FindPreviousAppointmentsByUserRequest(User user, int startPosition, int pageSize) {
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
