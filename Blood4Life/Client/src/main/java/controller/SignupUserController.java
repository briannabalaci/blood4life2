package controller;

import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.ServiceInterface;
import validator.UserValidator;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SignupUserController implements Initializable {

    public ComboBox<Gender> genderComboBox;
    public ComboBox<BloodType> bloodTypeComboBox;
    public ComboBox<Rh> rhComboBox;
    public TextField cnpTextField;
    public TextField emailTextField;
    public DatePicker birthDatePicker;
    public TextField weightTextField;
    public TextField heightTextField;
    public TextField lastNameTextField;
    public TextField firstNameTextField;
    public Label firstNameErrorLabel;
    public Label lastNameErrorLabel;
    public Label cnpErrorLabel;
    public Label emailErrorLabel;
    public Label heightErrorLabel;
    public Label weightErrorLabel;
    public Label signUpLabel;
    public Label rhErrorLabel;
    public Label bloodTypeErrorLabel;
    public Label genderErrorLabel;
    public Label birthDateErrorLabel;
    public TextArea errorTextArea;

    private ServiceInterface service;
    private Stage root;
    private UserValidator userValidator;

    public void setController(ServiceInterface service, Stage stage) {
        this.service = service;
        this.root = stage;
        userValidator = new UserValidator();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBloodTypes();
        setRhs();
        setGender();
        setVisibilityToFalse();
    }

    private void setVisibilityToFalse(){
        firstNameErrorLabel.setVisible(false);
        lastNameErrorLabel.setVisible(false);
        cnpErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        heightErrorLabel.setVisible(false);
        weightErrorLabel.setVisible(false);
        genderErrorLabel.setVisible(false);
        rhErrorLabel.setVisible(false);
        bloodTypeErrorLabel.setVisible(false);
        birthDateErrorLabel.setVisible(false);
        errorTextArea.setVisible(false);
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

    private void setGender() {
        ObservableList<Gender> genders = FXCollections.observableArrayList();
        genders.addAll(Arrays.asList(Gender.values()));
        genderComboBox.setItems(genders);
    }

    public void onSignUpUserButtonClick(ActionEvent actionEvent) throws IOException {
        setVisibilityToFalse();
        int countErrors = 0;
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String cnp = cnpTextField.getText();


        BloodType bloodType = bloodTypeComboBox.getValue();
        if(bloodType == null){
            bloodTypeErrorLabel.setText("Invalid blood type!");
            bloodTypeErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        Rh rh = rhComboBox.getValue();
        if(rh == null){
            rhErrorLabel.setText("Invalid Rh!");
            rhErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        Gender gender = genderComboBox.getValue();
        if(gender == null){
            genderErrorLabel.setText("Invalid gender!");
            genderErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        Double weight = null;
        try{
            weight = Double.valueOf(weightTextField.getText());
            if(!userValidator.validateWeight(weight)){
                weightErrorLabel.setText("Invalid weight!");
                weightErrorLabel.setVisible(true);
                countErrors = countErrors + 1;
            }
        }catch (NumberFormatException e){
            weightErrorLabel.setText("Invalid weight!");
            weightErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        Integer height = null;
        try{
            height = Integer.valueOf(heightTextField.getText());
            if(!userValidator.validateHeight(height)){
                heightErrorLabel.setText("Invalid height!");
                heightErrorLabel.setVisible(true);
                countErrors = countErrors + 1;
            }
        }catch (NumberFormatException e){
            heightErrorLabel.setText("Invalid height!");
            heightErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        if(!userValidator.validateName(firstName)) {
            firstNameErrorLabel.setText("Invalid first name!");
            firstNameErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        if(!userValidator.validateName(lastName)){
            lastNameErrorLabel.setText("Invalid last name!");
            lastNameErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        boolean okCNP = true;
        if(!userValidator.validateCNP(cnp)){
            okCNP = false;
            cnpErrorLabel.setText("Invalid cnp!");
            cnpErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        if(!userValidator.validateEmail(email)){
            emailErrorLabel.setText("Invalid email!");
            emailErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        LocalDate birthdate = birthDatePicker.getValue();
        boolean okDate = true;
        if(birthdate == null || birthdate.isAfter(LocalDate.now())){
            okDate = false;
            birthDateErrorLabel.setText("Invalid birth date!");
            birthDateErrorLabel.setVisible(true);
            countErrors = countErrors + 1;
        }

        if(okCNP && okDate){
            if(!userValidator.validateBirthDateCNP(birthdate, cnp)){
                cnpErrorLabel.setText("Invalid cnp + birthdate\n combination!");
                cnpErrorLabel.setVisible(true);
                birthDateErrorLabel.setText("Invalid cnp + birthdate\n combination!");
                birthDateErrorLabel.setVisible(true);
                countErrors = countErrors + 1;
            }
        }



        if(countErrors == 0) {
            service.addUser(firstName, lastName, email, cnp, birthdate, gender, bloodType, rh, weight, height);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 660, 500);
            LoginUserController loginUserController = fxmlLoader.getController();
            loginUserController.setService(service);
            loginUserController.setStage(root);
            root.setTitle("Blood4Life");
            root.setScene(scene);
            root.show();
        }
        else {
            errorTextArea.setVisible(true);
            errorTextArea.setText("Verify your information and retry!");
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(root);
        root.setTitle("Blood4Life");
        root.setScene(scene);
        root.show();
    }
}
