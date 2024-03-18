package controller;

import domain.User;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import service.ServiceInterface;

public class UserProfileController {

    public Label cnpCompleted;
    public Label dateCompleted;
    public Label emailCompleted;
    public Label genderCompleted;
    public Label rhCompleted;
    public Label bloodTypeCompleted;
    public Label heightCompleted;
    public Label weightCompleted;
    public Label nameCompleted;
    public Label patientsNumber;
    public Label vouchersNumber;
    public ProgressBar pointsBar;
    private ServiceInterface service;
    private User loggedUser;

    public void setService(ServiceInterface service, User loggedUser) {
        this.service = service;
        this.loggedUser = loggedUser;
        setProfilePage();
    }

    private void setProfilePage(){
        nameCompleted.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
        emailCompleted.setText(loggedUser.getEmail());
        cnpCompleted.setText(loggedUser.getCnp());
        dateCompleted.setText(loggedUser.getBirthDate().toString());
        weightCompleted.setText(loggedUser.getWeight().toString() + " kg");
        heightCompleted.setText(loggedUser.getHeight().toString() + " cm");
        rhCompleted.setText(loggedUser.getRh().toString());
        bloodTypeCompleted.setText(loggedUser.getBloodType().toString());
        genderCompleted.setText(loggedUser.getGender().toString());

        long points = loggedUser.getPoints();
        int patientsNo = (int) (points / 100);
        patientsNumber.setText(String.valueOf(patientsNo));
        int vouchersNo = (int) (points / 300);
        long leftPoints = points % 300;
        vouchersNumber.setText(String.valueOf(vouchersNo));
        double percentage = ((double) 100 * (double) leftPoints) / (double) 300;
        pointsBar.setProgress(percentage / (double) 100);

    }
}
