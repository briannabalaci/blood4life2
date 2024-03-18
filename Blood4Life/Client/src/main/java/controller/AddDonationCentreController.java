package controller;

import exception.DatabaseException;
import exception.ServerException;
import exception.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.ServiceInterface;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddDonationCentreController implements Initializable {
    public TextArea errorsTextArea;
    public TextField nameTextField;
    public TextField maximumCapacityTextField;
    public TextField countyTextField;
    public TextField cityTextField;
    public TextField streetTextField;
    public TextField numberTextField;
    public Button submitAddDonationCentreButton;
    public ComboBox<String> openHourHourComboBox;
    public ComboBox<String> openHourMinuteComboBox;
    public ComboBox<String> closeHourHourComboBox;
    public ComboBox<String> closeHourMinuteComboBox;
    private ServiceInterface service;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    private void setHours() {
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                openHourHourComboBox.getItems().add("0" + i);
                closeHourHourComboBox.getItems().add("0" + i);
            }
            else {
                openHourHourComboBox.getItems().add(String.valueOf(i));
                closeHourHourComboBox.getItems().add(String.valueOf(i));
            }
        }
    }

    private void setMinutes() {
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                openHourMinuteComboBox.getItems().add("0" + i);
                closeHourMinuteComboBox.getItems().add("0" + i);
            }
            else {
                openHourMinuteComboBox.getItems().add(String.valueOf(i));
                closeHourMinuteComboBox.getItems().add(String.valueOf(i));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHours();
        setMinutes();
        errorsTextArea.setVisible(false);
        errorsTextArea.setEditable(false);
    }

    public void onAddDonationCentreButtonClick(ActionEvent actionEvent) {
        try {
            int maximumCapacity = Integer.parseInt(maximumCapacityTextField.getText());
            try {
                int number = Integer.parseInt(numberTextField.getText());
                try {
                    int openHourHour = Integer.parseInt(openHourHourComboBox.getValue());
                    int openHourMinute = Integer.parseInt(openHourMinuteComboBox.getValue());
                    int closeHourHour = Integer.parseInt(closeHourHourComboBox.getValue());
                    int closeHourMinute = Integer.parseInt(closeHourMinuteComboBox.getValue());
                    try {
                        service.addDonationCentre(countyTextField.getText(), cityTextField.getText(), streetTextField.getText(),
                                number, nameTextField.getText(), maximumCapacity,
                                LocalTime.of(openHourHour, openHourMinute),
                                LocalTime.of(closeHourHour, closeHourMinute));
                        errorsTextArea.setVisible(true);
                        errorsTextArea.setText("Donation centre added successfully!");
                    } catch (ValidationException | ServerException | DatabaseException validationException) {
                        errorsTextArea.setVisible(true);
                        errorsTextArea.setText(validationException.getMessage());
                    }
                } catch (NumberFormatException numberFormatException) {
                    errorsTextArea.setVisible(true);
                    errorsTextArea.setText("Hours must be selected in combo boxes");
                }
            } catch (NumberFormatException numberFormatException) {
                errorsTextArea.setVisible(true);
                errorsTextArea.setText("Number must be a number");
            }
        } catch (NumberFormatException numberFormatException) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Maximum capacity must be a number");
        }
    }
}
