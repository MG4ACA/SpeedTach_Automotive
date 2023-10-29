package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Stock;
import com.speedtech_automotive.model.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StockFormController {
    public Stock selectedStock;
    private final  StockController stockController = new StockController();

    public TextField txtSearchSupplier;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TableView tblStockManage;
    public TableColumn colCompanyName;
    public TableColumn colProductName;
    public TableColumn colPrice;
    public TableColumn colQuantity;
    public TableColumn colDate;
    public TableColumn colProductCode1;
    public Text lblSupplierName;
    public Text lblCompanyName;
    public TextField txtSearchProduct;
    public Text lblProductName;
    public Text lblProductCode;

    Product product;
    Supplier supplier;

    public void initialize(){
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        TableColumn<Stock, Button> lastCol = (TableColumn<Stock, Button>) tblStockManage.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                DeleteStock(param.getValue());
                tblStockManage.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
//        loadSuppliersTable();

        tblStockManage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedSupplierDetail) -> {
            if (selectedSupplierDetail != null) {
                SetUpdateData((Stock) selectedSupplierDetail);
            }
        });
    }

    private void SetUpdateData(Stock dto) {
        selectedStock = dto;
//        txt.setText(dto.getCompanyName());
//        txtSupplierName.setText(dto.getSupplierName());
//        txtContact.setText(String.valueOf(dto.getContact()));
//        txtDescription.setText(dto.getDescription());
    }

    private void DeleteStock(Stock param) {
//        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't Delete supplier "+param.getCompanyName()+" ..!", ButtonType.YES,ButtonType.NO);
//        Optional<ButtonType> result = alert.showAndWait();
//
//        if (result.get().getText().equals("Yes")) {
//            try {
//                if (batchController.deleteSupplierById(param.getBatch_id())) {
//                    new Alert(Alert.AlertType.INFORMATION, "Delete"+param.getCompanyName()+" ..!").show();
//                    loadSuppliersTable();
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
//                }
//            } catch (SQLException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    public void saveStockOnClick(ActionEvent actionEvent) {
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));
        int qty = Integer.parseInt(txtQuantity.getText());
        Date addedDate = Date.valueOf(LocalDate.now());


        try {
            if (selectedStock == null){
                boolean isSaved = stockController.saveStock(new Stock(product, supplier, price, qty, addedDate, true));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock added successfully..!").show();
                    clearFieldsOnClick();
//                    loadSuppliersTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                selectedStock.setProduct(product);
                selectedStock.setSupplier(supplier);
                selectedStock.setPrice(BigDecimal.valueOf(Integer.parseInt(txtPrice.getText())));
                selectedStock.setQuantity(Integer.parseInt(txtQuantity.getText()));

                boolean isSaved = stockController.updateStock(selectedStock);
                selectedStock = null;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock updated successfully..!").show();
//                    loadSuppliersTable();
                    clearFieldsOnClick();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearFieldsOnClick() {
        txtQuantity.clear();
        txtPrice.clear();
        txtSearchProduct.clear();
        txtSearchProduct.clear();
        lblCompanyName.setText("");
        lblProductCode.setText("");
        lblProductName.setText("");
        lblSupplierName.setText("");
    }
//
//    private void loadSuppliersTable() {
//        tblStockManage.getItems().clear();
//        try {
//            ArrayList<Stock> all = batchController.getAll();
//            for (Stock dto:all) {
//                tblStockManage.getItems().add(
//                        new Stock(
//                                dto.getBatch_id(),
//                                dto.getSupplierName(),
//                                dto.getCompanyName(),
//                                dto.getContact(),
//                                dto.getDescription(),
//                                dto.getAddedDate()
//                        ));
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public void onSearchSupplier(KeyEvent keyEvent) {
        System.out.println("search suppler");
    }

    public void onSearchProduct(KeyEvent keyEvent) {
        System.out.println("search product");

    }
}
