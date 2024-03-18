package controller;

import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginAdminController {
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label errorLabel;

    private ServiceInterface service;
    private Stage stage;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onAdminLoginButtonClick(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            service.loginAdmin(username, encodePassword(password));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("adminMainPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 860, 600);
            AdminMainPageController adminMainPageController = fxmlLoader.getController();
            adminMainPageController.setService(service);
            adminMainPageController.setStage(stage);
            stage.close();
            stage.setTitle("Blood4Life");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | ServerException exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(stage);
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }

    private String encodePassword(String password) {
        String encryptedPassword = null;
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
}
