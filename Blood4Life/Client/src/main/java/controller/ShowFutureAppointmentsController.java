package controller;

import domain.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowFutureAppointmentsController implements Initializable {
    public Pagination pagination;
    public AnchorPane root;

    private ServiceInterface service;
    private User loggedUser;
    private final int pageSize = 4;

    public void setService(ServiceInterface service, User loggedUser) {
        this.loggedUser = loggedUser;
        this.service = service;
        getAppointments();
    }

    private void getAppointments() {
        root.getChildren().remove(pagination);
        int appointmentsNumber = service.countFutureAppointmentsByUser(loggedUser);

        int pagesNumber = appointmentsNumber % pageSize != 0 ? (appointmentsNumber/pageSize + 1) : appointmentsNumber/pageSize;
        if(pagesNumber == 0){
            Label label = new Label("No appointments to show");
            label.setLayoutX(250);
            label.setLayoutY(150);
            label.setFont(Font.font("Arial"));
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
            root.getChildren().add(label);
        }
        else{
            pagination = new Pagination(pagesNumber, 0);
            pagination.setLayoutX(40.0);
            pagination.setLayoutY(20.0);
            pagination.setPrefWidth(760);
            pagination.setPrefHeight(540);
            pagination.setPageFactory(pageIndex -> {
                if (pageIndex >= pagesNumber) {
                    return null;
                } else {
                    return createPage(pageIndex);
                }
            });
            root.getChildren().add(pagination);
        }
    }

    private GridPane createPage(Integer pageIndex){
        GridPane pane = new GridPane();
        pane.setPrefWidth(740);
        pane.setPrefHeight(530);
        pane.setStyle("-fx-background-color: #EEEBDD");

        List<Appointment> appointmentsToShow = service.findFutureAppointmentsByUser(loggedUser, pageIndex * pageSize, pageSize);

        for (int i = 0; i < appointmentsToShow.size(); i++) {
            Appointment appointment = appointmentsToShow.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellFutureAppointment-view.fxml"));
            try {
                Pane view = fxmlLoader.load();
                CellFutureAppointmentController cellFutureAppointmentController = fxmlLoader.getController();
                cellFutureAppointmentController.setAppointment(appointment, service);
                pane.add(view, 2 * (i / 2), i % 2);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
        return pane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
