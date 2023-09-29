package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Timer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DashBoardFormController {

    public Pane paneTimer;
    public Pane secondPane;
    public Label lblDate;
    public Label lblTimeHour;
    public Label lblTimeSec;
    public Label lblTimeMin;

    public void initialize() throws IOException {
       // Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/DashBoardForm.fxml")));
//        mainPane.getChildren().setAll(pane);
//        new Timer().initialize();
        generateRealTime();


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

    /*-----DATE AND TIME GENERATE------*/
    private void generateRealTime() {
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatterSec = DateTimeFormatter.ofPattern("ss");
            lblTimeSec.setText(LocalDateTime.now().format(formatterSec));

            DateTimeFormatter formatterMin = DateTimeFormatter.ofPattern("mm");
            lblTimeMin.setText(LocalDateTime.now().format(formatterMin));

            DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("hh");
            lblTimeHour.setText(LocalDateTime.now().format(formatterHour));

        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
