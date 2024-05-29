package com.speedtech_automotive.controller;

import javafx.collections.FXCollections;
import javafx.scene.chart.*;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SubDashBoardFormController {
    public CategoryAxis xAxis = new CategoryAxis();
    public NumberAxis yAxis = new NumberAxis();
    public BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    public CategoryAxis productXAxis = new CategoryAxis();
    public NumberAxis productYAxis = new NumberAxis();
    public BarChart<String, Number>  productSaleBarChart = new BarChart<>(productXAxis, productYAxis);

    public NumberAxis incomeYAxis = new NumberAxis();
    public CategoryAxis incomeXAxis = new CategoryAxis();
    public BarChart<String, Number> incomeBarChart = new BarChart<>(incomeXAxis, incomeYAxis);
    SubDashBoardController controller = new SubDashBoardController();
    public void initialize(){
//        xAxis.setLabel("Month");
        yAxis.setLabel("Total Sale Amount");
//        productXAxis.setLabel("Product");
        productYAxis.setLabel("Product Quantity");
//        incomeXAxis.setLabel("Income");
        incomeYAxis.setLabel("Product Quantity");



        XYChart.Series<String, Number> series = new XYChart.Series();
        XYChart.Series<String, Number> productSeries = new XYChart.Series();
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series();
        XYChart.Series<String, Number> expencesSeries = new XYChart.Series();
        series.setName("Monthly Sales");
        productSeries.setName("Product Sales");
        incomeSeries.setName("Monthly Income");
        expencesSeries.setName("Monthly Expenditure");

        try (ResultSet resultSet = controller.getAllByMonth()) {
            while (resultSet.next()) {
                String month = getMonthName(resultSet.getInt("month"));
                double totalSale = resultSet.getDouble("total_sale");
                series.getData().add(new XYChart.Data<>(month, totalSale));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ResultSet resultSet = controller.getProductsBySoldQty()) {
            while (resultSet.next()) {
                String product = resultSet.getString("productCode") + "\n" +resultSet.getString("productName");
                double qty = resultSet.getInt("total_quantity_sold");
                productSeries.getData().add(new XYChart.Data<>(product, qty));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ResultSet resultSet = controller.getOverAllIncome()) {
            while (resultSet.next()) {
                String month = getMonthName(resultSet.getInt("month"));
                double totalSale = resultSet.getDouble("total_income");
                double totalExpenses = resultSet.getDouble("total_expenses");
                incomeSeries.getData().add(new XYChart.Data<>(month, totalSale));
                expencesSeries.getData().add(new XYChart.Data<>(month, totalExpenses));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        barChart.getData().add(series);
        productSaleBarChart.getData().add(productSeries);
        incomeBarChart.getData().addAll(incomeSeries, expencesSeries);

    }

    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }


}
