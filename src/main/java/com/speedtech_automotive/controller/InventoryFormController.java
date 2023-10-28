package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InventoryFormController {
    public Product selectedProduct;
    public TextField txtSearch;
    public TextField txtCode;
    public TextField txtDescription;
    public TableView tblProductDetails;
    public TextField txtName;
    public DatePicker datePicker;
    private final  InventoryController inventoryController = new InventoryController();
    public TableColumn colProductCode;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colDescription;
    public ListView<String> lvSearch;
    ArrayList<Product> allProducts;

    public void initialize(){
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Product, Button> lastCol = (TableColumn<Product, Button>) tblProductDetails.getColumns().get(4);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                DeleteProduct(param.getValue());
                tblProductDetails.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        loadProductsTable();

        tblProductDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedProductDetail) -> {
            if (selectedProductDetail != null) {
                SetUpdateData((Product) selectedProductDetail);
            }
        });
    }

    private void SetUpdateData(Product dto) {
        selectedProduct = dto;
        txtCode.setText(dto.getCode());
        txtName.setText(dto.getName());
        txtDescription.setText(dto.getDescription());
        datePicker.setValue(dto.getAddedDate().toLocalDate());
        txtSearch.clear();
    }

    private void DeleteProduct(Product param) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't Delete product "+param.getCode()+" ..!",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getText().equals("Yes")) {
            try {
                if (inventoryController.deleteProductById(param.getProduct_id())) {
                    new Alert(Alert.AlertType.INFORMATION, "Delete"+param.getCode()+" ..!").show();
                    loadProductsTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveProductOnClick(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        Date addedDate = Date.valueOf(datePicker.getValue());

        try {
            if (selectedProduct == null){
                boolean isSaved = inventoryController.saveProduct(new Product(code, name, description, addedDate));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Product added successfully..!").show();
                    clearInputFields();
                    loadProductsTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                selectedProduct.setCode(txtCode.getText());
                selectedProduct.setName(txtName.getText());
                selectedProduct.setAddedDate(Date.valueOf(datePicker.getValue()));
                selectedProduct.setDescription(txtDescription.getText());

                boolean isSaved = inventoryController.updateProduct(selectedProduct);
                selectedProduct = null;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Product updated successfully..!").show();
                    loadProductsTable();
                    clearInputFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearInputFields() {
        txtCode.clear();
        txtName.clear();
        txtDescription.clear();
        datePicker.setValue(null);
        txtSearch.clear();
    }

    private void loadProductsTable() {
        tblProductDetails.getItems().clear();
        try {
            allProducts = inventoryController.getAll();
            for (Product dto:allProducts) {
                tblProductDetails.getItems().add(
                        new Product(
                                dto.getProduct_id(),
                                dto.getName(),
                                dto.getCode(),
                                dto.getDescription(),
                                dto.getAddedDate()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onProductSearch(KeyEvent keyEvent) {
        ArrayList<Product> tempAllProducts = allProducts;
        ArrayList<String> productArray = new ArrayList<>();
            for (Product product : tempAllProducts) {
                if (product.getName().toLowerCase().contains(txtSearch.getText().toLowerCase()) || product.getCode().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
                    productArray.add(product.getProduct_id() +" - "+product.getCode()+" - "+product.getName());
                }
            }
            lvSearch.getItems().clear();
            lvSearch.getItems().addAll(productArray);
            productArray.clear();
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        lvSearch.setVisible(true);

        ArrayList<String> productArray = new ArrayList<>();
        for (Product product : allProducts) {
                productArray.add(product.getProduct_id() +" - "+ product.getCode()+" - "+product.getName());
            }
        lvSearch.getItems().clear();
        lvSearch.getItems().addAll(productArray);
    }

    public void onProductSearchClicked(MouseEvent mouseEvent) {
        String[] array = lvSearch.getSelectionModel().getSelectedItem().split("-");
        for (Product product: allProducts) {
            if (product.getProduct_id().equals(array[0].trim())){
                SetUpdateData(product);
                break;
            }
        }
        lvSearch.setVisible(false);
    }

}
