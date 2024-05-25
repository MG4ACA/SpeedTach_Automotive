package com.speedtech_automotive.controller;

import javafx.collections.FXCollections;
import javafx.scene.chart.*;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SubDashBoardFormController {
    public CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList("Month"));
    public NumberAxis yAxis =new NumberAxis();
    public BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    SubDashBoardController controller = new SubDashBoardController();
    public void initialize(){
        xAxis.setLabel("Month");
        yAxis.setLabel("Total Sale Amount");


        XYChart.Series series = new XYChart.Series();
        series.setName("Monthly Sales");

        try (ResultSet resultSet = controller.getAllByMonth()) {
            while (resultSet.next()) {
                String month = getMonthName(resultSet.getInt("month"));
                double totalSale = resultSet.getDouble("total_sale");
                series.getData().add(new XYChart.Data<>(month, totalSale));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        barChart.getData().add(series);

    }

    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }


}
