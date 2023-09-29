package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import com.speedtech_automotive.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginPageController {
    public TextField txtUserName;
    public TextField txtPassword;
    public Pane mainPane;


    public void onLoginBtnClick(ActionEvent actionEvent) throws RuntimeException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        Boolean isUserExist = false;

        if (!username.isEmpty()) {
            if (!password.isEmpty()) {
                ArrayList<User> AllUsers = new ArrayList<>();
                ResultSet resultSet = null;
                try {
                    resultSet = Utils.executeQuery("SELECT username, password FROM user");
                    while (resultSet.next()) {
                        AllUsers.add(
                                new User(
                                        resultSet.getString(1),
                                        resultSet.getString(2)
                                ));
                    }
                    for (User user : AllUsers) {
                        if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)) {
                            isUserExist = true;
                        }
                    }
                    if (isUserExist){
                        new Alert(Alert.AlertType.CONFIRMATION, "User login success..").show();
                        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/DashBoardForm.fxml")));
                        mainPane.getChildren().setAll(pane);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "No Users found..").show();
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    throw new RuntimeException(e);
                }

            } else {
                new Alert(Alert.AlertType.WARNING, "Enter password...").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter username...").show();
        }
    }
}
