package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Customer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtSearchCustomer;
    public TextField txtCustomerName;
    public TextField txtCustomerNIC;
    public TextField txtAddress;
    public TableView tblCustomerDetails;
    public TableColumn colCustomerName;
    public TableColumn colCustomerNIC;
    public TableColumn colNumber;
    public TableColumn colNumberPlate;
    public TableColumn colAddress;
    public TableColumn colAction;
    public ListView<String> lvSearch;
    public Button btnCustomerClear;
    public TextField txtContactNumber;
    public TextField txtNumberPlate;
    public Customer selectedCustomer;
    ArrayList<Customer> allCustomers;
    private final  CustomerController customerController = new CustomerController();


    public void initialize(){
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customer_nic"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNumberPlate.setCellValueFactory(new PropertyValueFactory<>("number_plate"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Customer, Button> lastCol = (TableColumn<Customer, Button>) tblCustomerDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                DeleteProduct(param.getValue());
                tblCustomerDetails.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        loadCustomerTable();

        tblCustomerDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomerDetail) -> {
            if (selectedCustomerDetail != null) {
                SetUpdateData((Customer) selectedCustomerDetail);
            }
        });
    }

    private void SetUpdateData(Customer dto) {
        selectedCustomer = dto;
        txtCustomerName.setText(dto.getName());
        txtContactNumber.setText(dto.getContact());
        txtCustomerNIC.setText(dto.getCustomer_nic());
        txtAddress.setText(dto.getAddress());
        txtNumberPlate.setText(dto.getNumber_plate());
        txtSearchCustomer.clear();
    }

    private void loadCustomerTable() {
        tblCustomerDetails.getItems().clear();
        try {
            allCustomers = customerController.getAll();
            for (Customer dto:allCustomers) {
                tblCustomerDetails.getItems().add(
                        new Customer(
                                dto.getCus_id(),
                                dto.getName(),
                                dto.getContact(),
                                dto.getNumber_plate(),
                                dto.getAddress(),
                                dto.getCustomer_nic()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void DeleteProduct(Customer param) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't Delete customer "+param.getName()+" ..!",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getText().equals("Yes")) {
            try {
                if (customerController.deleteCustomerById(param.getCus_id())) {
                    new Alert(Alert.AlertType.INFORMATION, "Delete"+param.getName()+" ..!").show();
                    loadCustomerTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void clearFieldsOnClick(ActionEvent actionEvent) {
        clearInputFields();
        txtSearchCustomer.clear();
    }

    public void addCustomerDetails(ActionEvent actionEvent) {
        String customerName = txtCustomerName.getText();
        String contact = txtContactNumber.getText();
        String numberPlate = txtNumberPlate.getText();
        String address = txtAddress.getText();
        String cusNIC = txtCustomerNIC.getText();

        try {
            if (selectedCustomer == null){
                Customer customer = new Customer(customerName, contact, numberPlate, address, cusNIC);
                boolean isSaved = customerController.saveCustomer(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer added successfully..!").show();
                    clearInputFields();
                    loadCustomerTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                selectedCustomer.setName(customerName);
                selectedCustomer.setContact(contact);
                selectedCustomer.setNumber_plate(numberPlate);
                selectedCustomer.setAddress(address);
                selectedCustomer.setCustomer_nic(cusNIC);
                boolean isSaved = customerController.updateCustomer(selectedCustomer);
                selectedCustomer = null;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully..!").show();
                    loadCustomerTable();
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
        txtCustomerNIC.clear();
        txtAddress.clear();
        txtCustomerName.clear();
        txtNumberPlate.clear();
        txtContactNumber.clear();
    }

    public void onCustomerSearchClicked(MouseEvent mouseEvent) {
        String[] array = lvSearch.getSelectionModel().getSelectedItem().split("-");
        for (Customer customer : allCustomers) {
            if (customer.getCus_id().equals(array[0].trim())){
                SetUpdateData(customer);
                break;
            }
        }
        lvSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }

    public void onListViewMouseExited(MouseEvent mouseEvent) {
        lvSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }

    public void onCustomerSearch(KeyEvent keyEvent) {
        ArrayList<Customer> tempAllCustomers = allCustomers;
        ArrayList<String> customerArray = new ArrayList<>();
        String searchFieldText = txtSearchCustomer.getText().toLowerCase();
        for (Customer customer : tempAllCustomers) {
            if (customer.getName().toLowerCase().contains(searchFieldText) ||
                    customer.getCustomer_nic().toLowerCase().contains(searchFieldText) ||
                    customer.getNumber_plate().toLowerCase().contains(searchFieldText)) {
                customerArray.add(customer.getCus_id() +" - "+ customer.getName()+" - "+ customer.getCustomer_nic()+" - "+customer.getNumber_plate());
            }
        }
        lvSearch.getItems().clear();
        lvSearch.getItems().addAll(customerArray);
        customerArray.clear();
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        lvSearch.setVisible(true);
        btnCustomerClear.setVisible(true);

        ArrayList<String> customerArray = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customerArray.add(customer.getCus_id() +" - "+ customer.getName()+" - "+ customer.getCustomer_nic()+" - "+customer.getNumber_plate());
        }
        lvSearch.getItems().clear();
        lvSearch.getItems().addAll(customerArray);
    }
}
