package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class DashBoardFormController {

    public Pane paneTimer;
    public Pane secondPane;

    public void initialize() throws IOException {
       // Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/DashBoardForm.fxml")));
//        mainPane.getChildren().setAll(pane);
        new Timer().initialize();
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        setUi("");
    }

    public void btnInventryOnAction(ActionEvent actionEvent) {
        setUi("");
    }

    public void setUi(String url){
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/view/"+url+".fxml"));
            secondPane.getChildren().setAll(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
