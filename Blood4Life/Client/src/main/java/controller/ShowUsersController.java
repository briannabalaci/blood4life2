package controller;

import domain.Appointment;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowUsersController implements Initializable {
    public AnchorPane root;
    public Pagination pagination;
    private ServiceInterface service;
    private List<User> users;

    public void setService(ServiceInterface service) {
        this.service = service;
        getUsers();
    }

    private void getUsers() {
        users = service.findAllUsers();
        root.getChildren().remove(pagination);
        int pageSize = 4;
        int usersNumber = users.size();
        int pagesNumber = usersNumber % pageSize != 0 ? (usersNumber/pageSize + 1) : usersNumber/pageSize;
        if(pagesNumber == 0){
            Label label = new Label("No users to show");
            label.setLayoutX(250);
            label.setLayoutY(150);
            label.setFont(Font.font("Arial"));
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
            root.getChildren().add(label);
        }
        else{
            pagination = new Pagination(pagesNumber, 0);
            pagination.setLayoutX(40.0);
            pagination.setLayoutY(20.0);
            pagination.setPrefWidth(760);
            pagination.setPrefHeight(540);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    if (pageIndex >= pagesNumber) {
                        return null;
                    } else {
                        return createPage(pageIndex);
                    }
                }
            });
            root.getChildren().add(pagination);
        }
    }

    private GridPane createPage(Integer pageIndex){
        GridPane pane = new GridPane();
        pane.setPrefWidth(740);
        pane.setPrefHeight(530);

        List<User> currentUsers = users.stream()
                .skip(pageIndex  * 4)
                .limit(4)
                .collect(Collectors.toList());

        for (int i = 0; i < currentUsers.size(); i++) {
            User user = currentUsers.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellUser-view.fxml"));
            try {
                Pane view = fxmlLoader.load();
                CellUserController cellUserController = fxmlLoader.getController();
                cellUserController.setUser(user);
                pane.add(view, 2 * (i / 2), i % 2);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
        return pane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
