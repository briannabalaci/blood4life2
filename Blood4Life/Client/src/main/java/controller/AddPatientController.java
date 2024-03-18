package controller;

import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import exception.ServerException;
import exception.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.ServiceInterface;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {
    public TextField cnpTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField bloodQuantityNeededTextField;
    public ComboBox<BloodType> bloodTypeComboBox;
    public ComboBox<Rh> rhComboBox;
    public ComboBox<Severity> severityComboBox;
    public DatePicker birthdayDatePicker;
    public Button submitAddPatientButton;
    public TextArea errorsTextArea;

    private ServiceInterface service;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    private void setBloodTypes() {
        ObservableList<BloodType> bloodTypes = FXCollections.observableArrayList();
        bloodTypes.addAll(Arrays.asList(BloodType.values()));
        bloodTypeComboBox.setItems(bloodTypes);
    }

    private void setRhs() {
        ObservableList<Rh> rhs = FXCollections.observableArrayList();
        rhs.addAll(Arrays.asList(Rh.values()));
        rhComboBox.setItems(rhs);
    }

    private void setSeverities() {
        ObservableList<Severity> severities = FXCollections.observableArrayList();
        severities.addAll(Arrays.asList(Severity.values()));
        severityComboBox.setItems(severities);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBloodTypes();
        setRhs();
        setSeverities();
        errorsTextArea.setVisible(false);
        errorsTextArea.setEditable(false);
    }

    public void onAddPatientButtonClick(ActionEvent actionEvent) {
        try {
            int bloodQuantityNeeded = Integer.parseInt(bloodQuantityNeededTextField.getText());
            try {
                service.addPatient(cnpTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(),
                        birthdayDatePicker.getValue(), bloodTypeComboBox.getValue(), rhComboBox.getValue(),
                        severityComboBox.getValue(), bloodQuantityNeeded);
                errorsTextArea.setVisible(true);
                errorsTextArea.setText("Patient added successfully!");
            } catch (ValidationException| ServerException validationException) {
                errorsTextArea.setVisible(true);
                errorsTextArea.setText(validationException.getMessage());
            }
        } catch (NumberFormatException numberFormatException) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Blood quantity must be a number");
        }
    }
}
