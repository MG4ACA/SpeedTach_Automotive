package com.speedtech_automotive.controller;

import com.speedtech_automotive.tm.OrderDetailTm;
import com.speedtech_automotive.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class OrderDetailsFormController {
    public TableColumn colProductName;
    public TableColumn colProductCode;
    public TableColumn colSellingPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colProductTotal;
    public TableView tblOrderedProductList;

    public void initialize() {
//        try {
//            joinList = stockController.getStockProductJoin();
//            mapProductList();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
//        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
//        colProductTotal.setCellValueFactory(new PropertyValueFactory<>("productTotal"));
//        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblOrderedProductList.getColumns().get(6);
//
//        lastCol.setCellValueFactory(param -> {
//            Button btnDelete = new Button("Delete");
//            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");
//
//            btnDelete.setOnAction(event -> {
//                deleteOrderTable(param.getValue());
//                tblOrderedProductList.getSelectionModel().clearSelection();
//            });
//            return new ReadOnlyObjectWrapper<>(btnDelete);
//        });
//        clearFieldsOnClick();
//        loadOrderDetailsTable();
//
//        tblOrderedProductList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
//            if (selectedOrderDetail != null) {
//                isOrderTableUpdate = true;
//                btnAddUpdateOrder.setText("Update Order");
//                setOrderDetailsUpdateData((OrderDetailTm) selectedOrderDetail);
//            }
//        });

    }

    public void onListViewMouseExited(MouseEvent mouseEvent) {
    }

    public void clearAllFields(ActionEvent actionEvent) {
    }

    public void onBackToOrderForm(ActionEvent actionEvent) {
        Utils.setUi("OrderForm");
    }

    public void onSearchCustomerKeyType(KeyEvent keyEvent) {
    }

    public void clearSearchCustomer(ActionEvent actionEvent) {
    }

    public void onFilterByDate(ActionEvent actionEvent) {
    }

    public void validateQuantity(KeyEvent keyEvent) {
    }

    public void addOrderDetailToTable(ActionEvent actionEvent) {
    }

    public void clearFieldsOnClick(ActionEvent actionEvent) {
    }

    public void onCustomerSearch(MouseEvent mouseEvent) {

    }

    public void placeOrder(ActionEvent actionEvent) {
    }

    public void onListViewMouseClick(MouseEvent mouseEvent) {
    }
}
