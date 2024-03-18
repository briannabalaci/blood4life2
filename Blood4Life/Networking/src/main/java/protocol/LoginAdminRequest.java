package protocol;

public class LoginAdminRequest implements Request{
    final  String username;
    final String password;

    public LoginAdminRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
