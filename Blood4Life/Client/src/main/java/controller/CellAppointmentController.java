package controller;

import domain.Appointment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellAppointmentController implements Initializable {
    public Label patientCNPLabel;
    public Label donationCentreNameLabel;
    public Label userCNPLabel;
    public Label appointmentDateLabel;
    public Label appointmentTimeLabel;

    public void setAppointment(Appointment appointment) {
        patientCNPLabel.setText(appointment.getPatient().getCnp());
        userCNPLabel.setText(appointment.getUser().getCnp());
        donationCentreNameLabel.setText(appointment.getDonationCentre().getName());
        appointmentDateLabel.setText(appointment.getDate().toString());
        appointmentTimeLabel.setText(appointment.getTime().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
