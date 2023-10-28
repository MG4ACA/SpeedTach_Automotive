package com.speedtech_automotive.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.speedtech_automotive.utils.Utils.setUi;

public class DashBoardFormController {

    public Pane paneTimer;
    public Pane secondPane;
    public Label lblDate;
    public Label lblTimeHour;
    public Label lblTimeSec;
    public Label lblTimeMin;
    public Pane mainPane;

    public void initialize() {
        generateRealTime();
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

    public void loadOrderForm(ActionEvent actionEvent) {
        setUi("OrderForm", secondPane);
    }

    public void loadReportsForm(ActionEvent actionEvent) {
        setUi("ReportForm",secondPane);
    }

    public void loadDashBoardForm(ActionEvent actionEvent) {
        setUi("SubDashBoardForm",secondPane);
    }

    public void loadInventoryForm(ActionEvent actionEvent) {
        setUi("InventoryForm", secondPane);
    }

    public void loadStockForm(ActionEvent actionEvent) {
        setUi("StockManageForm", secondPane);
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml")));
        mainPane.getChildren().setAll(pane);     }

    public void loadSupplierForm(ActionEvent actionEvent) {
        setUi("BatchForm", secondPane);
    }
}
