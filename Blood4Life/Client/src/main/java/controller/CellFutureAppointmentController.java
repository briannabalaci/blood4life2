package controller;

import domain.Appointment;
import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ServiceInterface;

import java.net.URL;
import java.util.ResourceBundle;

public class CellFutureAppointmentController implements Initializable {
    public Label patientCNPLabel;
    public Label donationCentreNameLabel;
    public Label userCNPLabel;
    public Label appointmentDateLabel;
    public Label appointmentTimeLabel;
    public Button cancelButton;

    private ServiceInterface service;
    private Appointment appointment;

    public void setAppointment(Appointment appointment, ServiceInterface service) {
        patientCNPLabel.setText(appointment.getPatient().getCnp());
        userCNPLabel.setText(appointment.getUser().getCnp());
        donationCentreNameLabel.setText(appointment.getDonationCentre().getName());
        appointmentDateLabel.setText(appointment.getDate().toString());
        appointmentTimeLabel.setText(appointment.getTime().toString());
        this.appointment = appointment;
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCancelAppointmentButtonClick(ActionEvent actionEvent) {
        try {
            service.cancelAppointment(appointment);
            cancelButton.setText("Canceled");
            cancelButton.setDisable(true);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
