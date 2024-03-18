package controller;

import domain.Address;
import domain.DonationCentre;
import domain.Patient;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import exception.DatabaseException;
import exception.ServerException;
import exception.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.ServiceInterface;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    public TextArea errorsTextArea;
    public ComboBox<Patient> patientComboBox;
    public ComboBox<DonationCentre> centreComboBox;
    public DatePicker dayDatePicker;
    public ComboBox<String> hourComboBox;
    public Button appointmentButton;
    private ServiceInterface service;
    private User currentUser;

    public void setService(ServiceInterface service) {
        this.service = service;
        patientComboBox.getItems().addAll(service.findAllCompatiblePatients(currentUser.getBloodType(), currentUser.getRh()));
        centreComboBox.getItems().addAll(service.findAllDonationCentres());
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    private void setHours() {
        for (int i = 7; i <= 18; i++) {
            if (i < 10) {
                hourComboBox.getItems().add("0" + i);
            }
            else {
                hourComboBox.getItems().add(String.valueOf(i));
            }
        }
    }

    private void setHours(LocalTime open, LocalTime close) {
        Integer openHour = open.getHour();
        if(open.getMinute() != 0)
            openHour++;
        Integer closeHour = close.getHour();
        if(close.getMinute() != 0)
            closeHour --;
        for (Integer i = openHour; i < closeHour; i++) {
            if (i < 10) {
                hourComboBox.getItems().add("0" + i);
            }
            else {
                hourComboBox.getItems().add(String.valueOf(i));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHours();
        errorsTextArea.setVisible(false);
        errorsTextArea.setEditable(false);
        centreComboBox.valueProperty().addListener(new ChangeListener<DonationCentre>() {
            @Override
            public void changed(ObservableValue<? extends DonationCentre> observable, DonationCentre oldValue, DonationCentre newValue) {
                setHours(newValue.getOpenHour(), newValue.getCloseHour());
            }
        });
    }

    private Patient findMostUrgent() {
        return patientComboBox.getItems().stream().sorted(new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                return o1.getSeverity().compareTo(o2.getSeverity());
            }
        }).findFirst().get();
    }

    public void onCreateAppointmentButtonClick(ActionEvent actionEvent) {
        Patient patient = patientComboBox.getValue();
        DonationCentre centre = centreComboBox.getValue();
        if(centre == null) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Donation centre is a required field");
            return;
        }
        if(patient == null)
            try {
                patient = findMostUrgent();
            }
            catch(NoSuchElementException e){
                errorsTextArea.setVisible(true);
                errorsTextArea.setText("There are no patients at the moment");
                appointmentButton.setDisable(true);
                return;
            }
        LocalDate date = dayDatePicker.getValue();
        if(date == null) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Date is a required field");
            return;
        }
        if(date.isBefore(LocalDate.now())){
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Please select a future date");
            return;
        }
        int hour;
        try {
            hour = Integer.parseInt(hourComboBox.getValue());

            try {

            } catch (ValidationException | DatabaseException exception) {
                errorsTextArea.setVisible(true);
                errorsTextArea.setText(exception.getMessage());
                return;
            }
        } catch (NumberFormatException numberFormatException) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Time is a required field");
            return;
        }
        LocalTime time = LocalTime.of(hour, 0, 0, 0);
        Time time1 = Time.valueOf(time);
        try {
            System.out.println(Date.valueOf(date));
            service.addAppointment(currentUser, patient, centre, Date.valueOf(date), time1);
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("The appointment was created \nsuccessfully");
        } catch (ServerException exception) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText(exception.getMessage());
        }
    }
}
