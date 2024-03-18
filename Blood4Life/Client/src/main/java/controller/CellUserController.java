package controller;

import domain.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellUserController implements Initializable {
    public Label userNameLabel;
    public Label userCNPLabel;
    public Label patientEmailLabel;
    public Label userBloodTypeLabel;
    public Label userRhLabel;
    public Label patientBirthdayLabel;
    public Label patientGenderLabel;
    public Label patientHeightLabel;
    public Label patientWeightLabel;
    public Label patientPointsLabel;

    public void setUser(User user) {
        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        userCNPLabel.setText(user.getCnp());
        patientBirthdayLabel.setText(user.getBirthDate().toString());
        patientEmailLabel.setText(user.getEmail());
        patientGenderLabel.setText(user.getGender().toString());
        userBloodTypeLabel.setText(user.getBloodType().toString());
        userRhLabel.setText(user.getRh().toString());
        patientHeightLabel.setText(user.getHeight().toString());
        patientWeightLabel.setText(user.getWeight().toString());
        patientPointsLabel.setText(user.getPoints().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
