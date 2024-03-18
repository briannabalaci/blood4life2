package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainPageController implements Initializable {
    public BorderPane mainPageBorderPane;
    public Button showProfileButton;
    public Button createAppointmentButton;
    
    private ServiceInterface service;
    private Stage stage;

    private User currentUser;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.currentUser = user;
        try {
            loadProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onShowProfileButtonClick(ActionEvent actionEvent) throws IOException {
        loadProfile();
    }

    private void loadProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../userProfilePage-view.fxml"));
        Pane view = fxmlLoader.load();
        UserProfileController userProfileController = fxmlLoader.getController();
        userProfileController.setService(service, currentUser);
        mainPageBorderPane.setCenter(view);
    }

    public void onCreateAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("addAppointment-view.fxml"));
        Pane pane = fxmlLoader.load();
        AddAppointmentController addAppointmentController = fxmlLoader.getController();
        addAppointmentController.setUser(currentUser);
        addAppointmentController.setService(service);
        mainPageBorderPane.setCenter(pane);
    }

    public void onShowFutureAppointmentsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showUpcomingAppointments-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowFutureAppointmentsController showFutureAppointmentsController = fxmlLoader.getController();
        showFutureAppointmentsController.setService(service, currentUser);
        mainPageBorderPane.setCenter(view);
    }

    public void onShowPastAppointmentsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showPreviousAppointments-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowPreviousAppointmentsController showPreviousAppointmentsController = fxmlLoader.getController();
        showPreviousAppointmentsController.setService(service, currentUser);
        mainPageBorderPane.setCenter(view);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(stage);
        stage.close();
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
