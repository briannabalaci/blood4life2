package controller;

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

public class AdminMainPageController implements Initializable {
    public BorderPane mainPageBorderPane;
    public Button addPatientButton;
    public Button showPatientsButton;
    public Button showUsersButton;
    public Button showDonationCentresButton;
    public Button showAppointmentsButton;
    public Button addDonationCentreButton;

    private ServiceInterface service;
    private Stage stage;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onAddPatientButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../addPatient-view.fxml"));
        Pane view = fxmlLoader.load();
        AddPatientController addPatientController = fxmlLoader.getController();
        addPatientController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onShowPatientsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showPatients-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowPatientsController showPatientsController = fxmlLoader.getController();
        showPatientsController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onShowUsersButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showUsers-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowUsersController showUsersController = fxmlLoader.getController();
        showUsersController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onShowDonationCentresButtonnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showDonationCentres-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowDonationCentresController showDonationCentresController = fxmlLoader.getController();
        showDonationCentresController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onShowAppointmentsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../showAppointments-view.fxml"));
        Pane view = fxmlLoader.load();
        ShowAppointmentsController showAppointmentsController = fxmlLoader.getController();
        showAppointmentsController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onAddDonationCentreButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../addDonationCentre-view.fxml"));
        Pane view = fxmlLoader.load();
        AddDonationCentreController addDonationCentreController = fxmlLoader.getController();
        addDonationCentreController.setService(service);
        mainPageBorderPane.setCenter(view);
    }

    public void onLogoutAdminButtonClick(ActionEvent actionEvent) throws IOException {
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
