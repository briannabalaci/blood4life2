package controller;

import domain.DonationCentre;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellDonationCentreController implements Initializable {
    public Label donationCentreNameLabel;
    public Label donationCentreOpenHourLabel;
    public Label donationCentreCloseHourLabel;
    public Label addressCountyLabel;
    public Label addressCityLabel;
    public Label addressStreetLabel;
    public Label addressNumberLabel;
    public Label donationCentreMaximumCapacityLabel;

    public void setDonationCentre(DonationCentre donationCentre) {
        donationCentreNameLabel.setText(donationCentre.getName());
        donationCentreOpenHourLabel.setText(donationCentre.getOpenHour().toString());
        donationCentreCloseHourLabel.setText(donationCentre.getCloseHour().toString());
        donationCentreMaximumCapacityLabel.setText(String.valueOf(donationCentre.getMaximumCapacity()));
        addressCountyLabel.setText(donationCentre.getAddress().getCounty());
        addressCityLabel.setText(donationCentre.getAddress().getCity());
        addressStreetLabel.setText(donationCentre.getAddress().getStreet());
        addressNumberLabel.setText(String.valueOf(donationCentre.getAddress().getNumber()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
