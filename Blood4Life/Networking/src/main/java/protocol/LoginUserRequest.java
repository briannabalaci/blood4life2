package protocol;

import domain.User;

import java.util.List;

public class LoginUserRequest implements Request{

    private List<String> userLoginInfo;

    public LoginUserRequest(List<String> userLoginInfo){ this.userLoginInfo = userLoginInfo; }

    public List<String> getUserLoginInfo() {
        return userLoginInfo;
    }
}
