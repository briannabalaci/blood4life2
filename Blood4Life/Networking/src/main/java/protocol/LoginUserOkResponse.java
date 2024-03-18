package protocol;

import domain.User;

public class LoginUserOkResponse implements Response {

    private User user;

    public LoginUserOkResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
