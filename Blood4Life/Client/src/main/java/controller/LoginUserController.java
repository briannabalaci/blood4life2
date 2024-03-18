package controller;

import domain.User;
import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class LoginUserController {
    public TextField usernameTextField;
    public TextField cnpTextField;
    public Label messageLabel;

    private ServiceInterface service;
    private Stage stage;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onUserLoginButtonClick(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String cnp = cnpTextField.getText();
            User user = service.loginUser(username, cnp);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("userMainPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 860, 600);
            UserMainPageController userMainPageController = fxmlLoader.getController();
            userMainPageController.setService(service);
            userMainPageController.setUser(user);
            userMainPageController.setStage(stage);
            stage.close();
            stage.setTitle("Blood4Life");
            stage.setScene(scene);
            stage.show();
        } catch (ServerException | IOException exception) {
           messageLabel.setText(exception.getMessage());
        }
    }

    public void onSignUpButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("signupUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),660, 500);
        SignupUserController signupUserController = fxmlLoader.getController();
        signupUserController.setController(service, stage);
        stage.getIcons().add(new Image("icons/stage-picture.png"));
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }

    public void onLoginAsAdminButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginAdmin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginAdminController loginAdminController = fxmlLoader.getController();
        loginAdminController.setService(service);
        loginAdminController.setStage(stage);
        stage.getIcons().add(new Image("icons/stage-picture.png"));
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
