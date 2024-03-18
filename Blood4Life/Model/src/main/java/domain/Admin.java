package domain;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.*;
@Entity
@Table(name = "Admins")
public class Admin implements Serializable {

    private Long adminID;
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    public Long getAdminID() {
        return adminID;
    }

    public void setAdminID(Long adminID) {
        this.adminID = adminID;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String s) {
        this.username = s;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Admin() {
    }
}
