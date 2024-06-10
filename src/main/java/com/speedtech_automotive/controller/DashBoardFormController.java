package com.speedtech_automotive.controller;

import com.speedtech_automotive.db.DBConnection;
import com.speedtech_automotive.utils.Utils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
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
    public Pane mainPane;

    public void initialize() {
        Utils.setSecondPane(secondPane);
        generateRealTime();
        Utils.setUi("OrderForm");
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
        Utils.setUi("OrderForm");
    }

    public void loadReportsForm(ActionEvent actionEvent) {
        Utils.setUi("ReportForm");
    }

    public void loadDashBoardForm(ActionEvent actionEvent) {
        Utils.setUi("SubDashBoardForm");
    }

    public void loadInventoryForm(ActionEvent actionEvent) {
        Utils.setUi("InventoryForm");
    }

    public void loadStockForm(ActionEvent actionEvent) {
        Utils.setUi("StockManageForm");
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml")));
        mainPane.getChildren().setAll(pane);     }

    public void loadSupplierForm(ActionEvent actionEvent) {
        Utils.setUi("BatchForm");
    }

    public void loadCustomerForm(ActionEvent actionEvent) { Utils.setUi("CustomerForm"); }

    public void CustomerReportOnClick(ActionEvent actionEvent) {
        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Customer.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void orderReportOnClick(ActionEvent actionEvent) {
        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/orders.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
