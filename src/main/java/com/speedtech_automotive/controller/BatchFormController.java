package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class BatchFormController {
    public Supplier selectedSupplier;
    private final  BatchController batchController = new BatchController();
    public TextField txtSearch;
    public TextArea txtDescription;
    public TextField txtContact;
    public TextField txtSupplierName;
    public TextField txtCompanyName;
    public TableColumn colSupplierName;
    public TableColumn colCompanyName;
    public TableColumn colContact;
    public TableColumn colDescription;
    public TableView tblSupplierDetails;
    public TableColumn colDate;

    public void initialize(){
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Supplier, Button> lastCol = (TableColumn<Supplier, Button>) tblSupplierDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                DeleteSupplier(param.getValue());
                tblSupplierDetails.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        loadSuppliersTable();

        tblSupplierDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedSupplierDetail) -> {
            if (selectedSupplierDetail != null) {
                SetUpdateData((Supplier) selectedSupplierDetail);
            }
        });
    }

    private void SetUpdateData(Supplier dto) {
        selectedSupplier = dto;
        txtCompanyName.setText(dto.getCompanyName());
        txtSupplierName.setText(dto.getSupplierName());
        txtContact.setText(String.valueOf(dto.getContact()));
        txtDescription.setText(dto.getDescription());
    }

    private void DeleteSupplier(Supplier param) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't Delete supplier "+param.getCompanyName()+" ..!",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getText().equals("Yes")) {
            try {
                if (batchController.deleteSupplierById(param.getBatch_id())) {
                    new Alert(Alert.AlertType.INFORMATION, "Delete"+param.getCompanyName()+" ..!").show();
                    loadSuppliersTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveSupplierOnClick(ActionEvent actionEvent) {
        String supplierName = txtSupplierName.getText();
        String companyName = txtCompanyName.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String description = txtDescription.getText();
        Date addedDate = Date.valueOf(LocalDate.now());


        try {
            if (selectedSupplier == null){
                boolean isSaved = batchController.saveSupplier(new Supplier(supplierName, companyName, contact, description,  addedDate));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier added successfully..!").show();
                    clearInputFields();
                    loadSuppliersTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                selectedSupplier.setSupplierName(txtSupplierName.getText());
                selectedSupplier.setCompanyName(txtCompanyName.getText());
                selectedSupplier.setContact(Integer.parseInt(txtContact.getText()));
                selectedSupplier.setDescription(txtDescription.getText());

                boolean isSaved = batchController.updateSupplier(selectedSupplier);
                selectedSupplier = null;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully..!").show();
                    loadSuppliersTable();
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
        txtCompanyName.clear();
        txtSupplierName.clear();
        txtDescription.clear();
        txtContact.clear();
        txtSearch.clear();
    }

    private void loadSuppliersTable() {
        tblSupplierDetails.getItems().clear();
        try {
            ArrayList<Supplier> all = batchController.getAll();
            for (Supplier dto:all) {
                tblSupplierDetails.getItems().add(
                        new Supplier(
                                dto.getBatch_id(),
                                dto.getSupplierName(),
                                dto.getCompanyName(),
                                dto.getContact(),
                                dto.getDescription(),
                                dto.getAddedDate()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFieldsOnClick(ActionEvent actionEvent) {
        clearInputFields();
    }

}
