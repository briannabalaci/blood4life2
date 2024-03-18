package controller;

import domain.Patient;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellPatientController implements Initializable {
    public Label patientNameLabel;
    public Label patientCNPLabel;
    public Label patientSeverityLabel;
    public Label patientBloodTypeLabel;
    public Label patientRhLabel;
    public Label patientBirthdayLabel;
    public Label patientBloodNeededLabel;

    public void setPatient(Patient patient) {
        patientNameLabel.setText(patient.getFirstName() + " " + patient.getLastName());
        patientCNPLabel.setText(patient.getCnp());
        patientBirthdayLabel.setText(patient.getBirthday().toString());
        patientSeverityLabel.setText(patient.getSeverity().toString());
        patientBloodTypeLabel.setText(patient.getBloodType().toString());
        patientRhLabel.setText(patient.getRh().toString());
        patientBloodNeededLabel.setText(patient.getBloodQuantityNeeded().toString() + "ml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
