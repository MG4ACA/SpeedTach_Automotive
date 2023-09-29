package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Timer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class DashBoardFormController {

    public Pane paneTimer;

    public void initialize() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/DashBoardForm.fxml")));
//        mainPane.getChildren().setAll(pane);
        new Timer().initialize();
    }
}
