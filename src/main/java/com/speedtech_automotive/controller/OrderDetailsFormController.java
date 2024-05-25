package com.speedtech_automotive.controller;

import com.speedtech_automotive.db.DBConnection;
import com.speedtech_automotive.model.Customer;
import com.speedtech_automotive.model.Order;
import com.speedtech_automotive.model.OrderDetail;
import com.speedtech_automotive.tm.OrderDetailTm;
import com.speedtech_automotive.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public TableColumn colProductName;
    public TableColumn colProductCode;
    public TableColumn colSellingPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colProductTotal;
    public TableView tblOrderedProductList;
    public ListView<String> lvOrderSearch;
    public Button btnCustomerClear;
    private final OrderController orderController = new OrderController();
    private final CustomerController customerController = new CustomerController();
    private final OrderDetailsController orderDetailsController = new OrderDetailsController();
    public Text txtCustomerName;
    public Text txtOrderTotal;
    public Text txtTotalDiscount;
    public Text txtDiscountPrice;
    public Text txtProductName;
    public Text txtAddedDate;
    public TextField txtPrice;
    public TextField txtDiscount;
    public TextField txtQuantity;
    public Text lblTotalDiscount;
    public Text lblDiscountedPrice;
    public Text lblTotalValue;
    public DatePicker datePicker;
    public TextField txtSearchCustomer;
    private Boolean isProductTableUpdate = false;
    private Order selectedorder;
    Customer selectedCustomer;
    OrderDetail selectedOrderDetail;
    ArrayList<OrderDetail> selectedOrderDetails;
    BigDecimal totalPrice = new BigDecimal(0);
    BigDecimal totalDiscount = new BigDecimal(0);
    BigDecimal finalTotal = new BigDecimal(0);


    ArrayList<Order> allOrders;
    ArrayList<Customer> allCustomers;
    ArrayList<OrderDetail> allOrderDetails;

    public void initialize() {
        try {

            allOrders = orderController.getAllOrders();
            allCustomers = customerController.getAll();
            allOrderDetails = orderDetailsController.getAllOrderDetails();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colProductTotal.setCellValueFactory(new PropertyValueFactory<>("productTotal"));
        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblOrderedProductList.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");
            btnDelete.disableProperty();
            btnDelete.setDisable(true);
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        /* need to implement with stock data reduce part*/
//        lastCol.setCellValueFactory(param -> {
//            Button btnDelete = new Button("Delete");
//            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");
//
//            btnDelete.setOnAction(event -> {
//                deleteProductDetails(param.getValue());
//                tblOrderedProductList.getSelectionModel().clearSelection();
//            });
//            return new ReadOnlyObjectWrapper<>(btnDelete);
//        });
        clearFieldsOnClick();
        tblOrderedProductList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, tblSelectedOrderDetail) -> {
            if (tblSelectedOrderDetail != null) {
                setProductDetailsToLables((OrderDetail) tblSelectedOrderDetail);
            }
        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    allOrders = orderController.getOrdersByDate(newValue);
                    setDataToViewList();
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                    throw new RuntimeException(e);

                }            } else {
                System.out.println("No date selected");
            }
        });
    }

    private void setProductDetailsToLables(OrderDetail tblSelectedOrderDetail) {
        selectedOrderDetail = tblSelectedOrderDetail;
        txtPrice.setText(String.valueOf(tblSelectedOrderDetail.getSellingPrice()));
        txtDiscount.setText(String.valueOf(tblSelectedOrderDetail.getDiscount()));
        txtQuantity.setText(String.valueOf(tblSelectedOrderDetail.getQuantity()));
        txtProductName.setText(tblSelectedOrderDetail.getProductName());
    }

    private void loadProductDetailsTable() {
        tblOrderedProductList.getItems().clear();
        if (selectedOrderDetails != null) {
            tblOrderedProductList.getItems().addAll(selectedOrderDetails);
        }
        calculateTotals();
    }

    private void deleteProductDetails(OrderDetailTm value) {

    }

    public void onListViewMouseExited(MouseEvent mouseEvent) {
        lvOrderSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }

    public void clearAllFields() {
        txtOrderTotal.setText("--");
        txtDiscountPrice.setText("--");
        txtDiscountPrice.setText("--");
        txtCustomerName.setText("--");
        txtAddedDate.setText("--");
        clearFieldsOnClick();
        tblOrderedProductList.getItems().clear();
        selectedOrderDetails.clear();
        lblDiscountedPrice.setText("--");
        lblTotalValue.setText("--");
        lblTotalDiscount.setText("--");
    }

    public void onBackToOrderForm(ActionEvent actionEvent) {
        Utils.setUi("OrderForm");
    }

    public void onSearchCustomerKeyType(KeyEvent keyEvent) {
        ArrayList<Customer> tempAllCustomers = allCustomers;
        ArrayList<Customer> customerArray = new ArrayList<>();
        ArrayList<Order> orderArrayList = new ArrayList<>();
        String searchFieldText = txtSearchCustomer.getText().toLowerCase();
        for (Customer customer : tempAllCustomers) {
            if (customer.getName().toLowerCase().contains(searchFieldText) ||
                    customer.getCustomer_nic().toLowerCase().contains(searchFieldText) ||
                    customer.getNumber_plate().toLowerCase().contains(searchFieldText)) {
                customerArray.add(customer);
            }
        }
        for (Customer customer : customerArray) {
            for (Order tm : allOrders) {
                if (customer.getCus_id().equals(tm.getCustomer_id())){
                    orderArrayList.add(tm);
                }
            }
        }
        allOrders = orderArrayList;
        setDataToViewList();
        loadAllOrders();
//        lvOrderSearch.getItems().clear();
//        lvOrderSearch.getItems().addAll(customerArray);
//        customerArray.clear();
    }

    public void clearSearchCustomer(ActionEvent actionEvent) {
    }

    public void validateQuantity(KeyEvent keyEvent) {
    }

    public void addOrderDetailToTable(ActionEvent actionEvent) {
        BigDecimal quantity = BigDecimal.valueOf(Integer.parseInt(txtQuantity.getText()));
        int intQuantity = Integer.parseInt(txtQuantity.getText());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));
        BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(txtDiscount.getText()));
