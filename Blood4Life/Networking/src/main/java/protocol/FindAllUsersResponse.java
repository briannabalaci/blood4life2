package protocol;

import domain.Patient;
import domain.User;

import java.util.List;

public class FindAllUsersResponse implements Response{
    private final List<User> users;

    public FindAllUsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