//        String customerId = null;
//        String customerName = null;
//        if (!txtSearchCustomer.getText().isEmpty()){
//            customerId = txtSearchCustomer.getText().split("-")[0];
//            customerName = txtSearchCustomer.getText().split("-")[1];
//        }
        OrderDetail orderDetail = new OrderDetail(selectedOrderDetail.getOrderDetail_id(), selectedOrderDetail.getOrder_id(),
                selectedOrderDetail.getProduct_id(), selectedOrderDetail.getProductName(), selectedOrderDetail.getProductCode(),
                price, intQuantity, discount, (price.subtract(discount)).multiply(quantity));

        tblOrderedProductList.getItems().clear();
        selectedOrderDetails.removeIf(obj -> obj.getProduct_id().equals(orderDetail.getProduct_id()));
        selectedOrderDetails.add(orderDetail);
        tblOrderedProductList.getItems().addAll(selectedOrderDetails);
        clearFieldsOnClick();
//        isOrderTableUpdate = false;
//        btnAddUpdateOrder.setText("Add Order");
        calculateTotals();
    }

    private void calculateTotals() {
        finalTotal = new BigDecimal(0);
        totalDiscount = new BigDecimal(0);
        totalPrice = new BigDecimal(0);
        for (OrderDetail orderDetail : selectedOrderDetails) {
            finalTotal = finalTotal.add(orderDetail.getProductTotal());
            totalPrice = totalPrice.add(orderDetail.getSellingPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));
        }
        totalDiscount = totalPrice.subtract(finalTotal);

        lblTotalValue.setText(totalPrice.toString());
        lblTotalDiscount.setText(totalDiscount.toString());
        lblDiscountedPrice.setText(finalTotal.toString());
    }

    public void clearFieldsOnClick() {
        txtPrice.clear();
        txtDiscount.clear();
        txtQuantity.clear();
        txtProductName.setText("--");
    }


    public void onCustomerSearch() {
        loadAllOrders();
        setDataToViewList();
    }

    private void loadAllOrders() {
        try {
            allOrders = orderController.getAllOrders();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    private void setDataToViewList() {
        lvOrderSearch.setVisible(true);
        btnCustomerClear.setVisible(true);

        ArrayList<String> arrayList = new ArrayList<>();
        String customerDetails = null;
        for (Order order : allOrders) {
            if (order.getCustomer_id() == null){
                customerDetails = ", -No customer";
                selectedCustomer = null;
            } else {
                selectedCustomer = getCustomer(order.getCustomer_id());
                customerDetails = ", -Customer Name: "+selectedCustomer.getName()+", -Number Plate: "+ selectedCustomer.getNumber_plate();
            }
            arrayList.add(order.getOrder_id()+"-Date: "+order.getAddedDate() +", -Order Total "+ order.getFinalTotal()+customerDetails);
        }
        lvOrderSearch.getItems().clear();
        lvOrderSearch.getItems().addAll(arrayList);
    }

    public Customer getCustomer(String customerId) {
        for (Customer customer : allCustomers) {
            if (customer.getCus_id().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public Order getOrder(String order_id) {
        for (Order order : allOrders) {
            if (order.getOrder_id().equals(order_id)) {
                return order;
            }
        }
        return null;
    }

    public ArrayList<OrderDetail> getOrderDetails(String order_id) {
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        for (OrderDetail orderDetail : allOrderDetails) {
            if (orderDetail.getOrder_id().equals(order_id)) {
                arrayList.add(orderDetail);
            }
        }
        return arrayList;
    }

    public void placeOrder(ActionEvent actionEvent) {
        Date addedDate = Date.valueOf(LocalDate.now());
        boolean status = false;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String customer_id = null;
            String numberPlate = null;
            if (selectedCustomer != null) {
                customer_id = selectedCustomer.getCus_id();
                numberPlate = selectedCustomer.getNumber_plate();
            }

            boolean isUpdated = orderController.updateOrder(new Order(totalPrice, totalDiscount, finalTotal, selectedorder.getOrder_id()));
            boolean isDeleted = orderDetailsController.deleteOrderDetails(selectedorder.getOrder_id());
            if (isUpdated && isDeleted) {
                for (OrderDetailTm orderDetail : selectedOrderDetails) {
                    status = OrderController.saveOrderDetail(orderDetail, selectedorder.getOrder_id());
                    if (!status) {
                        throw new SQLException("Something went wrong..!!");
                    }
                }
                con.commit();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Updated").show();
                clearAllFields();
                onCustomerSearch();
//                joinList = stockController.getStockProductJoin();
            }
        } catch (Exception er) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new Alert(Alert.AlertType.ERROR, "Something went wrong..!!").show();
        } finally {
            try {
                assert con != null;
                con.setAutoCommit(true);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong..!!").show();
                throw new RuntimeException(e);
            }
        }
    }

    public void onListViewMouseClick(MouseEvent mouseEvent) {
        String[] array = lvOrderSearch.getSelectionModel().getSelectedItem().split("-");
        selectedorder = getOrder(array[0].trim());
        selectedCustomer = getCustomer(selectedorder.getCustomer_id());
        selectedOrderDetails = getOrderDetails(selectedorder.getOrder_id());

        txtCustomerName.setText(selectedCustomer != null ? selectedCustomer.getName() : "--");
        txtOrderTotal.setText(String.valueOf(selectedorder.getTotalPrice()));
        txtTotalDiscount.setText(String.valueOf(selectedorder.getTotalDiscount()));
        txtDiscountPrice.setText(String.valueOf(selectedorder.getFinalTotal()));
        txtAddedDate.setText(String.valueOf(selectedorder.getAddedDate()));
        loadProductDetailsTable();
        lvOrderSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }


    private void setUpdateData(Customer customer) {
    }

    public void datepickerOnAction(ActionEvent actionEvent) {
        datePicker.setValue(null);
    }
}
